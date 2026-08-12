package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SimpleEmailVerificationService {

    @Value("${BREVO_API_KEY}")
    private String apiKey ;

    private final Map<String, String> codes = new HashMap<>() ;

    private final RestTemplate restTemplate = new RestTemplate() ;


    public void sendCode(String email) {
        String code = String.valueOf(new Random().nextInt(900000) + 100000) ;
        codes.put(email, code) ;

        HttpHeaders headers = new HttpHeaders() ;
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        Map<String, Object> body = new HashMap<>() ;
        body.put("sender", Map.of("name","Thrift Store","email", "sarabenabed9@gmail.com")) ;
        body.put("to", List.of(Map.of("email", email))) ;

        body.put("subject", "Verification Code") ;
        body.put("textContent", "Your Verification Code is : " + code) ;

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers) ;
        try {
            restTemplate.postForEntity("https://api.brevo.com/v3/smtp/email", request, String.class);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
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
