package com.example.e_learningcourse.api;

import com.example.e_learningcourse.model.request.BookmarkRequest;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookmarkAPI {
    @GET("api/v1/bookmarks/user")
    Call<ApiResponse<PaginateResponse<CourseResponse>>> getBookmarkByUser(@Query("page") int page, @Query("size") int size);
    @POST("api/v1/bookmarks")
    Call<ApiResponse<Void>> addBookmark(@Body BookmarkRequest bookmarkRequest);

    @DELETE("api/v1/bookmarks/{courseId}")
    Call<ApiResponse<Void>> removeBookmark(@Path("courseId") Long courseId);
}
