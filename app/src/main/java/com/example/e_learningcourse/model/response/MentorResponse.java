package com.example.e_learningcourse.model.response;

public class MentorResponse {
    private Long id;
    private String mentorName;
    private String mentorAvatar;

    public MentorResponse() {
    }

    public MentorResponse(Long id, String mentorName, String mentorAvatar) {
        this.id = id;
        this.mentorName = mentorName;
        this.mentorAvatar = mentorAvatar;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMentorName() {
        return mentorName;
    }
    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }
    public String getMentorAvatar() {
        return mentorAvatar;
    }
    public void setMentorAvatar(String mentorAvatar) {
        this.mentorAvatar = mentorAvatar;
    }
}