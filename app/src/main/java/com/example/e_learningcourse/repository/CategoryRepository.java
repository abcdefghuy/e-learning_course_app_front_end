package com.example.e_learningcourse.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.CategoryApi;
import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.CategoryResponse;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository extends BaseRepository {
    private CategoryApi api;
    public CategoryRepository() {
        api = RetrofitClient.createAuthenticatedService(CategoryApi.class);
    }
    // Add methods to fetch categories from the API
    public LiveData<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        MutableLiveData<ApiResponse<List<CategoryResponse>>> categoryLiveData = new MutableLiveData<>();
        enqueue(api.getCategories(), categoryLiveData, new TypeToken<ApiResponse<List<CategoryResponse>>>() {}.getType());
        return categoryLiveData;
    }
}
