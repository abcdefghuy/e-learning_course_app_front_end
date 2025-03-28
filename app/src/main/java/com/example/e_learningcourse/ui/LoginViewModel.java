package com.example.e_learningcourse.ui;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningcourse.model.response.LoginResponse;
import com.example.e_learningcourse.repository.LoginRepository;

public class LoginViewModel extends ViewModel {
    private final LoginRepository repository;
    public MutableLiveData<String> email = new MutableLiveData<>("");
    public MutableLiveData<String> password = new MutableLiveData<>("");
    private MutableLiveData<Integer> isLoading = new MutableLiveData<>(View.GONE);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<LoginResponse> loginResponse = new MutableLiveData<>();

    public LoginViewModel() {
        repository = new LoginRepository();
    }

    public LiveData<Integer> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<LoginResponse> getLoginResponse() {
        return loginResponse;
    }

    public void login() {
        isLoading.setValue(View.VISIBLE);

        repository.login(email.getValue(), password.getValue()).observeForever(response -> {
            isLoading.setValue(View.GONE);
            loginResponse.setValue(response);
        });
    }
}
