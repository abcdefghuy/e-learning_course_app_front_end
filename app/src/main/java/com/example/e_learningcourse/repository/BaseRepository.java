package com.example.e_learningcourse.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseRepository {
    protected <T> void enqueue(Call<ApiResponse<T>> call, MutableLiveData<ApiResponse<T>> liveData, Class<T> clazz) {
        call.enqueue(new Callback<ApiResponse<T>>() {
            @Override
            public void onResponse(Call<ApiResponse<T>> call, Response<ApiResponse<T>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(ApiUtils.parseErrorResponse(response, clazz));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<T>> call, Throwable t) {
                liveData.setValue(new ApiResponse<>(false, 500, "Lỗi mạng: " + t.getMessage(), null));
            }
        });
    }
}
