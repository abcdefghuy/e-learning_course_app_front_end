package com.example.e_learningcourse.model.response;

public class LessonResponse {
    private Long lessonId;
    private Long courseId;
    private String lessonName;
    private String lessonDescription;
    private String status;
    private int duration;
    private String lessonVideoUrl;
    private int lessonOrder;

    public LessonResponse() {
    }

    public LessonResponse(Long lessonId, Long courseId, String lessonName, String lessonDescription, String status, int duration, String lessonVideoUrl, int lessonOrder) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.lessonName = lessonName;
        this.lessonDescription = lessonDescription;
        this.status = status;
        this.duration = duration;
        this.lessonVideoUrl = lessonVideoUrl;
        this.lessonOrder = lessonOrder;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonDescription() {
        return lessonDescription;
    }

    public void setLessonDescription(String lessonDescription) {
        this.lessonDescription = lessonDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLessonVideoUrl() {
        return lessonVideoUrl;
    }

    public void setLessonVideoUrl(String lessonVideoUrl) {
        this.lessonVideoUrl = lessonVideoUrl;
    }

    public int getLessonOrder() {
        return lessonOrder;
    }

    public void setLessonOrder(int lessonOrder) {
        this.lessonOrder = lessonOrder;
    }
}
