package com.example.e_learningcourse.model.response;

import android.os.Parcel;
import android.os.Parcelable;

public class LessonResponse implements Parcelable {
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

    // Implement Parcelable methods

    protected LessonResponse(Parcel in) {
        if (in.readByte() == 0) {
            lessonId = null;
        } else {
            lessonId = in.readLong();
        }
        if (in.readByte() == 0) {
            courseId = null;
        } else {
            courseId = in.readLong();
        }
        lessonName = in.readString();
        lessonDescription = in.readString();
        status = in.readString();
        duration = in.readInt();
        lessonVideoUrl = in.readString();
        lessonOrder = in.readInt();
    }

    public static final Creator<LessonResponse> CREATOR = new Creator<LessonResponse>() {
        @Override
        public LessonResponse createFromParcel(Parcel in) {
            return new LessonResponse(in);
        }

        @Override
        public LessonResponse[] newArray(int size) {
            return new LessonResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (lessonId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(lessonId);
        }
        if (courseId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(courseId);
        }
        dest.writeString(lessonName);
        dest.writeString(lessonDescription);
        dest.writeString(status);
        dest.writeInt(duration);
        dest.writeString(lessonVideoUrl);
        dest.writeInt(lessonOrder);
    }

    // Getters and Setters...

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
