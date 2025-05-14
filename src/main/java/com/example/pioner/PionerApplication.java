package com.example.pioner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class PionerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(PionerApplication.class, args);
    }
}
