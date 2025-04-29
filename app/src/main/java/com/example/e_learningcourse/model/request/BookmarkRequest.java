package com.example.e_learningcourse.model.request;

public class BookmarkRequest {
    private Long courseId;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
    public BookmarkRequest(Long courseId) {
        this.courseId = courseId;
    }
}
