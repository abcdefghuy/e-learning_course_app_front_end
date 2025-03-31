package com.example.e_learningcourse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningcourse.MainActivity;
import com.example.e_learningcourse.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        binding.btnLogin.setOnClickListener(v -> {
            viewModel.login();

            viewModel.getLoginResponse().observe(this, response -> {
                if (response != null && response.getToken() != null) {
                    startActivity(new Intent(this, MainActivity.class));
                    Toast.makeText(this, "Login successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
