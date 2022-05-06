package com.example.gateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom Pre Filter
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            log.info("Global filter baseMessage : {}", config.getBaseMessage());
//
//            if(config.isPostLoger()){
//                log.info("Global filter PRE request ID : {}", request.getId());
//                log.info("Global filter PRE request URI : {}", request.getURI());
//            }
//
//            // Custom Post Filter
//            return chain.filter(exchange).then(Mono.fromRunnable(()->{
//                if(config.isPostLoger()){
//                    log.info("Global POST filter =: response code -> {}", response.getStatusCode());
//                }
//            }));
//
//        };
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) ->{
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging filter baseMessage : {}", config.getBaseMessage());

            if(config.isPreLogger()){
                log.info("Logging filter PRE request ID : {}", request.getId());
                log.info("Logging filter PRE request URI : {}", request.getURI());
            }

            // Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if(config.isPostLogger()){
                    log.info("Logging POST filter =: response code -> {}", response.getStatusCode());
                }
            }));
        }, Ordered.HIGHEST_PRECEDENCE);
        return filter;
    }

    @Data
    public static class Config{
        // Put the configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

}
