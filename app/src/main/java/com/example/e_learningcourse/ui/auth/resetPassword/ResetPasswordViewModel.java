package com.example.e_learningcourse.ui.auth.resetPassword;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.repository.AuthenticationRepository;
import com.example.e_learningcourse.ui.base.BaseViewModel;
import com.example.e_learningcourse.utils.ValidationUtils;

public class ResetPasswordViewModel extends BaseViewModel {
    private final AuthenticationRepository repository;

    public final MutableLiveData<String> password = new MutableLiveData<>(null);
    public final MutableLiveData<String> confirmPassword = new MutableLiveData<>(null);
    public final MutableLiveData<String> passwordError = new MutableLiveData<>(null);
    public final MutableLiveData<String> confirmPasswordError = new MutableLiveData<>(null);
    private final MutableLiveData<ApiResponse<Void>> resetPasswordResponse = new MutableLiveData<>();

    private String email;

    public ResetPasswordViewModel() {
        this.repository = new AuthenticationRepository();

        password.observeForever(value ->
                passwordError.setValue(ValidationUtils.validatePassword(value))
        );
        confirmPassword.observeForever(value ->
                confirmPasswordError.setValue(
                        ValidationUtils.validateConfirmPassword(password.getValue(), value)
                )
        );
    }

    public LiveData<ApiResponse<Void>> getResetPasswordResponse() {
        return resetPasswordResponse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void resetPassword() {
        if (!isValidInput()) return;

        setLoading(true);
        repository.resetPassword(email, password.getValue()).observeForever(response -> {
            setLoading(false);
            resetPasswordResponse.setValue(response);

            if (!response.isSuccess()) {
                setError(response.getMessage());
            }
        });
    }

    private boolean isValidInput() {
        String passError = ValidationUtils.validatePassword(password.getValue());
        String confirmError = ValidationUtils.validateConfirmPassword(password.getValue(), confirmPassword.getValue());

        passwordError.setValue(passError);
        confirmPasswordError.setValue(confirmError);

        return passError == null && confirmError == null;
    }
}
