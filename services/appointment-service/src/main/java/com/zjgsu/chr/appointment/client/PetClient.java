package com.zjgsu.chr.appointment.client;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class PetClient {

    private final RestTemplate restTemplate;
    private final String petServiceBaseUrl;

    public PetClient(
            @Value("${pet-service.base-url}") String petServiceBaseUrl,
            @Value("${pet-service.connect-timeout}") Duration connectTimeout,
            @Value("${pet-service.read-timeout}") Duration readTimeout) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        this.restTemplate = new RestTemplate(requestFactory);
        this.petServiceBaseUrl = petServiceBaseUrl;
    }

    public PetSummary requirePet(Long petId) {
        try {
            PetSummary pet = restTemplate.getForObject(
                    petServiceBaseUrl + "/api/pets/{id}", PetSummary.class, petId);
            if (pet == null) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "宠物服务返回了空响应");
            }
            return pet;
        } catch (HttpClientErrorException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "宠物不存在，不能创建预约: " + petId);
        } catch (RestClientException exception) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "宠物服务不可用，预约未创建: " + exception.getMessage());
        }
    }

    public record PetSummary(Long id, Long ownerId, String name, String species) { }
}
