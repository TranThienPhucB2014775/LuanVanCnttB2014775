package com.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder
                .routes()
                .route(p -> p.path("/api/v1/identity/**")
                        .filters(f -> f.rewritePath("/api/v1/identity/(?<segment>.*)", "/${segment}"))
                        .uri("lb://IDENTITY-SERVICE"))
                .route(p -> p.path("/api/v1/profile/**")
                        .filters(f -> f.rewritePath("/api/v1/profile/(?<segment>.*)", "/${segment}"))
                        .uri("lb://PROFILE-SERVICE"))
                .build();
    }
}
