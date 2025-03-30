package com.example.e_learningcourse.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ActivityCoursesBinding;
import com.google.android.material.tabs.TabLayout;

public class CoursesActivity extends AppCompatActivity {
    private ActivityCoursesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupTabs();
        // Show About fragment by default
        if (savedInstanceState == null) {
            showFragment(new LessonsFragment());
        }
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 1:
                        fragment = new CertificateFragment();
                        break;
                    default:
                        fragment = new LessonsFragment();
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

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
