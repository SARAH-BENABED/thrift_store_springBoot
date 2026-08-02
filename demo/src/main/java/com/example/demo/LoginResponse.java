package com.example.demo;

public class LoginResponse {

    private User user ;
    private String token ;

    public LoginResponse(String token, User user ) {
        this.token = token ;
        this.user = user ;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
