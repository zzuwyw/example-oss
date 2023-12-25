package com.example.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ExampleOssServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleOssServerApplication.class, args);
    }

}
