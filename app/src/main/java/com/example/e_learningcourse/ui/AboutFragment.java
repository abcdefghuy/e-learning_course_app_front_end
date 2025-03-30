package com.example.e_learningcourse.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment {
    private FragmentAboutBinding binding;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        setupClickListeners();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupContent();
    }

    private void setupContent() {
        // Get course data from arguments or activity
        String description = "Design Thinking is a human-centered approach to innovation that draws from the designer's toolkit to integrate the needs of people, the possibilities of technology, and the requirements for business success.";
        
        binding.tvAboutCourse.setText(description);
        binding.tvAboutCourse.setMaxLines(3); // Initially show 3 lines
        
        binding.tvReadMore.setOnClickListener(v -> {
            binding.tvAboutCourse.setMaxLines(Integer.MAX_VALUE);
            v.setVisibility(View.GONE);
        });
    }
    private void setupClickListeners() {

        binding.tvReadMore.setOnClickListener(v -> {
            // Expand course description
            binding.tvAboutCourse.setMaxLines(Integer.MAX_VALUE);
            v.setVisibility(View.GONE);
        });

        binding.btnCall.setOnClickListener(v -> {
            // Implement call functionality
        });

        binding.btnMessage.setOnClickListener(v -> {
            // Implement message functionality
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 