package com.example.e_learningcourse.api;

import com.example.e_learningcourse.model.request.UserRequest;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.CertificateResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.model.response.ReviewResponse;
import com.example.e_learningcourse.model.response.UserDetailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPI {
    @GET("api/v1/users/me")
    Call<ApiResponse<UserDetailResponse>> getUserInfo();

    @PUT("api/v1/users/update")
    Call<ApiResponse<Void>> updateInfoUser(@Body UserRequest userRequest);

    @GET("api/v1/users/me/certificates")
    Call<ApiResponse<CertificateResponse>> getCertificate(@Query("courseId") Long courseId);
}
