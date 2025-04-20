package com.example.e_learningcourse.model.response;

import java.util.Date;

public class ReviewResponse {
    private int reviewId;
    private Long courseId;
    private String reviewUserName;
    private String reviewContent;
    private Date reviewDate;
    private int reviewScore;
    private String reviewerAvatarUrl;

    public String getReviewerAvatarUrl() {
        return reviewerAvatarUrl;
    }

    public void setReviewerAvatarUrl(String reviewerAvatarUrl) {
        this.reviewerAvatarUrl = reviewerAvatarUrl;
    }

    public int getReviewId() {
        return reviewId;
    }

    public ReviewResponse() {
    }

    public ReviewResponse(int reviewId, Long courseId, String reviewUserName, String reviewContent, Date reviewDate, int reviewScore, String reviewerAvatarUrl) {
        this.reviewId = reviewId;
        this.courseId = courseId;
        this.reviewUserName = reviewUserName;
        this.reviewContent = reviewContent;
        this.reviewDate = reviewDate;
        this.reviewScore = reviewScore;
        this.reviewerAvatarUrl= reviewerAvatarUrl;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getReviewUserName() {
        return reviewUserName;
    }

    public void setReviewUserName(String reviewUserName) {
        this.reviewUserName = reviewUserName;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }
}
