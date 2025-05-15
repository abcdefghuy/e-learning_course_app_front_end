package com.example.e_learningcourse.ui.course;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.CourseAdapter;
import com.example.e_learningcourse.adapter.PopularCoursesAdapter;
import com.example.e_learningcourse.databinding.ActivityPopularCoursesBinding;
import com.example.e_learningcourse.model.Course;
import com.example.e_learningcourse.model.response.CourseDetailResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.ui.bookmark.BookmarkViewModel;
import com.example.e_learningcourse.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.List;

public class PopularCoursesActivity extends AppCompatActivity implements CourseAdapter.OnBookmarkClickListener {
    private ActivityPopularCoursesBinding binding;
    private CourseAdapter adapter;
    private List<CourseResponse> courses;
    private CourseViewModel courseViewModel;
    private BookmarkViewModel bookmarkViewModel;
    private boolean isLoading = false;
    private boolean hasMoreData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPopularCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);

        // Set up back button
        binding.backButton.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });

        // Set up RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter and set it to RecyclerView
        adapter = new CourseAdapter(this);
        adapter.setOnBookmarkClickListener(this);
        binding.recyclerView.setAdapter(adapter);

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

        // Observe bookmark changes
        bookmarkViewModel.getBookmarkStateChanged().observe(this, changed -> {
            if (changed) {
                // Refresh the course list when bookmark state changes
                initializeCourses();
            }
        });

        // Add scroll listener for pagination
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        course.setBookmarked(newBookmarkState); // cập nhật trạng thái
        // Gọi ViewModel để xử lý thêm hoặc xóa bookmark
        bookmarkViewModel.toggleBookmark(course.getCourseId(), newBookmarkState);
        // Thông báo và cập nhật UI
        String message = newBookmarkState ?
                getString(R.string.bookmark_added) :
                getString(R.string.bookmark_removed);
        showMessage(message);
        adapter.notifyItemChanged(position);
    }

    private void showMessage(String message) {
        NotificationUtils.showInfo(this, binding.getRoot(), message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
    @Override
    protected void onResume() {
        super.onResume();
        initializeCourses(); // Load lại dữ liệu mỗi khi activity quay lại
    }
}