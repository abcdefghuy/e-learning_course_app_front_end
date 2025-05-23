package com.example.e_learningcourse.api;

import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.LessonResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.model.response.QuizQuestionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LessonAPI {
    @GET("api/v1/lessons/{courseId}")
    Call<ApiResponse<PaginateResponse<LessonResponse>>> getLessonByCourse(@Path("courseId") Long courseId, @Query("page") int page, @Query("size") int limit);
    @POST("api/v1/lessons/update-progress/{lessonId}")
    Call<ApiResponse<Void>> updateLessonProgress(@Path("lessonId") Long lessonId);
    @GET("api/lessons/{lessonId}/quiz")
    Call<ApiResponse<List<QuizQuestionResponse>>> getQuizByLesson(@Path("lessonId") Long lessonId);
    @GET("api/v1/lessons/{courseId}/demo")
    Call<ApiResponse<PaginateResponse<LessonResponse>>> getLessonDemoByCourse(@Path("courseId") Long courseId, @Query("page") int page, @Query("size") int limit);

}
