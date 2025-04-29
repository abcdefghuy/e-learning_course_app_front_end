package com.example.e_learningcourse.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.App;
import com.example.e_learningcourse.api.AuthenticationAPI;
import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.data.local.TokenManager;
import com.example.e_learningcourse.model.request.ForgotPasswordRequest;
import com.example.e_learningcourse.model.request.LoginRequest;
import com.example.e_learningcourse.model.request.RegisterRequest;
import com.example.e_learningcourse.model.request.ResetPasswordRequest;
import com.example.e_learningcourse.model.request.VerifyRequest;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.LoginResponse;
import com.example.e_learningcourse.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationRepository extends BaseRepository {
    private final AuthenticationAPI api;

    public AuthenticationRepository() {
        this.api = RetrofitClient.createUnauthenticatedService(AuthenticationAPI.class);
    }

    public LiveData<ApiResponse<LoginResponse>> login(String email, String password) {
        MutableLiveData<ApiResponse<LoginResponse>> liveData = new MutableLiveData<>();
        enqueue(api.login(new LoginRequest(email, password)), liveData, LoginResponse.class);
        liveData.observeForever(response -> {
            if (response != null && response.isSuccess() && response.getData() != null) {
                String token = response.getData().getToken();
                TokenManager.getInstance(App.getContext()).saveToken(token);
                TokenManager.getInstance(App.getContext()).saveExpiredToken(String.valueOf(response.getData().getExpiresIn()));
            }
        });
        return liveData;
    }

    public LiveData<ApiResponse<Void>> register(String email, String password) {
        MutableLiveData<ApiResponse<Void>> liveData = new MutableLiveData<>();
        enqueue(api.register(new RegisterRequest(email, password)), liveData, Void.class);
        return liveData;
    }

    public LiveData<ApiResponse<Void>> verify(String email, String code) {
        MutableLiveData<ApiResponse<Void>> liveData = new MutableLiveData<>();
        enqueue(api.verify(new VerifyRequest(email, code)), liveData, Void.class);
        return liveData;
    }

    public LiveData<ApiResponse<Void>> forgetPassword(String email) {
        MutableLiveData<ApiResponse<Void>> liveData = new MutableLiveData<>();
        enqueue(api.forgetPassword(new ForgotPasswordRequest(email)), liveData, Void.class);
        return liveData;
    }

    public LiveData<ApiResponse<Void>> resetPassword(String email, String password) {
        MutableLiveData<ApiResponse<Void>> liveData = new MutableLiveData<>();
        enqueue(api.resetPassword(new ResetPasswordRequest(email, password)), liveData, Void.class);
        return liveData;
    }
}