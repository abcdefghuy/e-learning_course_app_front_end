package com.example.e_learningcourse.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.api.RetrofitClient;
import com.example.e_learningcourse.api.UserAPI;
import com.example.e_learningcourse.model.request.UserRequest;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.CertificateResponse;
import com.example.e_learningcourse.model.response.UserDetailResponse;

public class UserRepository extends BaseRepository {
    private final UserAPI userAPI;
    public UserRepository() {
        userAPI = RetrofitClient.createAuthenticatedService(UserAPI.class);
    }

    public LiveData<ApiResponse<UserDetailResponse>> getUserInfo() {
        MutableLiveData<ApiResponse<UserDetailResponse>> userInfoLiveData = new MutableLiveData<>();
        enqueue(userAPI.getUserInfo(), userInfoLiveData, UserDetailResponse.class);
        return userInfoLiveData;
    }

    public LiveData<ApiResponse<Void>> updateUserInfo(UserRequest userRequest) {
        MutableLiveData<ApiResponse<Void>> updateUserInfoLiveData = new MutableLiveData<>();
        enqueue(userAPI.updateInfoUser(userRequest), updateUserInfoLiveData, Void.class);
        return updateUserInfoLiveData;
    }
    public LiveData<ApiResponse<CertificateResponse>> getCertificate(Long courseId) {
        MutableLiveData<ApiResponse<CertificateResponse>> certificateLiveData = new MutableLiveData<>();
        enqueue(userAPI.getCertificate(courseId), certificateLiveData, Void.class);
        return certificateLiveData;
    }
}
