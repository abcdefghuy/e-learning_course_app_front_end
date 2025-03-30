package com.example.e_learningcourse.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ActivityCourseDetailsBinding;
import com.google.android.material.tabs.TabLayout;

public class CourseDetailsActivity extends AppCompatActivity {
    private ActivityCourseDetailsBinding binding;
    private boolean isBookmarked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupToolbar();
        setupTabs();
        setupClickListeners();
        // Show About fragment by default
        if (savedInstanceState == null) {
            showFragment(new AboutFragment());
        }
    }

    private void setupToolbar() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnShare.setOnClickListener(v -> {
            // Implement share functionality
        });

        binding.btnBookmark.setOnClickListener(v -> {
            isBookmarked = !isBookmarked;
            binding.btnBookmark.setImageResource(
                    isBookmarked ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark
            );
        });
    }

    private void setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 1:
                        fragment = new LessonsFragment();
                        break;
                    case 2:
                        fragment = new ReviewsFragment();
                        break;
                    default:
                        fragment = new AboutFragment();
                        break;
                }
                showFragment(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Select the first tab by default
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0));
    }

    private void setupClickListeners() {
        binding.btnPlay.setOnClickListener(v -> {
            // Implement video playback
        });

        binding.btnEnroll.setOnClickListener(v -> {
            // Implement enrollment
        });
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
} 