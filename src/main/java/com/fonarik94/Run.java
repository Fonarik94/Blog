package com.fonarik94;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Run {

    public static void main(String[] args) {
        SpringApplication.run(Run.class, args);
    }

}