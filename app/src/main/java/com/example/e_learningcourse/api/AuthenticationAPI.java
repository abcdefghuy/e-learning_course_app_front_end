package com.example.e_learningcourse.api;

import com.example.e_learningcourse.model.request.LoginRequest;
import com.example.e_learningcourse.model.request.RegisterRequest;
import com.example.e_learningcourse.model.response.LoginResponse;
import com.example.e_learningcourse.model.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationAPI {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("auth/signup")
    Call<RegisterResponse> register(@Body RegisterRequest request);
}
