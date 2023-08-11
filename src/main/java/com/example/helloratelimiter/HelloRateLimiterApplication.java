package com.example.helloratelimiter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.helloratelimiter.dao"}, annotationClass = Repository.class)

public class HelloRateLimiterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloRateLimiterApplication.class, args);
    }

}
