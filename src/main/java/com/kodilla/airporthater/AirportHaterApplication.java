package com.kodilla.airporthater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AirportHaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirportHaterApplication.class, args);
    }

}
