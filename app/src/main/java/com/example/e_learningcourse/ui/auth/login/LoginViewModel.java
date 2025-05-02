package com.example.e_learningcourse.ui.auth.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.LoginResponse;
import com.example.e_learningcourse.model.response.UserDetailResponse;
import com.example.e_learningcourse.repository.AuthenticationRepository;
import com.example.e_learningcourse.repository.UserRepository;
import com.example.e_learningcourse.ui.base.BaseViewModel;
import com.example.e_learningcourse.utils.ValidationUtils;

public class LoginViewModel extends BaseViewModel {
    private final AuthenticationRepository repository;
    private final UserRepository userRepository;

    public final MutableLiveData<String> email = new MutableLiveData<>(null);
    public final MutableLiveData<String> password = new MutableLiveData<>(null);
    public final MutableLiveData<String> emailError = new MutableLiveData<>(null);
    public final MutableLiveData<String> passwordError = new MutableLiveData<>(null);

    private final MutableLiveData<ApiResponse<LoginResponse>> loginResponse = new MutableLiveData<>();

    public LoginViewModel() {
        repository = new AuthenticationRepository();
        userRepository = new UserRepository();

        email.observeForever(value -> emailError.setValue(ValidationUtils.validateEmail(value)));
        password.observeForever(value -> passwordError.setValue(ValidationUtils.validatePassword(value)));
    }

    public LiveData<ApiResponse<LoginResponse>> getLoginResponse() {
        return loginResponse;
    }

    public void login() {
        if (!isValidInput()) return;

        setLoading(true);
        repository.login(email.getValue(), password.getValue()).observeForever(response -> {
            setLoading(false);
            loginResponse.setValue(response);

            if (!response.isSuccess()) {
                setError(response.getMessage());
            }
        });
    }

    private boolean isValidInput() {
        String emailValidation = ValidationUtils.validateEmail(email.getValue());
        String passwordValidation = ValidationUtils.validatePassword(password.getValue());

        emailError.setValue(emailValidation);
        passwordError.setValue(passwordValidation);

        return emailValidation == null && passwordValidation == null;
    }

    public LiveData<ApiResponse<UserDetailResponse>> fetchUserInfo() {
        return userRepository.getUserInfo();
    }
}