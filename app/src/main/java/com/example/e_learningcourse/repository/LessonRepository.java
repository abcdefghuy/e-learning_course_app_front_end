package com.example.e_learningcourse.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.LessonAPI;
import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.LessonResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.model.response.QuizQuestionResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LessonRepository extends BaseRepository {
    private LessonAPI lessonAPI;

    public LessonRepository() {
        lessonAPI = RetrofitClient.createAuthenticatedService(LessonAPI.class);
    }
    public LiveData<ApiResponse<PaginateResponse<LessonResponse>>> getLessonByCourse(Long courseId, int page, int size){
        MutableLiveData<ApiResponse<PaginateResponse<LessonResponse>>> coursesLiveData = new MutableLiveData<>();
        enqueue(lessonAPI.getLessonByCourse(courseId, page, size), coursesLiveData, new TypeToken<ApiResponse<PaginateResponse<LessonResponse>>>() {}.getType());
        return coursesLiveData;
    }
    public LiveData<ApiResponse<Void>> updateLessonProgress(Long lessonId) {
        MutableLiveData<ApiResponse<Void>> lessonLiveData = new MutableLiveData<>();
        enqueue(lessonAPI.updateLessonProgress(lessonId), lessonLiveData, Void.class);
        return lessonLiveData;
    }
    public LiveData<ApiResponse<List<QuizQuestionResponse>>> getQuizByLesson(Long lessonId) {
        MutableLiveData<ApiResponse<List<QuizQuestionResponse>>> quizLiveData = new MutableLiveData<>();
        enqueue(lessonAPI.getQuizByLesson(lessonId), quizLiveData, new TypeToken<ApiResponse<List<QuizQuestionResponse>>>() {}.getType());
        return quizLiveData;
    }
    public LiveData<ApiResponse<PaginateResponse<LessonResponse>>> getLessonDemoByCourse(Long courseId, int page, int size){
        MutableLiveData<ApiResponse<PaginateResponse<LessonResponse>>> coursesLiveData = new MutableLiveData<>();
        enqueue(lessonAPI.getLessonDemoByCourse(courseId, page, size), coursesLiveData, new TypeToken<ApiResponse<PaginateResponse<LessonResponse>>>() {}.getType());
        return coursesLiveData;
    }
}
