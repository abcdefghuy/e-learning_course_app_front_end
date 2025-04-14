package com.example.e_learningcourse.api;

import com.example.e_learningcourse.model.request.ForgotPasswordRequest;
import com.example.e_learningcourse.model.request.LoginRequest;
import com.example.e_learningcourse.model.request.RegisterRequest;
import com.example.e_learningcourse.model.request.ResetPasswordRequest;
import com.example.e_learningcourse.model.request.VerifyRequest;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface AuthenticationAPI {
    @POST("auth/login")
    Call<ApiResponse<LoginResponse>> login(@Body LoginRequest request);

    @POST("auth/signup")
    Call<ApiResponse<Void>> register(@Body RegisterRequest request);

    @POST("auth/verify")
    Call<ApiResponse<Void>> verify(@Body VerifyRequest request);

    @POST("auth/forgot-password")
    Call<ApiResponse<Void>> forgetPassword(@Body ForgotPasswordRequest request);

    @POST("auth/reset-password")
    Call<ApiResponse<Void>> resetPassword(@Body ResetPasswordRequest request);
}
