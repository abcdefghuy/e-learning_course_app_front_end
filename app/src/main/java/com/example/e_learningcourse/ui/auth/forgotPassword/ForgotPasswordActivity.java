package com.example.e_learningcourse.ui.auth.forgotPassword;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningcourse.databinding.ActivityForgetPasswordBinding;
import com.example.e_learningcourse.ui.auth.verifyOtp.VerifyOtpActivity;
import com.example.e_learningcourse.ui.base.BaseActivity;

public class ForgotPasswordActivity extends BaseActivity<ActivityForgetPasswordBinding, ForgetPasswordViewModel> {

    @Override
    protected ActivityForgetPasswordBinding getViewBinding() {
        return ActivityForgetPasswordBinding.inflate(getLayoutInflater());
    }

    @Override
    protected ForgetPasswordViewModel getViewModel() {
        return new ViewModelProvider(this).get(ForgetPasswordViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.setViewModel(viewModel);

        binding.btnSendCode.setOnClickListener(v -> viewModel.forgetPassword());

        viewModel.getForgetPasswordResponse().observe(this, response -> {
            if (response != null && response.isSuccess()) {
                Intent intent = new Intent(this, VerifyOtpActivity.class);
                intent.putExtra("email", viewModel.email.getValue());
                intent.putExtra("action", "reset_password");
                startActivity(intent);
                finish();
            }
        });
    }
}