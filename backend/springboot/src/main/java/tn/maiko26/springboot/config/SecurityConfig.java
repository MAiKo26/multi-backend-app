package tn.maiko26.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import tn.maiko26.springboot.interceptor.JwtAuthInterceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthInterceptor jwtAuthInterceptor) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/**").hasAnyRole("user", "admin")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthInterceptor, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType("application/json");

                            Map<String, Object> errorDetails = new HashMap<>();
                            errorDetails.put("status", HttpStatus.UNAUTHORIZED.value());
                            errorDetails.put("message", "Not Authenticated: " + authException.getMessage());
                            errorDetails.put("timestamp", new Date());
                            errorDetails.put("path", request.getRequestURI());

                            response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json");

                            Map<String, Object> errorDetails = new HashMap<>();
                            errorDetails.put("status", HttpStatus.FORBIDDEN.value());
                            errorDetails.put("message", "Not Authorized: " + accessDeniedException.getMessage());
                            errorDetails.put("timestamp", new Date());
                            errorDetails.put("path", request.getRequestURI());

                            response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
                        })
                );

        return http.build();
    }
}