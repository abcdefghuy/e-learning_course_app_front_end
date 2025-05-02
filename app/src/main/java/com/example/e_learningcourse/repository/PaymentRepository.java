package com.example.e_learningcourse.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.PaymentAPI;
import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.model.request.PaymentRequest;
import com.example.e_learningcourse.model.response.ApiResponse;

public class PaymentRepository extends BaseRepository{
    private PaymentAPI api;
    public PaymentRepository() {
        api = RetrofitClient.createAuthenticatedService(PaymentAPI.class);
    }
    public LiveData<ApiResponse<String>> createPayment(PaymentRequest request) {
        MutableLiveData<ApiResponse<String>> liveData = new MutableLiveData<>();
        enqueue(api.createPayment(request), liveData, String.class);
        return liveData;
    }
}
