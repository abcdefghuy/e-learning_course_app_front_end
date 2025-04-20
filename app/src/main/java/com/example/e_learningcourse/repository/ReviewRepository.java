package com.example.e_learningcourse.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.api.ReviewAPI;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.model.response.ReviewResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRepository {
    private final ReviewAPI reviewAPI;
    public  ReviewRepository() {
        reviewAPI = RetrofitClient.createAuthenticatedService(ReviewAPI.class);
    }

    public LiveData<PaginateResponse<ReviewResponse>> getReviewByLesson(Long courseId,int page, int size ) {
        MutableLiveData<PaginateResponse<ReviewResponse>> reviewsLiveData = new MutableLiveData<>();

        reviewAPI.getReviewsByCourse(courseId, page, size).enqueue(new Callback<PaginateResponse<ReviewResponse>>() {
            @Override
            public void onResponse(Call<PaginateResponse<ReviewResponse>> call, Response<PaginateResponse<ReviewResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SUCCESS", "Dữ liệu trả về: " + new Gson().toJson(response.body()));
                    reviewsLiveData.setValue(response.body());
                } else {
                    Log.e("API_ERROR", "Lỗi response: " + response.code() + " - " + response.message());
                    reviewsLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PaginateResponse<ReviewResponse>> call, Throwable t) {
                Log.e("API_FAILURE", "Lỗi kết nối: " + t.getMessage());
                reviewsLiveData.setValue(null);
            }
        });
        return reviewsLiveData;
    }

}
