package com.example.e_learningcourse.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.adapter.LessonsAdapter;
import com.example.e_learningcourse.databinding.FragmentLessonsBinding;
import com.example.e_learningcourse.model.Lesson;
import com.example.e_learningcourse.model.Section;

import java.util.ArrayList;
import java.util.List;

public class LessonsFragment extends Fragment {

    private FragmentLessonsBinding binding;
    private LessonsAdapter adapter;

    public static LessonsFragment newInstance() {
        return new LessonsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        binding = FragmentLessonsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        loadLessons();
    }

    private void setupRecyclerView() {
        adapter = new LessonsAdapter();
        binding.rvLessons.setAdapter(adapter);
    }

    private void loadLessons() {
        // Sample data - replace with actual data from your backend
        List<Section> sections = new ArrayList<>();

        // Section 1 - Introduction
        List<Lesson> introLessons = new ArrayList<>();
        introLessons.add(new Lesson("01", "Introduction to Design Thinking", "10:00", false));
        introLessons.add(new Lesson("02", "Empathy in Design", "05:00", true));
        sections.add(new Section("Section 1 - Introduction", "15 Min", introLessons));

        // Section 2 - Fundamentals
        List<Lesson> fundamentalLessons = new ArrayList<>();
        fundamentalLessons.add(new Lesson("03", "Ideation and Brainstorming", "25:00", true));
        fundamentalLessons.add(new Lesson("04", "Prototyping and Testing", "10:00", true));
        fundamentalLessons.add(new Lesson("05", "Empathy in Design", "10:00", true));
        sections.add(new Section("Section 2 - Fundamentals", "45 Min", fundamentalLessons));

        adapter.setSections(sections);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 