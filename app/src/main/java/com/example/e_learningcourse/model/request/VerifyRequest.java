package com.example.e_learningcourse.model.request;

public class VerifyRequest {
    private String email;
    private String verificationCode;

    public VerifyRequest(String email, String verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
    }
}
