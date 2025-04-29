package com.example.e_learningcourse.ui.account;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_learningcourse.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class NotificationSettingsActivity extends AppCompatActivity {
    private SwitchMaterial switchNewCourse;
    private SwitchMaterial switchAssignment;
    private SwitchMaterial switchAnnouncement;
    private SwitchMaterial switchReminder;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        initViews();
        setupClickListeners();
        loadSettings();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        switchNewCourse = findViewById(R.id.switchNewCourse);
        switchAssignment = findViewById(R.id.switchAssignment);
        switchAnnouncement = findViewById(R.id.switchAnnouncement);
        switchReminder = findViewById(R.id.switchReminder);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        switchNewCourse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveNotificationSetting("new_course", isChecked);
        });

        switchAssignment.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveNotificationSetting("assignment", isChecked);
        });

        switchAnnouncement.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveNotificationSetting("announcement", isChecked);
        });

        switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveNotificationSetting("reminder", isChecked);
        });
    }

    private void loadSettings() {
        // TODO: Load saved settings from SharedPreferences
        // For example:
        // SharedPreferences prefs = getSharedPreferences("notification_settings", MODE_PRIVATE);
        // switchNewCourse.setChecked(prefs.getBoolean("new_course", true));
        // switchAssignment.setChecked(prefs.getBoolean("assignment", true));
        // switchAnnouncement.setChecked(prefs.getBoolean("announcement", true));
        // switchReminder.setChecked(prefs.getBoolean("reminder", true));
    }

    private void saveNotificationSetting(String key, boolean value) {
        // TODO: Save settings to SharedPreferences
        // For example:
        // SharedPreferences prefs = getSharedPreferences("notification_settings", MODE_PRIVATE);
        // prefs.edit().putBoolean(key, value).apply();
    }
} 