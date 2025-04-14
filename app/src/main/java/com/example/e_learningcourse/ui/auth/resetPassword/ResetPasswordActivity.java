package com.example.e_learningcourse.ui.auth.resetPassword;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningcourse.databinding.ActivityResetPasswordBinding;
import com.example.e_learningcourse.ui.auth.login.LoginActivity;
import com.example.e_learningcourse.ui.base.BaseActivity;
import com.example.e_learningcourse.ui.common.PasswordToggleHelper;

public class ResetPasswordActivity extends BaseActivity<ActivityResetPasswordBinding, ResetPasswordViewModel> {

    @Override
    protected ActivityResetPasswordBinding getViewBinding() {
        return ActivityResetPasswordBinding.inflate(getLayoutInflater());
    }

    @Override
    protected ResetPasswordViewModel getViewModel() {
        return new ViewModelProvider(this).get(ResetPasswordViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.setViewModel(viewModel);

        String email = getIntent().getStringExtra("email");
        if (email != null) {
            viewModel.setEmail(email);
        }

        binding.btnContinue.setOnClickListener(v -> viewModel.resetPassword());

        viewModel.getResetPasswordResponse().observe(this, response -> {
            if (response != null && response.isSuccess()) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });

        PasswordToggleHelper.setupPasswordToggle(binding.edtNewPassword);
        PasswordToggleHelper.setupPasswordToggle(binding.edtConfirmNewPassword);
    }
}
