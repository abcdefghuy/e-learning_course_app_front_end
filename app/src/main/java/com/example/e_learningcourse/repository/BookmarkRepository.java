package com.example.e_learningcourse.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.BookmarkAPI;
import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.model.request.BookmarkRequest;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class BookmarkRepository extends BaseRepository{
    private BookmarkAPI api;
    public BookmarkRepository(){
        api = RetrofitClient.createAuthenticatedService(BookmarkAPI.class);
    }
    public LiveData<ApiResponse<PaginateResponse<CourseResponse>>> getBookmarkByUser(int page, int size){
        MutableLiveData<ApiResponse<PaginateResponse<CourseResponse>>> liveData = new MutableLiveData<>();
        Type type = new TypeToken<ApiResponse<PaginateResponse<CourseResponse>>>() {}.getType();
        enqueue(api.getBookmarkByUser(page,size), liveData, type);
        return liveData;
    }
    public LiveData<ApiResponse<Void>> addBookmark(Long courseId){
        BookmarkRequest request = new BookmarkRequest(courseId);
        MutableLiveData<ApiResponse<Void>> liveData = new MutableLiveData<>();
        enqueue(api.addBookmark(request), liveData, Void.class);
        return liveData;
    }
    public LiveData<ApiResponse<Void>> removeBookmark(Long courseId){
        MutableLiveData<ApiResponse<Void>> liveData = new MutableLiveData<>();
        enqueue(api.removeBookmark(courseId), liveData, Void.class);
        return liveData;
    }
}
