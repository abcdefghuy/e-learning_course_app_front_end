package com.example.e_learningcourse.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.CourseAPI;
import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.model.response.PaginateCourseResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseRepository {
    private CourseAPI api;

    public CourseRepository() {
        api = RetrofitClient.createService(CourseAPI.class);
    }

    public LiveData<PaginateCourseResponse> getTopSellingCourses() {
        MutableLiveData<PaginateCourseResponse> topCourseLiveData = new MutableLiveData<>();

        api.getTopCourseSelling().enqueue(new Callback<PaginateCourseResponse>() {
            @Override
            public void onResponse(Call<PaginateCourseResponse> call, Response<PaginateCourseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    topCourseLiveData.setValue(response.body());
                } else {
                    topCourseLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PaginateCourseResponse> call, Throwable t) {
                topCourseLiveData.setValue(null);
            }
        });
        return topCourseLiveData;
    }

    public LiveData<PaginateCourseResponse> getCourses(int page, int size) {
        MutableLiveData<PaginateCourseResponse> coursesLiveData = new MutableLiveData<>();

        api.getAllCourse(page, size).enqueue(new Callback<PaginateCourseResponse>() {
            @Override
            public void onResponse(Call<PaginateCourseResponse> call, Response<PaginateCourseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SUCCESS", "Dữ liệu trả về: " + new Gson().toJson(response.body()));
                    coursesLiveData.setValue(response.body());
                } else {
                    Log.e("API_ERROR", "Lỗi response: " + response.code() + " - " + response.message());
                    coursesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PaginateCourseResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Lỗi kết nối: " + t.getMessage());
                coursesLiveData.setValue(null);
            }
        });
        return coursesLiveData;
    }
}
