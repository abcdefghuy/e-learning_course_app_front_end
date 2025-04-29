package com.example.e_learningcourse.model.response;

import java.util.Date;

public class CertificateResponse {
    private String certificateId;
    private String userName;
    private String courseName;
    private Date createdAt;

    public CertificateResponse(String id, String userName, String courseName, Date createdAt) {
        this.certificateId = id;
        this.userName = userName;
        this.courseName = courseName;
        this.createdAt = createdAt;
    }

    public String getCertificateId() {
        return certificateId;
    }
    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
