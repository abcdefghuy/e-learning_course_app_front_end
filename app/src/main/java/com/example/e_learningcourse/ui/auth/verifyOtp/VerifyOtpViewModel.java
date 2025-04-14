package com.example.e_learningcourse.ui.auth.verifyOtp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.repository.AuthenticationRepository;
import com.example.e_learningcourse.ui.base.BaseViewModel;
import com.example.e_learningcourse.utils.ValidationUtils;

public class VerifyOtpViewModel extends BaseViewModel {

    private final AuthenticationRepository repository;
    public final MutableLiveData<String> otpCode = new MutableLiveData<>();

    public final LiveData<Boolean> isOtpComplete = Transformations.map(otpCode,
            code -> code != null && code.length() == 6);

    private final MutableLiveData<ApiResponse<Void>> verifyResponse = new MutableLiveData<>();
    private String email;

    public VerifyOtpViewModel() {
        this.repository = new AuthenticationRepository();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public LiveData<ApiResponse<Void>> getVerifyResponse() {
        return verifyResponse;
    }

    public void verify() {
        String otp = otpCode.getValue();

        String otpError = ValidationUtils.validateOtp(otp);
        if (otpError != null) {
            setError(otpError);
            return;
        }

        setLoading(true);
        repository.verify(email, otp).observeForever(response -> {
            setLoading(false);
            verifyResponse.setValue(response);

            if (!response.isSuccess()) {
                setError(response.getMessage());
            }
        });
    }
}
