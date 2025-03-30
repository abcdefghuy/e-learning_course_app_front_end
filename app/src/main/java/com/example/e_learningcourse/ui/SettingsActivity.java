package com.example.e_learningcourse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_learningcourse.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout layoutNotifications;
    private LinearLayout layoutPasswordManager;
    private LinearLayout layoutDeleteAccount;
    private TextView tvDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
        setupClickListeners();
        ImageButton backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(v -> finish());
    }

    private void initViews() {
        layoutNotifications = findViewById(R.id.layoutNotifications);
        layoutPasswordManager = findViewById(R.id.layoutPasswordManager);
        layoutDeleteAccount = findViewById(R.id.layoutDeleteAccount);
//        tvDeleteAccount = findViewById(R.id.tvDeleteAccount);
    }

    private void setupClickListeners() {
        // Notification Settings
        layoutNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, NotificationSettingsActivity.class);
            startActivity(intent);
        });

        // Password Manager
        layoutPasswordManager.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, PasswordManagerActivity.class);
            startActivity(intent);
        });

        // Delete Account
        layoutDeleteAccount.setOnClickListener(v -> {
            showDeleteAccountDialog();
        });
    }

    private void showDeleteAccountDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // TODO: Implement account deletion logic
                    deleteAccount();
                })
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
