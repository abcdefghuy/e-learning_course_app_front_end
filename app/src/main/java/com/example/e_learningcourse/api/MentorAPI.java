package com.example.e_learningcourse.api;

import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.MentorResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MentorAPI {
    @GET("api/mentors/top-mentors")
    Call<ApiResponse<PaginateResponse<MentorResponse>>> getTopMentors();
    @GET("api/mentors/all")
    Call<ApiResponse<PaginateResponse<MentorResponse>>> getAllMentors();
}
