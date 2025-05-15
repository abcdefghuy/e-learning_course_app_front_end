package com.example.e_learningcourse.ui.auth.verifyOtp;

import android.os.CountDownTimer;

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
    private String action;

    public void setAction(String action) {
        this.action = action;
    }

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
        repository.verify(email, otp, action).observeForever(response -> {
            setLoading(false);
            verifyResponse.setValue(response);

            if (!response.isSuccess()) {
                setError(response.getMessage());
            }
        });
    }


    // Thời gian còn lại (giây)
    private final MutableLiveData<Integer> remainingTime = new MutableLiveData<>();
    public LiveData<Integer> getRemainingTime() {
        return remainingTime;
    }

    // Trạng thái hiển thị nút resend
    private final MutableLiveData<Boolean> isResendVisible = new MutableLiveData<>(false);
    public LiveData<Boolean> getIsResendVisible() {
        return isResendVisible;
    }

    private CountDownTimer countDownTimer;

    // Bắt đầu đếm ngược 14 phút (840000 ms)
    public void startCountdown() {
        // Reset hiển thị resend
        isResendVisible.setValue(false);

        // Hủy nếu đang chạy
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(840000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                remainingTime.postValue(seconds);
            }

            @Override
            public void onFinish() {
                isResendVisible.postValue(true);
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private final MutableLiveData<ApiResponse<Void>> resendResponse = new MutableLiveData<>();
    public LiveData<ApiResponse<Void>> getResendResponse() {
        return resendResponse;
    }

    public void resendOtp() {
        if (email == null || email.isEmpty()) {
            setError("Email không hợp lệ.");
            return;
        }

        setLoading(true);
        repository.resendVerificationCode(email).observeForever(response -> {
            setLoading(false);
            resendResponse.setValue(response);

            if (response.isSuccess()) {
                startCountdown(); // reset đếm ngược khi gửi lại thành công
            } else {
                setError(response.getMessage());
            }
        });
    }
}
