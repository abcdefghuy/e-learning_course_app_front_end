package com.example.e_learningcourse.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.CourseAPI;
import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.model.response.ContinueCourseResponse;
import com.example.e_learningcourse.model.response.CourseDetailResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseRepository {
    private CourseAPI api;

    public CourseRepository() {
        api = RetrofitClient.createAuthenticatedService(CourseAPI.class);
    }

    public LiveData<PaginateResponse<CourseResponse>> getTopSellingCourses() {
        MutableLiveData<PaginateResponse<CourseResponse>> topCourseLiveData = new MutableLiveData<>();
        api.getTopCourseSelling().enqueue(new Callback<PaginateResponse<CourseResponse>>() {
            @Override
            public void onResponse(Call<PaginateResponse<CourseResponse>> call, Response<PaginateResponse<CourseResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    topCourseLiveData.setValue(response.body());
                } else {
                    topCourseLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PaginateResponse<CourseResponse>> call, Throwable t) {
                topCourseLiveData.setValue(null);
            }
        });
        return topCourseLiveData;
    }

    public LiveData<PaginateResponse<CourseResponse>> getCourses(int page, int size) {
        MutableLiveData<PaginateResponse<CourseResponse>> coursesLiveData = new MutableLiveData<>();

        api.getAllCourse(page, size).enqueue(new Callback<PaginateResponse<CourseResponse>>() {
            @Override
            public void onResponse(Call<PaginateResponse<CourseResponse>> call, Response<PaginateResponse<CourseResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SUCCESS", "Dữ liệu trả về: " + new Gson().toJson(response.body()));
                    coursesLiveData.setValue(response.body());
                } else {
                    Log.e("API_ERROR", "Lỗi response: " + response.code() + " - " + response.message());
                    coursesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PaginateResponse<CourseResponse>> call, Throwable t) {
                Log.e("API_FAILURE", "Lỗi kết nối: " + t.getMessage());
                coursesLiveData.setValue(null);
            }
        });
        return coursesLiveData;
    }

    public LiveData<CourseDetailResponse> getCourseDetails(Long courseId) {
        MutableLiveData<CourseDetailResponse> courseDetailsLiveData = new MutableLiveData<>();
        api.getCourseDetails(courseId).enqueue(new Callback<CourseDetailResponse>() {
            @Override
            public void onResponse(Call<CourseDetailResponse> call, Response<CourseDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SUCCESS", "Dữ liệu trả về: " + new Gson().toJson(response.body()));
                    courseDetailsLiveData.setValue(response.body());
                } else {
                    Log.e("API_ERROR", "Lỗi response: " + response.code() + " - " + response.message());
                    courseDetailsLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<CourseDetailResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Lỗi kết nối: " + t.getMessage());
                courseDetailsLiveData.setValue(null);
            }
        });
        return courseDetailsLiveData;
    }
    public LiveData<ContinueCourseResponse> getCourseContinueLatest() {
        MutableLiveData<ContinueCourseResponse> courseDetailsLiveData = new MutableLiveData<>();
        api.getContinueCourseLatest().enqueue(new Callback<ContinueCourseResponse>() {
            @Override
            public void onResponse(Call<ContinueCourseResponse> call, Response<ContinueCourseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SUCCESS", "Dữ liệu trả về: " + new Gson().toJson(response.body()));
                    courseDetailsLiveData.setValue(response.body());
                } else {
                    Log.e("API_ERROR", "Lỗi response: " + response.code() + " - " + response.message());
                    courseDetailsLiveData.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<ContinueCourseResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Lỗi kết nối: " + t.getMessage());
                courseDetailsLiveData.setValue(null);
            }
        });
        return courseDetailsLiveData;
    }
    public LiveData<PaginateResponse<ContinueCourseResponse>> getContinueCourse(int page, int size) {
        MutableLiveData<PaginateResponse<ContinueCourseResponse>> coursesLiveData = new MutableLiveData<>();

        api.getContinueCourse(page, size).enqueue(new Callback<PaginateResponse<ContinueCourseResponse>>() {
            @Override
            public void onResponse(Call<PaginateResponse<ContinueCourseResponse>> call, Response<PaginateResponse<ContinueCourseResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SUCCESS", "Dữ liệu trả về: " + new Gson().toJson(response.body()));
                    coursesLiveData.setValue(response.body());
                } else {
                    Log.e("API_ERROR", "Lỗi response: " + response.code() + " - " + response.message());
                    coursesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PaginateResponse<ContinueCourseResponse>> call, Throwable t) {
                Log.e("API_FAILURE", "Lỗi kết nối: " + t.getMessage());
                coursesLiveData.setValue(null);
            }
        });
        return coursesLiveData;
    }
    public LiveData<PaginateResponse<ContinueCourseResponse>> getCompletedCourse(int page, int size) {
        MutableLiveData<PaginateResponse<ContinueCourseResponse>> coursesLiveData = new MutableLiveData<>();

        api.getCompletedCourse(page, size).enqueue(new Callback<PaginateResponse<ContinueCourseResponse>>() {
            @Override
            public void onResponse(Call<PaginateResponse<ContinueCourseResponse>> call, Response<PaginateResponse<ContinueCourseResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SUCCESS", "Dữ liệu trả về: " + new Gson().toJson(response.body()));
                    coursesLiveData.setValue(response.body());
                } else {
                    Log.e("API_ERROR", "Lỗi response: " + response.code() + " - " + response.message());
                    coursesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PaginateResponse<ContinueCourseResponse>> call, Throwable t) {
                Log.e("API_FAILURE", "Lỗi kết nối: " + t.getMessage());
                coursesLiveData.setValue(null);
            }
        });
        return coursesLiveData;
    }
}
