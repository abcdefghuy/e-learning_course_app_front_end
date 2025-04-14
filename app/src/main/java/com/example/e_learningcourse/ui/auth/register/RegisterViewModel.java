package com.example.e_learningcourse.ui.auth.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.repository.AuthenticationRepository;
import com.example.e_learningcourse.ui.base.BaseViewModel;
import com.example.e_learningcourse.utils.ValidationUtils;

public class RegisterViewModel extends BaseViewModel {

    private final AuthenticationRepository repository;

    public final MutableLiveData<String> email = new MutableLiveData<>(null);
    public final MutableLiveData<String> password = new MutableLiveData<>(null);
    public final MutableLiveData<String> confirmPassword = new MutableLiveData<>(null);

    public final MutableLiveData<String> emailError = new MutableLiveData<>(null);
    public final MutableLiveData<String> passwordError = new MutableLiveData<>(null);
    public final MutableLiveData<String> confirmPasswordError = new MutableLiveData<>(null);

    private final MutableLiveData<ApiResponse<Void>> registerResponse = new MutableLiveData<>();

    public RegisterViewModel() {
        repository = new AuthenticationRepository();

        email.observeForever(value -> emailError.setValue(ValidationUtils.validateEmail(value)));
        password.observeForever(value -> passwordError.setValue(ValidationUtils.validatePassword(value)));
        confirmPassword.observeForever(value ->
                confirmPasswordError.setValue(ValidationUtils.validateConfirmPassword(password.getValue(), value))
        );
    }

    public LiveData<ApiResponse<Void>> getRegisterResponse() {
        return registerResponse;
    }

    public void register() {
        if (!isValidInput()) return;

        setLoading(true);
        repository.register(email.getValue(), password.getValue()).observeForever(response -> {
            setLoading(false);
            registerResponse.setValue(response);

            if (!response.isSuccess()) {
                setError(response.getMessage());
            }
        });
    }

    private boolean isValidInput() {
        String emailValidation = ValidationUtils.validateEmail(email.getValue());
        String passwordValidation = ValidationUtils.validatePassword(password.getValue());
        String confirmValidation = ValidationUtils.validateConfirmPassword(password.getValue(), confirmPassword.getValue());

        emailError.setValue(emailValidation);
        passwordError.setValue(passwordValidation);
        confirmPasswordError.setValue(confirmValidation);

        return emailValidation == null && passwordValidation == null && confirmValidation == null;
    }
}