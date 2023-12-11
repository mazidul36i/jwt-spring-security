package com.gliesestudio.os.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Cors config for the application to expose API endpoint which can be consumed by UI
 *
 * @author MazidulIslam
 * @since 09-12-2023
 */
@Configuration
public class CorsConfig {

    private final static String GET = "GET";
    private final static String POST = "POST";
    private final static String PUT = "PUT";
    private final static String DELETE = "DELETE";

    /**
     * It takes {@link CorsRegistry} and configs mappings, request methods, headers, origin, and credential etc.
     *
     * @return {@link WebMvcConfigurer}
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods(GET, POST, PUT, DELETE)
                        .allowedHeaders("*")
                        .allowedOriginPatterns("*")
                        .allowCredentials(true);
            }
        };
    }

}