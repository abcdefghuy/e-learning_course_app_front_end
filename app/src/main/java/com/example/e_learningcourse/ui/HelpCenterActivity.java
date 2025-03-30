package com.example.e_learningcourse.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_learningcourse.ui.FaqItemAnimator;
import com.example.e_learningcourse.databinding.ActivityHelpCenterBinding;

public class HelpCenterActivity extends AppCompatActivity {
    private ActivityHelpCenterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelpCenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupFaqItems();
        setupBackButton();
    }

    private void setupFaqItems() {
        // Setup the first FAQ item
        new FaqItemAnimator(
                binding.layoutFaqItem,
                binding.imgArrow,
                binding.tvAnswer
        );

        // Add more FAQ items here as needed
    }

    private void setupBackButton() {
        binding.btnBack.setOnClickListener(v -> finish());
    }
} 