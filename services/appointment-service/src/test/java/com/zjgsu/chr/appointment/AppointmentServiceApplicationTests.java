package com.zjgsu.chr.appointment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class AppointmentServiceApplicationTests {

    @MockitoBean
    DiscoveryClient discoveryClient;

    @Test
    void contextLoads() {
    }
}
