package com.example.reportesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ReportesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportesServiceApplication.class, args);
    }

}
