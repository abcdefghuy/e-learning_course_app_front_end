package com.example.e_learningcourse.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.e_learningcourse.model.request.UserRequest;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.CertificateResponse;
import com.example.e_learningcourse.model.response.UserDetailResponse;
import com.example.e_learningcourse.repository.UserRepository;
import com.example.e_learningcourse.ui.base.BaseViewModel;

public class UserViewModel extends BaseViewModel {
    private UserRepository userRepository;
    private final MutableLiveData<ApiResponse<UserDetailResponse>> _userInfo = new MutableLiveData<>();
    private final LiveData<ApiResponse<UserDetailResponse>> userInfo = _userInfo;

    private final MutableLiveData<ApiResponse<CertificateResponse>> _certificate = new MutableLiveData<>();
    private final LiveData<ApiResponse<CertificateResponse>> certificate = _certificate;

    public LiveData<ApiResponse<CertificateResponse>> getCertificate() {
        return certificate;
    }
    private boolean isEdit = false;

    public UserViewModel() {
        userRepository = new UserRepository();
    }

    public LiveData<ApiResponse<UserDetailResponse>> getUserInfo() {
        return userInfo;
    }


    public void setUserInfo(ApiResponse<UserDetailResponse> userInfo) {
        _userInfo.setValue(userInfo);
    }

    public void fetchUserInfo() {
        if (_userInfo.getValue() == null || _userInfo.getValue().getData() == null || isEdit) {
            userRepository.getUserInfo().observeForever(response -> {
                if (response != null) {
                    _userInfo.setValue(response);
                }
            });
        }
    }

    public void updateUserInfo(UserRequest userRequest) {
        setLoading(true);
        userRepository.updateUserInfo(userRequest).observeForever(response -> {
            setLoading(false);
            if (response != null && response.isSuccess()) {
                isEdit = true;
                fetchUserInfo();
            } else if (response != null) {
                setError(response.getMessage());
            }
        });
    }
    public void fetchCertificate(Long courseId) {
        if (_certificate.getValue() == null || _certificate.getValue().getData() == null) {
            userRepository.getCertificate(courseId).observeForever(response -> {
                if (response != null) {
                    _certificate.setValue(response);
                }
            });
        }
    }

}
