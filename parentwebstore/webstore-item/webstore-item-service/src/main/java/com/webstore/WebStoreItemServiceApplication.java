package com.webstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.webstore.mapper")
public class WebStoreItemServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebStoreItemServiceApplication.class,args);
    }
}
