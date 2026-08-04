package com.example.demo;

import org.aspectj.weaver.patterns.IVerificationRequired;
import org.springframework.web.bind.annotation.* ;
import java.util.List ;

@RestController
@RequestMapping("/verify")
@CrossOrigin

public class VerificationController {

    private final SimpleEmailVerificationService service ;

    public VerificationController(SimpleEmailVerificationService service) {
        this.service = service ;
    }
    @PostMapping("/send")
    public String sendCode(@RequestParam String email) {
        service.sendCode(email);
        return "code sent !" ;
    }
}
