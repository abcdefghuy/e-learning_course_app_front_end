package com.example.e_learningcourse.ui.mycourse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.ContinueLearningAdapter;
import com.example.e_learningcourse.databinding.FragmentContinueCourseBinding;
import com.example.e_learningcourse.model.Course;

import java.util.ArrayList;
import java.util.List;

public class ContinueCourseFragment extends Fragment {
    private FragmentContinueCourseBinding binding;
    private ContinueLearningAdapter courseAdapter;
    private ContinueCourseViewModel continueCourseViewModel;
    private boolean isLoading = false;
    private boolean hasMoreData = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentContinueCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        continueCourseViewModel = new  ViewModelProvider(this).get(ContinueCourseViewModel.class);
        setupRecyclerView();
        continueCourseViewModel.getCourses().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if(response.getContent() == null || response.getContent().isEmpty()) {
                    showNoData(); // hoặc hiển thị layout báo không có đánh giá
                } else {
                    if (isLoading) {
                        if (courseAdapter.getItemCount() == 0) {
                            courseAdapter.setCourses(response.getContent()); // Initial load
                            Log.d("PopularCoursesActivity", "Initial load: " + response.getContent().size());
                        } else {
                            courseAdapter.addCourses(response.getContent()); // Load more
                            Log.d("PopularCoursesActivity", "Load more: " + response.getContent().size());
                        }
                        hasMoreData = !response.isLast();
                        isLoading = false;
                    }
                }

            }
        });

        binding.rvContinueCourses.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                    if (!isLoading && hasMoreData && lastVisibleItemPosition + 2 >= totalItemCount) {
                        loadMoreCourses();
                    }
                }
            }


        });
        loadCourses();
    }
    private void loadMoreCourses() {
        if (!hasMoreData) return;
        isLoading = true;
        continueCourseViewModel.fetchContinueCoursesNextPage();
    }
    private void showNoData() {
        binding.rvContinueCourses.setVisibility(View.GONE);
        binding.layoutNoData.setVisibility(View.VISIBLE);
    }

    private void setupRecyclerView() {
        courseAdapter = new ContinueLearningAdapter(requireContext());
        binding.rvContinueCourses.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvContinueCourses.setAdapter(courseAdapter);
    }

    private void loadCourses() {
        isLoading = true;
        continueCourseViewModel.fetchContinueCoursesNextPage();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 