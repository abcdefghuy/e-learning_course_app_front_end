package com.example.e_learningcourse.ui.auth.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningcourse.MainActivity;
import com.example.e_learningcourse.data.local.UserManager;
import com.example.e_learningcourse.databinding.ActivityLoginBinding;
import com.example.e_learningcourse.ui.auth.forgotPassword.ForgotPasswordActivity;
import com.example.e_learningcourse.ui.auth.register.RegisterActivity;
import com.example.e_learningcourse.ui.base.BaseActivity;
import com.example.e_learningcourse.ui.common.PasswordToggleHelper;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    protected ActivityLoginBinding getViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected LoginViewModel getViewModel() {
        return new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.setViewModel(viewModel);

        binding.btnForgetPassword.setOnClickListener(v ->
                startActivity(new Intent(this, ForgotPasswordActivity.class))
        );

        binding.btnRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );

        binding.btnLogin.setOnClickListener(v -> viewModel.login());

        viewModel.getLoginResponse().observe(this, response -> {
            if (response != null && response.getData() != null) {
                viewModel.fetchUserInfo().observe(this, userResponse -> {
                    if (userResponse != null && userResponse.getData() != null) {
                        long userId = userResponse.getData().getId();
                        String email = userResponse.getData().getEmail();
                        String fullName = userResponse.getData().getFullName();

                        UserManager.getInstance(this).saveUser(userId, email, fullName);

                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }
                });
            }
        });

        PasswordToggleHelper.setupPasswordToggle(binding.edtPassword);
    }
}