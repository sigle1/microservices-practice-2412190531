package com.zjgsu.chr.appointment.client;

import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class PetClient {

    private static final Logger log = LoggerFactory.getLogger(PetClient.class);

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
    private final String serviceName;

    public PetClient(
            RestTemplate restTemplate,
            DiscoveryClient discoveryClient,
            @Value("${pet-service.service-name:pet-service}") String serviceName) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
        this.serviceName = serviceName;
    }

    public PetSummary requirePet(Long petId) {
        // Query Nacos on every business operation so that a restarted or removed
        // instance is never hidden behind a URL cached at application startup.
        URI petServiceUri = discoverPetService();
        log.info("Discovered {} at {} for petId={}", serviceName, petServiceUri, petId);
        try {
            PetSummary pet = restTemplate.getForObject(
                    petServiceUri + "/api/pets/{id}", PetSummary.class, petId);
            if (pet == null) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "宠物服务返回了空响应");
            }
            return pet;
        } catch (HttpClientErrorException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "宠物不存在，不能创建预约: " + petId);
        } catch (RestClientException exception) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "宠物服务不可用，预约未创建: " + exception.getMessage(), exception);
        }
    }

    private URI discoverPetService() {
        List<ServiceInstance> instances;
        try {
            instances = discoveryClient.getInstances(serviceName);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "服务发现不可用，预约未创建: " + exception.getMessage(), exception);
        }
        if (instances == null || instances.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "宠物服务暂无可用实例，预约未创建: " + serviceName);
        }
        ServiceInstance instance = instances.getFirst();
        log.info("Resolved {} to instance {} ({})", serviceName,
                instance.getInstanceId(), instance.getUri());
        return instance.getUri();
    }

    public record PetSummary(Long id, Long ownerId, String name, String species) { }
}
