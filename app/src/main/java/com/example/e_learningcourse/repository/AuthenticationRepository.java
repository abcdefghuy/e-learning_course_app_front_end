package com.example.e_learningcourse.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.AuthenticationAPI;
import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.model.request.LoginRequest;
import com.example.e_learningcourse.model.request.RegisterRequest;
import com.example.e_learningcourse.model.response.LoginResponse;
import com.example.e_learningcourse.model.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationRepository {
    private AuthenticationAPI api;
    public AuthenticationRepository() {
        api = RetrofitClient.getInstance().create(AuthenticationAPI.class);
    }

    public LiveData<LoginResponse> login(String email, String password) {
        MutableLiveData<LoginResponse> loginResponse = new MutableLiveData<>();
        api.login(new LoginRequest(email, password)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loginResponse.setValue(response.body());
                } else {
                    loginResponse.setValue(new LoginResponse());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("Error: ", t.getMessage());
                loginResponse.setValue(new LoginResponse());
            }
        });

        return loginResponse;
    }

    public LiveData<RegisterResponse> register(String email, String password, String username) {
        MutableLiveData<RegisterResponse> registerResponse = new MutableLiveData<>();
        api.register(new RegisterRequest(email, password, username)).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    registerResponse.setValue(response.body());
                } else {
                    registerResponse.setValue(new RegisterResponse());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable throwable) {
                Log.d("Error: ", throwable.getMessage());
                registerResponse.setValue(new RegisterResponse());
            }
        });

        return registerResponse;
    }

}
