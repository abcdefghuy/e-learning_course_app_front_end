package com.example.e_learningcourse.model.request;

public class RegisterRequest {
    private String email;
    private String password;
    private String username;

    public RegisterRequest(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
