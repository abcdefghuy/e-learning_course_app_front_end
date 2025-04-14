package com.example.e_learningcourse.ui.auth.forgotPassword;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.repository.AuthenticationRepository;
import com.example.e_learningcourse.ui.base.BaseViewModel;
import com.example.e_learningcourse.utils.ValidationUtils;

public class ForgetPasswordViewModel extends BaseViewModel {
    private final AuthenticationRepository repository;

    public final MutableLiveData<String> email = new MutableLiveData<>(null);
    public final MutableLiveData<String> emailError = new MutableLiveData<>(null);

    private final MutableLiveData<ApiResponse<Void>> forgetPasswordResponse = new MutableLiveData<>();

    public ForgetPasswordViewModel() {
        repository = new AuthenticationRepository();

        email.observeForever(value ->
                emailError.setValue(ValidationUtils.validateEmail(value))
        );
    }

    public LiveData<ApiResponse<Void>> getForgetPasswordResponse() {
        return forgetPasswordResponse;
    }

    public void forgetPassword() {
        if (!isValidInput()) return;

        setLoading(true);
        repository.forgetPassword(email.getValue()).observeForever(response -> {
            setLoading(false);
            forgetPasswordResponse.setValue(response);
        });
    }

    private boolean isValidInput() {
        String validation = ValidationUtils.validateEmail(email.getValue());
        emailError.setValue(validation);
        return validation == null;
    }
}