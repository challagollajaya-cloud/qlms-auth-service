package com.microsoft.quantum.qlmsauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class QlmsAuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(
                QlmsAuthServiceApplication.class, args);
    }
}