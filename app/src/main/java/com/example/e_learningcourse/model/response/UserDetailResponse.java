package com.example.e_learningcourse.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class UserDetailResponse {
    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String avatarUrl;
    private Date dateOfBirth;
    private String gender;

    public UserDetailResponse() {
    }

    public UserDetailResponse(Long id, String email, String fullName, String phoneNumber, String avatarUrl, Date dateOfBirth, String gender) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.avatarUrl = avatarUrl;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
