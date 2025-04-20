package com.example.e_learningcourse.ui.course;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningcourse.model.response.CourseDetailResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.repository.CourseRepository;

public class CourseViewModel extends ViewModel {
    private final CourseRepository repository;
    private final MutableLiveData<PaginateResponse<CourseResponse>> _courses = new MutableLiveData<>();
    private final MutableLiveData<CourseDetailResponse> _courseDetails = new MutableLiveData<>();
    private final LiveData<PaginateResponse<CourseResponse>> courses = _courses;
    private int currentPage = 0;
    private final int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    public CourseViewModel() {
        repository = new CourseRepository();
    }

    public LiveData<PaginateResponse<CourseResponse>> getCourses() {
        return courses;
    }
    public LiveData<CourseDetailResponse> getCourseDetails() {
        return _courseDetails;
    }

    public void resetPagination() {
        currentPage = 0;
        isLastPage = false;
        isLoading = false;
        _courses.setValue(null);
    }

    public void fetchCoursesNextPage() {
        if (isLoading || isLastPage) return;
        isLoading = true;
        repository.getCourses(currentPage, pageSize).observeForever(response -> {
            if (response != null) {
                isLastPage = response.isLast();
                currentPage++;
                _courses.setValue(response);
            }
            isLoading = false;
        });
    }

    public void fetchTopSellingCourses() {
        repository.getTopSellingCourses().observeForever(response -> {
            if (response != null) {
                _courses.setValue(response);
            }
        });
    }
    public void fetchCourseDetails(Long courseId) {
        repository.getCourseDetails(courseId).observeForever(response -> {
            if (response != null) {
                _courseDetails.setValue(response);
            }
        });
    }
}
