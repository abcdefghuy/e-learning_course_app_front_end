package com.example.e_learningcourse.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.api.ReviewAPI;
import com.example.e_learningcourse.model.request.ReviewRequest;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.model.response.ReviewResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRepository extends BaseRepository{
    private final ReviewAPI reviewAPI;
    public  ReviewRepository() {
        reviewAPI = RetrofitClient.createAuthenticatedService(ReviewAPI.class);
    }

    public LiveData<ApiResponse<PaginateResponse<ReviewResponse>>> getReviewByLesson(Long courseId, int page, int size ) {
        MutableLiveData<ApiResponse<PaginateResponse<ReviewResponse>>> reviewsLiveData = new MutableLiveData<>();
        enqueue(reviewAPI.getReviewsByCourse(courseId, page, size), reviewsLiveData, new TypeToken<ApiResponse<PaginateResponse<ReviewResponse>>>() {}.getType());
        return reviewsLiveData;
    }

    public LiveData<ApiResponse<Void>> submitReview(ReviewRequest reviewRequest) {
        MutableLiveData<ApiResponse<Void>> responseLiveData = new MutableLiveData<>();
        enqueue(reviewAPI.createReview(reviewRequest), responseLiveData, Void.class);
        return responseLiveData;
    }
}
