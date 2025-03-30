package com.example.e_learningcourse.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.ContinueLearningAdapter;
import com.example.e_learningcourse.databinding.FragmentContinueCourseBinding;
import com.example.e_learningcourse.model.Course;

import java.util.ArrayList;
import java.util.List;

public class ContinueCourseFragment extends Fragment {
    private FragmentContinueCourseBinding binding;
    private ContinueLearningAdapter courseAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentContinueCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        loadCourses();
    }

    private void setupRecyclerView() {

    }

    private void loadCourses() {
        List<Course> courses = new ArrayList<>();
        
        // Add ongoing courses with progress 20/25
        courses.add(new Course(1, "Introduction of Figma", "Jacob Jones", R.drawable.avatar,
                R.drawable.ic_business, 4.5f, 0, 20));
        courses.add(new Course(2, "Introduction of Figma", "Eleanor Pena", R.drawable.avatar,
                R.drawable.ic_business, 4.5f, 0, 20));
        courses.add(new Course(3, "Figma Design Tool", "Kathryn Murphy", R.drawable.avatar,
                R.drawable.ic_business, 4.5f, 0, 20));
        courses.add(new Course(4, "User-Centered Design", "Marvin McKinney", R.drawable.avatar,
                R.drawable.ic_business, 4.5f, 0, 20));
        courses.add(new Course(5, "Introduction of Figma", "Jacob Jones", R.drawable.avatar,
                R.drawable.ic_business, 4.5f, 0, 20));

        courseAdapter = new ContinueLearningAdapter(courses);
        binding.rvContinueCourses.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvContinueCourses.setAdapter(courseAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 