package com.example.e_learningcourse.api;

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
    Call<PaginateResponse<CourseResponse>> getTopCourseSelling();
    @GET("api/courses/all-courses")
    Call<PaginateResponse<CourseResponse>> getAllCourse(@Query("page") int page, @Query("size") int limit);

    @GET("api/courses/{courseId}")
    Call<CourseDetailResponse> getCourseDetails(@Path("courseId") Long courseId);
    @GET("api/courses/search")
    Call<PaginateResponse<CourseResponse>> searchCourse(@Query("keyword") String keyword, @Query("page") int page, @Query("size") int limit);

    @GET("api/courses/continue")
    Call<PaginateResponse<ContinueCourseResponse>> getContinueCourse(@Query("page") int page, @Query("size") int limit);
    @GET("api/courses/continue/latest")
    Call<ContinueCourseResponse> getContinueCourseLatest();
    @GET("api/courses/completed")
    Call<PaginateResponse<ContinueCourseResponse>> getCompletedCourse(@Query("page") int page, @Query("size") int limit);
}
