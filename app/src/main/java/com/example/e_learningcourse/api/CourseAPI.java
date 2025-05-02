package com.example.e_learningcourse.api;

import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.ContinueCourseResponse;
import com.example.e_learningcourse.model.response.CourseDetailResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.repository.BaseRepository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CourseAPI {
    @GET("api/courses/top-selling")
    Call<ApiResponse<PaginateResponse<CourseResponse>>> getTopCourseSelling();
    @GET("api/courses/all-courses")
    Call<ApiResponse<PaginateResponse<CourseResponse>>> getAllCourse(@Query("page") int page, @Query("size") int limit);

    @GET("api/courses/{courseId}")
    Call<ApiResponse<CourseDetailResponse>> getCourseDetails(@Path("courseId") Long courseId, @Query("userId") Long userId);
    @GET("api/courses/search")
    Call<ApiResponse<PaginateResponse<CourseResponse>>> searchCourse(@Query("keyword") String keyword, @Query("page") int page, @Query("size") int limit);

    @GET("api/courses/continue")
    Call<ApiResponse<PaginateResponse<ContinueCourseResponse>>> getContinueCourse(@Query("page") int page, @Query("size") int limit);
    @GET("api/courses/continue/latest")
    Call<ApiResponse<ContinueCourseResponse>> getContinueCourseLatest();
    @GET("api/courses/completed")
    Call<ApiResponse<PaginateResponse<ContinueCourseResponse>>> getCompletedCourse(@Query("page") int page, @Query("size") int limit);
    @GET("api/courses/{categoryName}/courses")
    Call<ApiResponse<PaginateResponse<CourseResponse>>> getCoursesByCategory(@Path("categoryName") String categoryName, @Query("page") int page, @Query("size") int limit);
}
