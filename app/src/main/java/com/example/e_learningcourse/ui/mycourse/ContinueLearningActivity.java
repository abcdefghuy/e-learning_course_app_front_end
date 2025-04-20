package com.example.e_learningcourse.ui.mycourse;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.ContinueLearningAdapter;
import com.example.e_learningcourse.model.Course;

import java.util.ArrayList;
import java.util.List;

public class ContinueLearningActivity extends AppCompatActivity {

    private ContinueLearningAdapter courseAdapter;
    private ContinueCourseViewModel continueCourseViewModel;
    private boolean isLoading = false;
    private boolean hasMoreData = true;
    private Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_learning);
        courseId = getIntent().getLongExtra("courseId", -1);
        continueCourseViewModel = new ViewModelProvider(this).get(ContinueCourseViewModel.class);
        // Set up back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Create sample courses with progress
        courseAdapter = new ContinueLearningAdapter(this);
        recyclerView.setAdapter(courseAdapter);

        continueCourseViewModel.getCourses().observe(this, response -> {
            if (response != null) {
                if (response.getContent() == null || response.getContent().isEmpty()) {
                    showNoData(); // hoặc hiển thị layout báo không có đánh giá
                }
                else {
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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

            private void loadMoreCourses() {
            }
        });
        loadCourses();
    }

    private void showNoData() {
        RecyclerView rvReviews = findViewById(R.id.rvReviews);
        View layoutNoData = findViewById(R.id.layoutNoData);
        rvReviews.setVisibility(View.GONE);
        layoutNoData.setVisibility(View.VISIBLE);
    }

    private void loadCourses() {
        isLoading = true;
        continueCourseViewModel.fetchContinueCoursesNextPage();
    }

    private void loadMoreCourses() {
        if (!hasMoreData) return;
        isLoading = true;
        continueCourseViewModel.fetchContinueCoursesNextPage();
    }
}