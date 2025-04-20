package com.example.e_learningcourse.api;

import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.LessonResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LessonAPI {
    @GET("api/v1/lessons/{courseId}")
    Call<PaginateResponse<LessonResponse>> getLessonByCourse(@Path("courseId") Long courseId, @Query("page") int page, @Query("size") int limit);
}
