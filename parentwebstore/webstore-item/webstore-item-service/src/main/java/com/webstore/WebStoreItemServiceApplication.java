package com.webstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WebStoreItemServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebStoreItemServiceApplication.class,args);
    }
}
