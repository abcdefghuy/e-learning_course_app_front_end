package com.example.e_learningcourse.ui.lesson;

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

import com.example.e_learningcourse.adapter.LessonsAdapter;
import com.example.e_learningcourse.databinding.FragmentLessonsBinding;
import com.example.e_learningcourse.model.Lesson;
import com.example.e_learningcourse.model.Section;
import com.example.e_learningcourse.ui.course.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

public class LessonsFragment extends Fragment {

    private FragmentLessonsBinding binding;
    private LessonsAdapter adapter;
    private boolean isLoading = false;
    private boolean hasMoreData = true;

    private LessonViewModel lessonViewModel;

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

        // Khởi tạo ViewModel ở đây
        lessonViewModel = new ViewModelProvider(this).get(LessonViewModel.class);
        setupRecyclerView();

        long courseId = getArguments() != null ? getArguments().getLong("courseId") : -1;
        if (courseId == -1) {
            Log.e("LessonsFragment", "Invalid course ID");
            return;
        }
        lessonViewModel.getLessons().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (isLoading) {
                    if (adapter.getItemCount() == 0) {
                        adapter.setLesson(response.getContent()); // Initial load
                        Log.d("PopularCoursesActivity", "Initial load: " + response.getContent().size());
                    } else {
                        adapter.addLessons(response.getContent()); // Load more
                        Log.d("PopularCoursesActivity", "Load more: " + response.getContent().size());
                    }
                    hasMoreData = !response.isLast();
                    isLoading = false;
                }
            }
        });

        binding.rvLessons.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                    if (!isLoading && hasMoreData && lastVisibleItemPosition + 2 >= totalItemCount) {
                        loadMoreLessons(courseId);
                    }
                }
            }
        });

        loadLessons(courseId);
    }

    private void setupRecyclerView() {
        adapter = new LessonsAdapter();
        binding.rvLessons.setAdapter(adapter);
    }

    private void loadLessons(Long courseId) {
        isLoading = true;
        lessonViewModel.fetchLessonsByCourse(courseId);
    }
    private void loadMoreLessons(Long courseId) {
        if (!hasMoreData) return;

        isLoading = true;
        lessonViewModel.fetchLessonsByCourse(courseId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 