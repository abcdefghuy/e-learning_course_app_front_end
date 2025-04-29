package com.example.e_learningcourse.ui.mycourse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.base.BaseViewModel;
import com.example.e_learningcourse.model.response.ContinueCourseResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.repository.CourseRepository;

public class ContinueCourseViewModel extends BaseViewModel {
    private final CourseRepository courseRepository;
    private final MutableLiveData<PaginateResponse<ContinueCourseResponse>> _courses = new MutableLiveData<>();
    private final MutableLiveData<ContinueCourseResponse> _courseLatest = new MutableLiveData<>();
    private final LiveData<PaginateResponse<ContinueCourseResponse>> courses = _courses;
    private int currentPage = 0;
    private final int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    public ContinueCourseViewModel() {
        courseRepository = new CourseRepository();
    }
    public LiveData<PaginateResponse<ContinueCourseResponse>> getCourses() {
        return courses;
    }
    public LiveData<ContinueCourseResponse> getCourseDetails() {
        return _courseLatest;
    }
    public void fetchCoursesInProgress() {
        courseRepository.getCourseContinueLatest().observeForever(response -> {
            if (response != null) {
                _courseLatest.setValue(response.getData());
            }
        });
    }
    public void fetchContinueCoursesNextPage() {
        if (isLoading || isLastPage) return;
        isLoading = true;
        courseRepository.getContinueCourse(currentPage, pageSize).observeForever(response -> {
            if (response != null) {
                isLastPage = response.getData().isLast();
                currentPage++;
                _courses.setValue(response.getData());
            }
            isLoading = false;
        });
    }
    public void fetchCompletedCoursesNextPage() {
        if (isLoading || isLastPage) return;
        isLoading = true;
        courseRepository.getCompletedCourse(currentPage, pageSize).observeForever(response -> {
            if (response != null) {
                isLastPage = response.getData().isLast();
                currentPage++;
                _courses.setValue(response.getData());
            }
            isLoading = false;
        });
    }

}
