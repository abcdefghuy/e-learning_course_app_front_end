package com.example.e_learningcourse.model;

import java.util.List;

public class Section {
    private String title;
    private String duration;
    private List<Lesson> lessons;

    public Section(String title, String duration, List<Lesson> lessons) {
        this.title = title;
        this.duration = duration;
        this.lessons = lessons;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }
} 