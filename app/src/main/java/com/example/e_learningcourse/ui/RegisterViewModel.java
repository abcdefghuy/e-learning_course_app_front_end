package com.example.e_learningcourse.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.model.response.LoginResponse;
import com.example.e_learningcourse.model.response.RegisterResponse;
import com.example.e_learningcourse.repository.AuthenticationRepository;

public class RegisterViewModel {
    private final AuthenticationRepository repository;
    public MutableLiveData<String> email = new MutableLiveData<>("");
    public MutableLiveData<String> password = new MutableLiveData<>("");

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<RegisterResponse> registerResponse = new MutableLiveData<>();

    public RegisterViewModel() {
        repository = new AuthenticationRepository();
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<RegisterResponse> getLoginResponse() {
        return registerResponse;
    }

    public void login() {
        isLoading.setValue(true);

        repository.login(email.getValue(), password.getValue()).observeForever(response -> {
            isLoading.setValue(false);
            loginResponse.setValue(response);
        });
    }
}
