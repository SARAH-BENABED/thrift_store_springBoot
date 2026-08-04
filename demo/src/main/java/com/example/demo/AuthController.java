package com.example.demo;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException ;
import io.jsonwebtoken.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder ;
import org.springframework.security.crypto.password.PasswordEncoder ;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")

public class AuthController {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder ;
    private final UserRepository userRepo ;
    private final OrderRepository orderRepo ;
    private final SimpleEmailVerificationService verificationService ;

    public AuthController(JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepo, OrderRepository orderRepo, SimpleEmailVerificationService verificationService) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder ;
        this.userRepo = userRepo ;
        this.orderRepo = orderRepo ;
        this.verificationService = verificationService ;
    }
    @GetMapping("/hello")
    public String hello() {
        return "Backend is running!";
    }

    @Transactional
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        if((userRepo.findByEmail(user.getEmail())).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists") ;
        }
        if(! verificationService.verifyCode(user.getEmail(), user.getVerificationCode()) ) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid or missing verification code . please try again") ;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user) ;
        List<Order> orders = orderRepo.findByGuestEmailAndUserIsNull(savedUser.getEmail()) ;
        for(Order order : orders) {
            order.setUser(savedUser);
        }
        orderRepo.saveAll(orders) ;
        return savedUser ;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody User user) {
        User existing = userRepo.findByEmail(user.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password")) ;
        if(! passwordEncoder.matches(user.getPassword(), existing.getPassword())) {
            throw new  ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password");
        }
        String token = jwtService.generateToken(existing.getEmail()) ;
        return new LoginResponse(token, existing) ;

    }

}

