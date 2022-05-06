package com.example.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {

    //@Bean
    public RouteLocator gate(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/user-service/**")
                            .filters(f->
                                    f.addRequestHeader("user-request", "user-request-header")
                                    .addRequestHeader("user-response", "use-response-header"))
                                    .uri("http://localhost:8081"))
                .route(r -> r.path("/order-service/**")
                        .filters(f-> f.addRequestHeader("order-request", "order-request-header")
                                .addRequestHeader("order-response", "order-response-header"))
                                .uri("http://localhost:8082"))
                .build();
    }
}
