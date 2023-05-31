package com.bookstore.history_transactionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HistoryTransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoryTransactionServiceApplication.class, args);
    }

}
