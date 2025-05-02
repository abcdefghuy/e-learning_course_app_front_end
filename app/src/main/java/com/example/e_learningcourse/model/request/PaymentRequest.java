package com.example.e_learningcourse.model.request;

public class PaymentRequest {
    private Long userId;
    private Long courseId;
    private double amount;
    private String provider;

    public PaymentRequest(Long userId, Long courseId, double amount, String provider) {
        this.amount = amount;
        this.courseId = courseId;
        this.provider = provider;
        this.userId = userId;
    }
}
