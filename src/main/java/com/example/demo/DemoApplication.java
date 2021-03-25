package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.demo.repositories")

public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        WebClient client = WebClient.builder()
                .baseUrl("https://api.jowi.club/v010/bills/:qr_code?api_key=q35Oua5HknLfRmY8cBvs0gNbt7xwxe3avAStXs1F&sig=3516596d8ff5da2")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "https://api.jowi.club/v010/bills/:qr_code?api_key=q35Oua5HknLfRmY8cBvs0gNbt7xwxe3avAStXs1F&sig=3516596d8ff5da2"))
                .build();
        System.out.println("DATA"+client);
    }

}
