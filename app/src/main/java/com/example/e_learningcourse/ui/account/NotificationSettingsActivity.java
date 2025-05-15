package com.example.e_learningcourse.ui.account;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.e_learningcourse.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import android.content.SharedPreferences;

public class NotificationSettingsActivity extends AppCompatActivity {
    private SwitchMaterial switchNewCourse;
    private SwitchMaterial switchAssignment;
    private SwitchMaterial switchAnnouncement;
    private SwitchMaterial switchReminder;
    private ImageButton btnBack;
    private Animation fadeIn;
    private Animation slideUp;
    private SharedPreferences prefs;
    private static final String PREFS_NAME = "NotificationSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        // Initialize animations
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        initViews();
        setupClickListeners();
        loadSettings();
        
        // Start with fade in animation
        View rootView = findViewById(R.id.rootView);
        rootView.startAnimation(fadeIn);
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        switchNewCourse = findViewById(R.id.switchNewCourse);
        switchAssignment = findViewById(R.id.switchAssignment);
        switchAnnouncement = findViewById(R.id.switchAnnouncement);
        switchReminder = findViewById(R.id.switchReminder);

        // Add slide up animation to each switch
        switchNewCourse.startAnimation(slideUp);
        switchAssignment.startAnimation(slideUp);
        switchAnnouncement.startAnimation(slideUp);
        switchReminder.startAnimation(slideUp);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            v.animate()
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(100)
                .withEndAction(() -> {
                    v.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .withEndAction(this::finish)
                        .start();
                })
                .start();
        });

        setupSwitchListener(switchNewCourse, "new_course");
        setupSwitchListener(switchAssignment, "assignment");
        setupSwitchListener(switchAnnouncement, "announcement");
        setupSwitchListener(switchReminder, "reminder");
    }

    private void setupSwitchListener(SwitchMaterial switchView, String key) {
        switchView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Add animation when switch changes
            buttonView.animate()
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setDuration(100)
                .withEndAction(() -> {
                    buttonView.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start();
                })
                .start();
            
            saveNotificationSetting(key, isChecked);
        });
    }

    private void loadSettings() {
        switchNewCourse.setChecked(prefs.getBoolean("new_course", true));
        switchAssignment.setChecked(prefs.getBoolean("assignment", true));
        switchAnnouncement.setChecked(prefs.getBoolean("announcement", true));
        switchReminder.setChecked(prefs.getBoolean("reminder", true));
    }

    private void saveNotificationSetting(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }
} 