package com.example.e_learningcourse.model;

public class Review {
    private String reviewerName;
    private String reviewerAvatar;
    private String reviewDate;
    private float rating;
    private String content;
    private boolean isVerified;

    public Review(String reviewerName, String reviewerAvatar, String reviewDate, float rating, String content, boolean isVerified) {
        this.reviewerName = reviewerName;
        this.reviewerAvatar = reviewerAvatar;
        this.reviewDate = reviewDate;
        this.rating = rating;
        this.content = content;
        this.isVerified = isVerified;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public String getReviewerAvatar() {
        return reviewerAvatar;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public float getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public boolean isVerified() {
        return isVerified;
    }
} 