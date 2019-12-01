package com.webstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class WebStoreEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebStoreEurekaApplication.class,args);
    }
}
