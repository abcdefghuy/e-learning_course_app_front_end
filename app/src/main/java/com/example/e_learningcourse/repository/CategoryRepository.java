package com.example.e_learningcourse.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.CategoryApi;
import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.model.response.CategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private CategoryApi api;
    public CategoryRepository() {
        api = RetrofitClient.createAuthenticatedService(CategoryApi.class);
    }
    // Add methods to fetch categories from the API
    public LiveData<List<CategoryResponse>> getAllCategories() {
        MutableLiveData<List<CategoryResponse>> categoryLiveData = new MutableLiveData<>();
        api.getCategories().enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryLiveData.setValue(response.body());
                    Log.d("CATEGORY_SUCCESS", response.body().toString());
                } else {
                    categoryLiveData.setValue(null);
                    Log.e("CATEGORY_ERROR", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                categoryLiveData.setValue(null);
                Log.e("CATEGORY_FAILURE", t.getMessage());
            }
        });

        return categoryLiveData;
    }
}
