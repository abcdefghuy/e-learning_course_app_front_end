package com.example.e_learningcourse.ui.course;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.CourseAdapter;
import com.example.e_learningcourse.adapter.PopularCoursesAdapter;
import com.example.e_learningcourse.model.Course;
import com.example.e_learningcourse.model.response.CourseDetailResponse;
import com.example.e_learningcourse.model.response.CourseResponse;

import java.util.ArrayList;
import java.util.List;

public class PopularCoursesActivity extends AppCompatActivity implements CourseAdapter.OnBookmarkClickListener {
    private CourseAdapter adapter;
    private List<CourseResponse> courses;
    private RecyclerView recyclerView;
    private CourseViewModel courseViewModel;
    private boolean isLoading = false;
    private boolean hasMoreData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_courses);

        // Initialize ViewModel
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        // Set up back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter and set it to RecyclerView
        adapter = new CourseAdapter(this);
        adapter.setOnBookmarkClickListener(this);
        recyclerView.setAdapter(adapter);

        // Show shimmer loading
        adapter.showShimmer(true);

        // Observe LiveData only ONCE here
        courseViewModel.getCourses().observe(this, response -> {
            if (response != null) {
                if (isLoading) {
                    // Hide shimmer loading
                    adapter.showShimmer(false);
                    if (adapter.getItemCount() == 0) {
                        adapter.setCourses(response.getContent()); // Initial load
                        Log.d("PopularCoursesActivity", "Initial load: " + response.getContent().size());
                    } else {
                        adapter.addCourses(response.getContent()); // Load more
                        Log.d("PopularCoursesActivity", "Load more: " + response.getContent().size());
                    }
                    hasMoreData = !response.isLast(); // Use isLast from response
                    isLoading = false;
                }
            }
        });

        // Add scroll listener for pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                    // Check if we need to load more items
                    if (!isLoading && hasMoreData && lastVisibleItemPosition + 2 >= totalItemCount) {
                        loadMoreCourses();
                    }
                }
            }
        });

        // Load initial courses
        initializeCourses();
    }

    private void initializeCourses() {
        isLoading = true;
        courseViewModel.fetchCoursesNextPage(); // Start with first page
    }

    private void loadMoreCourses() {
        if (!hasMoreData) return;
        isLoading = true;
        courseViewModel.fetchCoursesNextPage();
    }

    @Override
    public void onBookmarkClick(CourseResponse course, int position) {
        // Toggle bookmark state
        boolean newBookmarkState = !course.isBookmarked();
        course.setBookmarked(newBookmarkState);
        // Show feedback to user
        String message = newBookmarkState ?
                getString(R.string.bookmark_added) :
                getString(R.string.bookmark_removed);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        // Update the UI
        adapter.notifyItemChanged(position);

        // Here you could also save the bookmark state to persistent storage
        // saveBookmarkState(course);
    }
}