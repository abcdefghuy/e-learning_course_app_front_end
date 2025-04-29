package com.example.e_learningcourse.ui.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ActivitySettingsBinding;
import com.example.e_learningcourse.ui.auth.resetPassword.ResetPasswordActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupClickListeners();
    }

    private void setupClickListeners() {
        // Back Button
        binding.btnBack.setOnClickListener(v -> finish());

        // Notification Settings
        binding.layoutNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, NotificationSettingsActivity.class);
            startActivity(intent);
        });

        // Password Manager
        binding.layoutPasswordManager.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });

        // Delete Account
        binding.layoutDeleteAccount.setOnClickListener(v -> showDeleteAccountDialog());
    }

    private void showDeleteAccountDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> deleteAccount())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void deleteAccount() {
        // TODO: Add your account deletion logic here
        // 1. Call your API to delete the account
        // 2. Clear local storage/preferences
        // 3. Log out the user
        // 4. Navigate to login screen
    }
}
