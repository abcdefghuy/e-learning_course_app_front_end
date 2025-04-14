package com.example.e_learningcourse.ui.auth.verifyOtp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningcourse.databinding.ActivityVerifyOtpBinding;
import com.example.e_learningcourse.ui.auth.login.LoginActivity;
import com.example.e_learningcourse.ui.auth.resetPassword.ResetPasswordActivity;
import com.example.e_learningcourse.ui.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

public class VerifyOtpActivity extends BaseActivity<ActivityVerifyOtpBinding, VerifyOtpViewModel> {

    private List<EditText> otpFields;
    private String action;

    @Override
    protected ActivityVerifyOtpBinding getViewBinding() {
        return ActivityVerifyOtpBinding.inflate(getLayoutInflater());
    }

    @Override
    protected VerifyOtpViewModel getViewModel() {
        return new ViewModelProvider(this).get(VerifyOtpViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.setViewModel(viewModel);

        String email = getIntent().getStringExtra("email");
        action = getIntent().getStringExtra("action");

        if (email != null) {
            viewModel.setEmail(email);
            binding.emailInfo.setText("OTP code has been sent to " + hideEmail(email));
        }

        binding.btnVerify.setOnClickListener(v -> viewModel.verify());

        otpFields = Arrays.asList(
                binding.otp1, binding.otp2, binding.otp3,
                binding.otp4, binding.otp5, binding.otp6
        );

        setupOtpInputs();

        viewModel.isOtpComplete.observe(this, isComplete ->
                binding.btnVerify.setEnabled(Boolean.TRUE.equals(isComplete))
        );

        viewModel.getVerifyResponse().observe(this, response -> {
            if (response != null && response.isSuccess()) {
                goToNextScreen(action);
            }
        });
    }

    private void setupOtpInputs() {
        for (int i = 0; i < otpFields.size(); i++) {
            final int index = i;

            otpFields.get(index).addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void afterTextChanged(Editable s) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1 && index < otpFields.size() - 1) {
                        otpFields.get(index + 1).requestFocus();
                    } else if (s.length() > 1) {
                        otpFields.get(index).setText(String.valueOf(s.charAt(0)));
                        otpFields.get(index).setSelection(1);
                    }
                    viewModel.otpCode.setValue(getOtpCode());
                }
            });

            otpFields.get(index).setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_DEL &&
                        otpFields.get(index).getText().toString().isEmpty() &&
                        index > 0) {
                    otpFields.get(index - 1).requestFocus();
                    otpFields.get(index - 1).setSelection(otpFields.get(index - 1).getText().length());
                    return true;
                }
                return false;
            });
        }
    }

    private String getOtpCode() {
        StringBuilder sb = new StringBuilder();
        for (EditText et : otpFields) {
            sb.append(et.getText().toString());
        }
        return sb.toString();
    }

    private String hideEmail(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex <= 1) return email;
        return email.charAt(0) + "*****" + email.substring(atIndex - 1);
    }

    private void goToNextScreen(String action) {
        if ("register".equals(action)) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if ("reset_password".equals(action)) {
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            intent.putExtra("email", viewModel.getEmail());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Unknown action: " + action, Toast.LENGTH_SHORT).show();
        }
    }
}