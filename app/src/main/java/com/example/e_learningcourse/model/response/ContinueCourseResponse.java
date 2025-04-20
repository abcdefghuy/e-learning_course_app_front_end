package com.example.e_learningcourse.model.response;

public class ContinueCourseResponse {
    private Long courseId;
    private String courseTitle;
    private String categoryName;
    private String courseImageUrl;
    private int progress;

    public ContinueCourseResponse() {
    }

    public ContinueCourseResponse(Long courseId, String courseTitle, String categoryName, String courseImageUrl, int progress) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.categoryName = categoryName;
        this.courseImageUrl = courseImageUrl;
        this.progress = progress;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCourseImageUrl() {
        return courseImageUrl;
    }

    public void setCourseImageUrl(String courseImageUrl) {
        this.courseImageUrl = courseImageUrl;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
