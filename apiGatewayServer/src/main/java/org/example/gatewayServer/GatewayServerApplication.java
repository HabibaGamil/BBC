package org.example.gatewayServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication

public class GatewayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/bbc/test/**")
                        .filters(f -> f.rewritePath("/bbc/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time",new Date().toString()))
                        .uri("lb://mqserver"))
                 .route(p -> p
                        .path("/bbc/post/**")
                        .filters(f -> f.rewritePath("/bbc/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time",new Date().toString()))
                        .uri("lb://mqserver"))
                .route(p -> p
                        .path("/bbc/directory/**")
                        .filters(f -> f.rewritePath("/bbc/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time",new Date().toString()))
                        .uri("lb://mqserver"))
                 .route(p -> p
                        .path("/bbc/newsfeed/**")
                        .filters(f -> f.rewritePath("/bbc/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time",new Date().toString()))
                        .uri("lb://mqserver"))
                 .route(p -> p
                        .path("/bbc/search/**")
                        .filters(f -> f.rewritePath("/bbc/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time",new Date().toString()))
                        .uri("lb://mqserver"))
                 .route(p -> p
                        .path("/bbc/user/**")
                        .filters(f -> f.rewritePath("/bbc/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time",new Date().toString()))
                        .uri("lb://mqserver"))
                  .build();

    }
}
