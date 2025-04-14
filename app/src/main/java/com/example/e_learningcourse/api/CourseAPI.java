package com.example.e_learningcourse.api;

import com.example.e_learningcourse.model.response.PaginateCourseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CourseAPI {
    @GET("api/courses/top-selling")
    Call<PaginateCourseResponse> getTopCourseSelling();
    @GET("api/courses/all-courses")
    Call<PaginateCourseResponse> getAllCourse(@Query("page") int page, @Query("size") int limit);
}
