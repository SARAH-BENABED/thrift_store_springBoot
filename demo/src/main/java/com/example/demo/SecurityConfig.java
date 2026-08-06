package com.example.demo;

import org.springframework.context.annotation.Bean ;
import org.springframework.context.annotation.Configuration ;
import org.springframework.security.config.annotation.web.builders.HttpSecurity ;
import org.springframework.security.web.SecurityFilterChain ;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder ;
import org.springframework.security.crypto.password.PasswordEncoder ;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter ;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter ;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) .cors(cors -> {}).authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/orders/send", "/products/get","/test-email/**" , "/verify/**").permitAll()
                .requestMatchers("/products/add").hasRole("ADMIN")
                .anyRequest().authenticated()
        ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) ;
        return http.build() ;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder() ;
    }
}
