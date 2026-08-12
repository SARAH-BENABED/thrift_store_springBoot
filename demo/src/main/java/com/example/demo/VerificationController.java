package com.example.demo;

import org.aspectj.weaver.patterns.IVerificationRequired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> sendCode(@RequestParam String email) {
        System.out.println("inside controller");
        try {
            service.sendCode(email);
            System.out.println("email sent !");
            return ResponseEntity.ok("Code sent!");
        }
        catch (Exception e) {
            e.printStackTrace(); // prints full stack trace

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getClass().getName() + "\n" + e.getMessage());
        }
    }

}
