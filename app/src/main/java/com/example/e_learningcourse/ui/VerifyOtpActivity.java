package com.example.e_learningcourse.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_learningcourse.databinding.ActivityVerifyOtpBinding;

import java.util.Arrays;
import java.util.List;

public class VerifyOtpActivity extends AppCompatActivity implements NumpadFragment.OtpInputListener {
    private ActivityVerifyOtpBinding binding;
    private List<EditText> otpFields;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        otpFields = Arrays.asList(
                binding.otp1, binding.otp2, binding.otp3,
                binding.otp4, binding.otp5, binding.otp6
        );

        getSupportFragmentManager().beginTransaction()
                .replace(binding.fragmentContainer.getId(), new NumpadFragment())
                .commit();
    }

    @Override
    public List<EditText> getOtpFields() {
        return otpFields;
    }
}
