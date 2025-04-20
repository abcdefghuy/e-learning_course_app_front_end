package com.example.e_learningcourse.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.FragmentAboutBinding;
import com.example.e_learningcourse.ui.course.SharedCourseViewModel;
import com.example.e_learningcourse.ui.review.ReviewViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AboutFragment extends Fragment {
    private FragmentAboutBinding binding;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }
    private Long courseId;
    private SharedCourseViewModel sharedViewModel;

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
        courseId = getArguments() != null ? getArguments().getLong("courseId") : -1;
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedCourseViewModel.class);
        setupContent(courseId);
    }

    private void setupContent(Long courseId) {
        sharedViewModel.getCourseDetail().observe(getViewLifecycleOwner(), detail -> {
            if (detail != null) {
                binding.tvAboutCourse.setText(detail.getCourseDescription());
                binding.ivTutorAvatar.setImageResource(R.drawable.avatar);
                binding.tvTutorSubject.setText("DESIGN");
                binding.tvTutorName.setText("Huy");
                binding.tvStudentCount.setText(String.valueOf(detail.getStudentQuantity()));
                binding.tvLevel.setText(String.valueOf(detail.getLevel()));
                binding.tvLevel.setText(detail.getLevel());
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

                String formattedUpdate = sdf.format(detail.getUpdateAt());
                String formattedCreated = sdf.format(detail.getCreatedAt());

                binding.tvLastUpdate.setText(formattedUpdate);
                binding.tvCreatedAt.setText(formattedCreated);

                binding.tvAboutCourse.setMaxLines(3); // Initially show 3 lines

                binding.tvReadMore.setOnClickListener(v -> {
                    binding.tvAboutCourse.setMaxLines(Integer.MAX_VALUE);
                    v.setVisibility(View.GONE);
                });
            }
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