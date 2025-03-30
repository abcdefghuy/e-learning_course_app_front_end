package com.example.e_learningcourse.api;

import com.example.e_learningcourse.model.request.LoginRequest;
import com.example.e_learningcourse.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface AuthenticationAPI {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}
