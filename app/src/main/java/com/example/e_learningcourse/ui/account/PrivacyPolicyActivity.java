package com.example.e_learningcourse.ui.account;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.e_learningcourse.databinding.ActivityPrivacyPolicyBinding;
import com.example.e_learningcourse.R;
import android.content.SharedPreferences;
import android.widget.Toast;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private ActivityPrivacyPolicyBinding binding;
    private SharedPreferences prefs;
    private static final String PREFS_NAME = "PolicyPrefs";
    private static final String LAST_UPDATE_KEY = "last_policy_update";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        
        setupBackButton();
        loadPolicyContent();
        checkForPolicyUpdates();
    }

    private void setupBackButton() {
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void loadPolicyContent() {
        // In a real app, you might load this from a server or local database
        // For now, we're using the strings from the layout, but you could replace with:
        
        String cancellationPolicy = getString(R.string.cancellation_policy,
            7, // Refund window in days
            25  // Maximum course completion percentage for refund
        );
        
        binding.tvCancellationContent.setText(cancellationPolicy);
        
        // You could also load different policies based on user's region
        String userRegion = getUserRegion();
        loadRegionSpecificTerms(userRegion);
    }

    private String getUserRegion() {
        // In a real app, get this from user's settings or device locale
        return "US"; // Default to US
    }

    private void loadRegionSpecificTerms(String region) {
        // In a real app, load different terms based on region
        // For now, we're using the default terms from layout
        
        // Example of how you might implement this:
        switch (region) {
            case "EU":
                // Load EU-specific terms (GDPR, etc.)
                // binding.tvTermsContent.setText(getString(R.string.terms_eu));
                break;
            case "US":
                // Load US-specific terms
                // binding.tvTermsContent.setText(getString(R.string.terms_us));
                break;
            default:
                // Use default terms
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