package com.example.e_learningcourse.model.response;

import java.util.List;

public class QuizQuestionResponse {
    private Long id;
    private String questionText;
    private List<String> options;
    private int correctIndex;
    private Long lessonId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public QuizQuestionResponse(Long id, String questionText, List<String> options, int correctIndex, Long lessonId) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.correctIndex = correctIndex;
        this.lessonId = lessonId;
    }
}
