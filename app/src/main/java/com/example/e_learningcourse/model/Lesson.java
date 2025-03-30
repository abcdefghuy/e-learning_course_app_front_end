package com.example.e_learningcourse.model;

public class Lesson {
    private String number;
    private String title;
    private String duration;
    private boolean isLocked;

    public Lesson(String number, String title, String duration, boolean isLocked) {
        this.number = number;
        this.title = title;
        this.duration = duration;
        this.isLocked = isLocked;
    }

    public String getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public boolean isLocked() {
        return isLocked;
    }
} 