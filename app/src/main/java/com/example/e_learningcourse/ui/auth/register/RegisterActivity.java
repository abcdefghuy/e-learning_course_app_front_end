package com.example.e_learningcourse.ui.auth.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningcourse.databinding.ActivityRegisterBinding;
import com.example.e_learningcourse.ui.auth.login.LoginActivity;
import com.example.e_learningcourse.ui.auth.verifyOtp.VerifyOtpActivity;
import com.example.e_learningcourse.ui.base.BaseActivity;
import com.example.e_learningcourse.ui.common.PasswordToggleHelper;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> {

    @Override
    protected ActivityRegisterBinding getViewBinding() {
        return ActivityRegisterBinding.inflate(getLayoutInflater());
    }

    @Override
    protected RegisterViewModel getViewModel() {
        return new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.setViewModel(viewModel);

        binding.btnRegister.setOnClickListener(v -> viewModel.register());

        viewModel.getRegisterResponse().observe(this, response -> {
            if (response != null && response.isSuccess()) {
                Intent intent = new Intent(this, VerifyOtpActivity.class);
                intent.putExtra("email", viewModel.email.getValue());
                intent.putExtra("action", "register");
                startActivity(intent);
                finish();
            }
        });

        binding.btnLogin.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class))
        );

        PasswordToggleHelper.setupPasswordToggle(binding.edtPassword);

        PasswordToggleHelper.setupPasswordToggle(binding.edtConfirmPassword);
    }
}
