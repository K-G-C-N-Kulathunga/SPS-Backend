package com.it.sps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for APIs
                .cors(cors -> {})             // enable CORS with your WebConfig
                .authorizeHttpRequests(auth -> auth
                        // ✅ Permit preflight OPTIONS requests for CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // ✅ Permit public endpoints if any
                        .requestMatchers("/api/login/**").permitAll()
                        .requestMatchers("/report/**").permitAll()
                        .requestMatchers("/piv-cost-item/**").permitAll()
                        // ✅ Everything else requires Basic Auth
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // enable Basic Auth

        return http.build();
    }
}