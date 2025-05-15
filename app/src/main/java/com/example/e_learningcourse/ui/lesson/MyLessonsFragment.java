package com.example.e_learningcourse.ui.lesson;

import android.app.Activity;
import android.content.Intent;
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
import com.example.e_learningcourse.model.response.LessonResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyLessonsFragment extends Fragment implements LessonsAdapter.OnLessonClickListener {

    private static final int REQUEST_LESSON_PLAYER = 1001;
    private FragmentLessonsBinding binding;
    private LessonsAdapter adapter;
    private boolean isLoading = false;
    private boolean hasMoreData = true;
    private List<LessonResponse> allLessons = new ArrayList<>();

    private LessonViewModel lessonViewModel;

    public static MyLessonsFragment newInstance() {
        return new MyLessonsFragment();
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
                    } else {
                        adapter.addLessons(response.getContent()); // Load more
                    }
                    allLessons.addAll(response.getContent());
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
        adapter.setOnLessonClickListener(this);
        binding.rvLessons.setAdapter(adapter);
    }

    private void loadLessons(Long courseId) {
        isLoading = true;
        allLessons.clear(); // Clear existing lessons before loading new ones
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
    @Override
    public void onLessonClick(LessonResponse currentLesson) {
        if (currentLesson == null) {
            Log.e("LessonsFragment", "Clicked lesson is null");
            return;
        }
        
        Log.d("LessonsFragment", "Lesson clicked: " + currentLesson.getLessonId());
        List<LessonResponse> nextLessons = allLessons.stream()
                .filter(lesson -> !lesson.getLessonId().equals(currentLesson.getLessonId()))
                .collect(Collectors.toList());
                
        Intent intent = new Intent(requireContext(), LessonPlayerActivity.class);
        intent.putExtra("current_lesson", currentLesson);
        intent.putParcelableArrayListExtra("next_lessons", new ArrayList<>(nextLessons));
        startActivityForResult(intent, REQUEST_LESSON_PLAYER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LESSON_PLAYER && resultCode == Activity.RESULT_OK && data != null) {
            boolean lessonCompleted = data.getBooleanExtra("lesson_completed", false);
            String lessonId = data.getStringExtra("lesson_id");
            
            if (lessonCompleted && lessonId != null) {
                // Refresh the lessons list
                long courseId = getArguments() != null ? getArguments().getLong("courseId") : -1;
                if (courseId != -1) {
                    loadLessons(courseId);
                }
            }
        }
    }
} 