package com.example.e_learningcourse.api;

import com.example.e_learningcourse.model.request.PaymentRequest;
import com.example.e_learningcourse.model.response.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentAPI {
    @POST("api/payment/create")
    Call<ApiResponse<String>> createPayment(@Body PaymentRequest request);
}
