package com.example.e_learningcourse.model.request;

public class VerifyRequest {
    private String email;
    private String verificationCode;
    private String action;

    public VerifyRequest(String email, String verificationCode, String action) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.action = action;
    }
}
