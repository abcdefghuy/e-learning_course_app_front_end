package com.example.e_learningcourse.ui.course;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningcourse.model.response.PaginateCourseResponse;
import com.example.e_learningcourse.repository.CourseRepository;

public class CourseViewModel extends ViewModel {
    private final CourseRepository repository;
    private final MutableLiveData<PaginateCourseResponse> _courses = new MutableLiveData<>();
    private final LiveData<PaginateCourseResponse> courses = _courses;
    private int currentPage = 0;
    private final int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    public CourseViewModel() {
        repository = new CourseRepository();
    }

    public LiveData<PaginateCourseResponse> getCourses() {
        return courses;
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
}
