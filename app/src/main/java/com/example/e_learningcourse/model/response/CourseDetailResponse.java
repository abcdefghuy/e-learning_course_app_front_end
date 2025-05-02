package com.example.e_learningcourse.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class CourseDetailResponse {
    private Long courseId;
    private String courseName;
    private boolean enabled;
    private Double coursePrice;
    private String courseImg;
    private String courseDescription;
    private Integer duration;
    private Date createdAt;
    private Date updateAt;
    private String level;
    private Double rating;
    private int lessonCount;
    private int reviewCount;
    private Integer studentQuantity;
    private List<String> categoryNames;
    private boolean isBestSeller;
    private boolean isBookmarked;
    @SerializedName("enrolled")
    private boolean isEnrolled;

    public CourseDetailResponse(Long courseId, String courseName, boolean enabled, Double coursePrice, String courseImg, String courseDescription, Integer duration, Date createdAt, Date updateAt, String level, Double rating, int lessonCount, int reviewCount, Integer studentQuantity, List<String> categoryNames, boolean isBestSeller, boolean isBookmarked, boolean isEnrolled) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.enabled = enabled;
        this.coursePrice = coursePrice;
        this.courseImg = courseImg;
        this.courseDescription = courseDescription;
        this.duration = duration;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.level = level;
        this.rating = rating;
        this.lessonCount = lessonCount;
        this.reviewCount = reviewCount;
        this.studentQuantity = studentQuantity;
        this.categoryNames = categoryNames;
        this.isBestSeller = isBestSeller;
        this.isBookmarked = isBookmarked;
        this.isEnrolled = isEnrolled;
    }
    public CourseDetailResponse() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Double getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Double coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(int lessonCount) {
        this.lessonCount = lessonCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getStudentQuantity() {
        return studentQuantity;
    }

    public void setStudentQuantity(Integer studentQuantity) {
        this.studentQuantity = studentQuantity;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public boolean isBestSeller() {
        return isBestSeller;
    }

    public void setBestSeller(boolean bestSeller) {
        isBestSeller = bestSeller;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public boolean isEnrolled() {
        return isEnrolled;
    }

    public void setEnrolled(boolean enrolled) {
        this.isEnrolled = enrolled;
    }
}
