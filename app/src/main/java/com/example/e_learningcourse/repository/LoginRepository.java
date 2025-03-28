package com.example.e_learningcourse.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.AuthenticationAPI;
import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.model.request.LoginRequest;
import com.example.e_learningcourse.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private AuthenticationAPI api;
    public LoginRepository() {
        api = RetrofitClient.getInstance().create(AuthenticationAPI.class);
    }

    public LiveData<LoginResponse> login(String email, String password) {
        MutableLiveData<LoginResponse> loginResponse = new MutableLiveData<>();
        api.login(new LoginRequest(email, password)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("Success: ", response.toString());
                if (response.isSuccessful() && response.body() != null) {
                    loginResponse.setValue(response.body());
                    Log.d("Success: ", loginResponse.getValue().toString());
                } else {
                    loginResponse.setValue(new LoginResponse());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("Failed: ", t.getMessage());
                loginResponse.setValue(new LoginResponse());
            }
        });

        return loginResponse;
    }

}
