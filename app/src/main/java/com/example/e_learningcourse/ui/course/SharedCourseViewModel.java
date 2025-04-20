package com.example.e_learningcourse.ui.course;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.base.BaseViewModel;
import com.example.e_learningcourse.model.response.CourseDetailResponse;

public class SharedCourseViewModel extends BaseViewModel {
    private final MutableLiveData<CourseDetailResponse> courseDetail = new MutableLiveData<>();

    public void setCourseDetail(CourseDetailResponse detail) {
        courseDetail.setValue(detail);
    }

    public LiveData<CourseDetailResponse> getCourseDetail() {
        return courseDetail;
    }
}

