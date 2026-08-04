package com.example.demo;

import org.springframework.mail.SimpleMailMessage ;
import org.springframework.mail.javamail.JavaMailSender ;
import org.springframework.stereotype.Service ;
import java.util.* ;

@Service
public class SimpleEmailVerificationService {

    private final JavaMailSender mailSender ;
    private final Map<String, String> codes = new HashMap<>() ;

    public SimpleEmailVerificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender ;
    }

    public void sendCode(String email) {
        String code = String.valueOf(new Random().nextInt(900000) + 100000) ;
        codes.put(email, code) ;

        SimpleMailMessage message = new SimpleMailMessage() ;
        message.setTo(email);
        message.setSubject("Verification code");
        message.setText("Your Varification code is : " + code);

        mailSender.send(message);
    }

    public boolean verifyCode(String email, String inputCode) {
        String realCode = codes.get(email) ;

        if(realCode != null && realCode.equals(inputCode)) {
            codes.remove(email) ;
            return true ;
        }
        return false ;
    }
}
