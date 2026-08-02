package com.example.demo;

public class LoginResponse {

    private String token ;
    private String name ;
    private String phone ;

    public LoginResponse(String token, String name, String phone ) {
        this.token = token ;
        this.name = name ;
        this.phone = phone ;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getToken() {
        return token;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
