package com.web.protal.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProtalApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProtalApplication.class, args);
    }
}
