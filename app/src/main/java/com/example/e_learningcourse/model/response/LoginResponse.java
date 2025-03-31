package com.example.e_learningcourse.model.response;

import androidx.annotation.NonNull;

public class LoginResponse {
    private String token;
    private long expiresIn;

    public String getToken() {
        return token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    @NonNull
    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
