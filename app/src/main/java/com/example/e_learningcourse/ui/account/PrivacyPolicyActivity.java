package com.example.e_learningcourse.ui.account;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import com.example.e_learningcourse.databinding.ActivityPrivacyPolicyBinding;
import com.example.e_learningcourse.R;
import android.content.SharedPreferences;
import android.widget.Toast;
import androidx.core.content.ContextCompat;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private ActivityPrivacyPolicyBinding binding;
    private SharedPreferences prefs;
    private static final String PREFS_NAME = "PolicyPrefs";
    private static final String LAST_UPDATE_KEY = "last_policy_update";
    private Animation fadeIn;
    private Animation slideUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize animations
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        
        setupBackButton();
        loadPolicyContent();
        checkForPolicyUpdates();
        
        // Start with fade in animation
        binding.getRoot().startAnimation(fadeIn);
    }

    private void setupBackButton() {
        binding.btnBack.setOnClickListener(v -> {
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
    }

    private void loadPolicyContent() {
        // Add slide up animation to content sections
        binding.tvCancellationContent.startAnimation(slideUp);
        binding.tvTermsContent.startAnimation(slideUp);
        
        String cancellationPolicy = getString(R.string.cancellation_policy,
            7, // Refund window in days
            25  // Maximum course completion percentage for refund
        );
        
        binding.tvCancellationContent.setText(cancellationPolicy);
        
        String userRegion = getUserRegion();
        loadRegionSpecificTerms(userRegion);
    }

    private String getUserRegion() {
        return "US"; // Default to US
    }

    private void loadRegionSpecificTerms(String region) {
        switch (region) {
            case "EU":
                binding.tvTermsContent.setText(getString(R.string.terms_eu));
                break;
            case "US":
                binding.tvTermsContent.setText(getString(R.string.terms_us));
                break;
            default:
                binding.tvTermsContent.setText(getString(R.string.terms_default));
                break;
        }
    }

    private void checkForPolicyUpdates() {
        // In a real app, check against server version
        long lastUpdateTime = prefs.getLong(LAST_UPDATE_KEY, 0);
        long currentTime = System.currentTimeMillis();

        // Example: Check if policy was updated in the last 30 days
        if (currentTime - lastUpdateTime > 30 * 24 * 60 * 60 * 1000L) {
            // Show update notification to user
            Toast.makeText(this, "Please review our updated policies", Toast.LENGTH_LONG).show();

            // Update the last check time
            prefs.edit().putLong(LAST_UPDATE_KEY, currentTime).apply();
        }
    }

} 