package com.example.e_learningcourse.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_learningcourse.MainActivity;
import com.example.e_learningcourse.data.local.TokenManager;
import com.example.e_learningcourse.ui.auth.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean hasSeenIntro = prefs.getBoolean("hasSeenIntro", false);

        if (!hasSeenIntro) {
            startActivity(new Intent(this, IntroActivity.class));
        } else {
            String token = TokenManager.getInstance(getApplicationContext()).getToken();
            if (token != null && !token.isEmpty()) {
                if(TokenManager.getInstance(getApplicationContext()).isTokenExpired()) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    // Token is valid, proceed to MainActivity
                    startActivity(new Intent(this, MainActivity.class));
                }
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
        finish();
    }
}
