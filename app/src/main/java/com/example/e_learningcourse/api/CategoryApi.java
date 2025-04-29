package com.example.e_learningcourse.api;

import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.CategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryApi {
    @GET("api/categories")
    Call<ApiResponse<List<CategoryResponse>>> getCategories();
}
