package com.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableFeignClients
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/b2014775/identity/**")
                        .filters(f -> f.rewritePath("/b2014775/identity/(?<segment>.*)", "/${segment}"))
                        .uri("lb://IDENTITY-SERVICE"))
                .route(p -> p
                        .path("/b2014775/profile/**")
                        .filters(f -> f.rewritePath("/b2014775/profile/(?<segment>.*)", "/${segment}"))
                        .uri("lb://PROFILE-SERVICE"))
                .build();
    }


}
