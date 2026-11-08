package com.zjgsu.chr.appointment.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

class PetClientTest {

    private final DiscoveryClient discoveryClient = Mockito.mock(DiscoveryClient.class);
    private RestTemplate restTemplate;
    private MockRestServiceServer remote;
    private PetClient petClient;

    @BeforeEach
    void setUp() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(1));
        requestFactory.setReadTimeout(Duration.ofSeconds(1));
        restTemplate = new RestTemplate(requestFactory);
        remote = MockRestServiceServer.bindTo(restTemplate).build();
        petClient = new PetClient(restTemplate, discoveryClient, "pet-service");
    }

    @Test
    void resolvesTheCurrentInstanceForEveryCall() {
        Mockito.when(discoveryClient.getInstances("pet-service")).thenReturn(
                List.of(new DefaultServiceInstance("pet-a", "pet-service", "10.0.0.11", 8082, false)),
                List.of(new DefaultServiceInstance("pet-b", "pet-service", "10.0.0.12", 9082, false)));
        remote.expect(requestTo("http://10.0.0.11:8082/api/pets/1"))
                .andRespond(withSuccess("{\"id\":1,\"name\":\"团团\"}", MediaType.APPLICATION_JSON));
        remote.expect(requestTo("http://10.0.0.12:9082/api/pets/1"))
                .andRespond(withSuccess("{\"id\":1,\"name\":\"团团\"}", MediaType.APPLICATION_JSON));

        assertEquals(1L, petClient.requirePet(1L).id());
        assertEquals(1L, petClient.requirePet(1L).id());

        remote.verify();
        Mockito.verify(discoveryClient, Mockito.times(2)).getInstances("pet-service");
    }

    @Test
    void emptyInstanceListReturns503() {
        Mockito.when(discoveryClient.getInstances("pet-service")).thenReturn(List.of());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> petClient.requirePet(1L));

        assertEquals(503, exception.getStatusCode().value());
        remote.verify();
    }

    @Test
    void missingPetStillReturns404() {
        Mockito.when(discoveryClient.getInstances("pet-service")).thenReturn(
                List.of(new DefaultServiceInstance("pet-a", "pet-service", "10.0.0.11", 8082, false)));
        remote.expect(requestTo("http://10.0.0.11:8082/api/pets/99"))
                .andRespond(withResourceNotFound());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> petClient.requirePet(99L));

        assertEquals(404, exception.getStatusCode().value());
        remote.verify();
    }
}
