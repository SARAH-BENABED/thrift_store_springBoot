package com.example.demo;

import jakarta.servlet.*;
import jakarta.servlet.http.* ;
import org.springframework.stereotype.Component ;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder ;
import java.util.Collection ;

import java.io.IOException ;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService ;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService ;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String authHeader = request.getHeader("Authorization") ;
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                String email = jwtService.extractEmail(token);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                Collections.emptyList()
                        );
                SecurityContextHolder.getContext().setAuthentication(auth);

            }catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid or expired token");
                return ;
            }
        }

        filterChain.doFilter(request,response);

    }
}

