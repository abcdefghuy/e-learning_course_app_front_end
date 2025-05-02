package com.example.e_learningcourse.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.CourseAPI;
import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.ContinueCourseResponse;
import com.example.e_learningcourse.model.response.CourseDetailResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseRepository extends BaseRepository {
    private CourseAPI api;

    public CourseRepository() {
        api = RetrofitClient.createAuthenticatedService(CourseAPI.class);
    }

    public LiveData<ApiResponse<PaginateResponse<CourseResponse>>> getTopSellingCourses() {
        MutableLiveData<ApiResponse<PaginateResponse<CourseResponse>>> topCourseLiveData = new MutableLiveData<>();
        enqueue(api.getTopCourseSelling(), topCourseLiveData, new TypeToken<ApiResponse<PaginateResponse<CourseResponse>>>() {}.getType());
        return topCourseLiveData;
    }

    public LiveData<ApiResponse<PaginateResponse<CourseResponse>>> getCourses(int page, int size) {
        MutableLiveData<ApiResponse<PaginateResponse<CourseResponse>>> coursesLiveData = new MutableLiveData<>();
        enqueue(api.getAllCourse(page, size), coursesLiveData, new TypeToken<ApiResponse<PaginateResponse<CourseResponse>>>() {}.getType());
        return coursesLiveData;
    }

    public LiveData<ApiResponse<CourseDetailResponse>> getCourseDetails(Long courseId, Long userId) {
        MutableLiveData<ApiResponse<CourseDetailResponse>> courseDetailsLiveData = new MutableLiveData<>();
        enqueue(api.getCourseDetails(courseId, userId), courseDetailsLiveData, CourseDetailResponse.class);
        return courseDetailsLiveData;
    }
    public LiveData<ApiResponse<ContinueCourseResponse>> getCourseContinueLatest() {
        MutableLiveData<ApiResponse<ContinueCourseResponse>> courseDetailsLiveData = new MutableLiveData<>();
        enqueue(api.getContinueCourseLatest(), courseDetailsLiveData, ContinueCourseResponse.class);
        return courseDetailsLiveData;
    }
    public LiveData<ApiResponse<PaginateResponse<ContinueCourseResponse>>> getContinueCourse(int page, int size) {
        MutableLiveData<ApiResponse<PaginateResponse<ContinueCourseResponse>>> coursesLiveData = new MutableLiveData<>();
        enqueue(api.getContinueCourse(page, size), coursesLiveData, new TypeToken<PaginateResponse<ContinueCourseResponse>>() {}.getType());
        return coursesLiveData;
    }
    public LiveData<ApiResponse<PaginateResponse<ContinueCourseResponse>>> getCompletedCourse(int page, int size) {
        MutableLiveData<ApiResponse<PaginateResponse<ContinueCourseResponse>>> coursesLiveData = new MutableLiveData<>();
        enqueue(api.getCompletedCourse(page, size), coursesLiveData, new TypeToken<ApiResponse<PaginateResponse<ContinueCourseResponse>>>() {}.getType());
        return coursesLiveData;
    }
    public LiveData<ApiResponse<PaginateResponse<CourseResponse>>> searchCourse(String keyword, int page, int size) {
        MutableLiveData<ApiResponse<PaginateResponse<CourseResponse>>> searchLiveData = new MutableLiveData<>();
        enqueue(api.searchCourse(keyword, page, size), searchLiveData, new TypeToken<ApiResponse<PaginateResponse<CourseResponse>>>() {}.getType());
        return searchLiveData;
    }

    public LiveData<ApiResponse<PaginateResponse<CourseResponse>>> getCoursesByCategory(String categoryName, int currentPage, int pageSize) {
        MutableLiveData<ApiResponse<PaginateResponse<CourseResponse>>> coursesLiveData = new MutableLiveData<>();
        enqueue(api.getCoursesByCategory(categoryName, currentPage, pageSize), coursesLiveData, new TypeToken<ApiResponse<PaginateResponse<CourseResponse>>>() {}.getType());
        return coursesLiveData;
    }
}
