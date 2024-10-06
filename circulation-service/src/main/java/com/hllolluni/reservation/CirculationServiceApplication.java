package com.hllolluni.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication(
        scanBasePackages = {
                "com.hllolluni.reservation",
                "com.hllolluni.common_module.kafka"
        }
)
@EnableScheduling
public class CirculationServiceApplication {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/borrow/**").allowedHeaders("*")
                        .allowedOrigins("http://localhost:4200").allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(CirculationServiceApplication.class, args);
    }
}