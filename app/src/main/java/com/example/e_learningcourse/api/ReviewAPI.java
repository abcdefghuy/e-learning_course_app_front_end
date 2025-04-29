package com.example.e_learningcourse.api;

import com.example.e_learningcourse.model.request.ReviewRequest;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.model.response.ReviewResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewAPI {
    @GET("api/v1/reviews/course/{courseId}")
    Call<ApiResponse<PaginateResponse<ReviewResponse>>> getReviewsByCourse(@Path("courseId") Long courseId, @Query("page") int page, @Query("size") int limit);

    @POST("api/v1/reviews")
    Call<ApiResponse<Void>> createReview(@Body ReviewRequest reviewRequest);

    @PUT("api/v1/reviews/{reviewId}")
    Call<Void> updateReview(@Path("reviewId") int reviewId, @Body ReviewRequest reviewRequest);

    @DELETE("api/v1/reviews/{reviewId}")
    Call<Void> deleteReview(@Path("reviewId") int reviewId);
}
