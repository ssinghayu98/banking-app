package com.bank.banking.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        // 🔥 IMPORTANT
        config.setAllowCredentials(true);

        // ✅ Allow frontend (local + Vercel)
        config.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:3000",
                "https://*.vercel.app"
        ));

        // ✅ Allow everything needed
        config.setAllowedHeaders(Arrays.asList("*"));

        config.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        // 🔥 VERY IMPORTANT (fixes many CORS issues)
        config.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type"
        ));

        // 🔥 cache preflight (optional but good)
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}