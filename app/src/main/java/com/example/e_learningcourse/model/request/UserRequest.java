package com.example.e_learningcourse.model.request;

import java.time.LocalDateTime;
import java.util.Date;

public class UserRequest {
    private String email;
    private String fullName;
    private String phone;
    private String gender;
    private String birthday;
    private String avatarUrl;

    public UserRequest() {
    }

    public UserRequest(String email, String fullName, String phone, String gender, String birthday, String avatarUrl) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.gender = gender;
        this.birthday = birthday;
        this.avatarUrl = avatarUrl;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
