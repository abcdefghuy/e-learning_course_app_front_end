package com.example.e_learningcourse.utils;

import com.example.e_learningcourse.model.response.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Response;

public class ApiUtils {
    public static <T> ApiResponse<T> parseErrorResponse(Response<?> response, Class<T> clazz) {
        if (response.errorBody() == null) return null;
        try {
            String errorJson = response.errorBody().string();
            Type type = TypeToken.getParameterized(ApiResponse.class, clazz).getType();
            return new Gson().fromJson(errorJson, type);
        } catch (Exception e) {
            return new ApiResponse<>(false, 5000, "Unknown error", null);
        }
    }
}
