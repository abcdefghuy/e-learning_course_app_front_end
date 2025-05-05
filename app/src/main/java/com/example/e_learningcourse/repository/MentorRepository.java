package com.example.e_learningcourse.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.MentorAPI;
import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.MentorResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.google.gson.reflect.TypeToken;

public class MentorRepository extends BaseRepository {
    private MentorAPI mentorAPI;
    public MentorRepository() {
        mentorAPI = RetrofitClient.createAuthenticatedService(MentorAPI.class);
    }
    public LiveData<ApiResponse<PaginateResponse<MentorResponse>>> getTopMentors() {
        MutableLiveData<ApiResponse<PaginateResponse<MentorResponse>>> topMentorLiveData = new MutableLiveData<>();
        enqueue(mentorAPI.getTopMentors(), topMentorLiveData, new TypeToken<ApiResponse<PaginateResponse<MentorResponse>>>() {}.getType());
        return topMentorLiveData;
    }
    public LiveData<ApiResponse<PaginateResponse<MentorResponse>>> getAllMentors() {
        MutableLiveData<ApiResponse<PaginateResponse<MentorResponse>>> allMentorLiveData = new MutableLiveData<>();
        enqueue(mentorAPI.getAllMentors(), allMentorLiveData, new TypeToken<ApiResponse<PaginateResponse<MentorResponse>>>() {}.getType());
        return allMentorLiveData;
    }
}
