package dev.batuhanyetgin.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth", r -> r.path("/v1/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8089"))
                .route("debt", r -> r.path("/v1/debt/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8898"))
                .build();
    }

    @Bean
    public HttpMessageConverters customConverters() {
        return new HttpMessageConverters();
    }
}