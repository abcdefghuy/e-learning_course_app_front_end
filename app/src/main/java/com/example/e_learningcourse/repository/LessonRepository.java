package com.example.e_learningcourse.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.LessonAPI;
import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.LessonResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LessonRepository {
    private LessonAPI lessonAPI;

    public LessonRepository() {
        lessonAPI = RetrofitClient.createAuthenticatedService(LessonAPI.class);
    }
    public LiveData<PaginateResponse<LessonResponse>> getLessonByCourse(Long courseId,int page, int size){
        MutableLiveData<PaginateResponse<LessonResponse>> coursesLiveData = new MutableLiveData<>();

        lessonAPI.getLessonByCourse(courseId,page, size).enqueue(new Callback<PaginateResponse<LessonResponse>>() {
            @Override
            public void onResponse(Call<PaginateResponse<LessonResponse>> call, Response<PaginateResponse<LessonResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SUCCESS", "Dữ liệu trả về: " + new Gson().toJson(response.body()));
                    coursesLiveData.setValue(response.body());
                } else {
                    Log.e("API_ERROR", "Lỗi response: " + response.code() + " - " + response.message());
                    coursesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PaginateResponse<LessonResponse>> call, Throwable t) {
                Log.e("API_FAILURE", "Lỗi kết nối: " + t.getMessage());
                coursesLiveData.setValue(null);
            }
        });
        return coursesLiveData;

    }
}
