package com.example.e_learningcourse.ui.bookmark;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.CourseAdapter;
import com.example.e_learningcourse.databinding.ActivityBookmarkBinding;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.example.e_learningcourse.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.List;

public class BookMarkActivity extends AppCompatActivity implements CourseAdapter.OnBookmarkClickListener {
    private ActivityBookmarkBinding binding;
    private CourseAdapter adapter;
    private List<CourseResponse> bookmarkedCourses;
    private BookmarkViewModel bookmarkViewModel;

    private boolean isLoading = false;
    private boolean hasMoreData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookmarkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        
        // Initialize bookmarkedCourses list
        bookmarkedCourses = new ArrayList<>();
        
        // Set up back button
        binding.btnBack.setOnClickListener(v -> finish());
        
        // Set up RecyclerView
        binding.rvBookmarkedCourses.setLayoutManager(new LinearLayoutManager(this));
        
        // Initialize bookmarked courses
        initializeBookmarkedCourses();
        
        // Set up adapter
        adapter = new CourseAdapter(this);
        adapter.setCourses(bookmarkedCourses);
        adapter.setOnBookmarkClickListener(this);
        binding.rvBookmarkedCourses.setAdapter(adapter);

        // Show shimmer loading
        adapter.showShimmer(true);
        
        // Observe LiveData only ONCE here
        bookmarkViewModel.getCourses().observe(this, response -> {
            if (response != null) {
                if (isLoading) {
                    // Hide shimmer loading
                    adapter.showShimmer(false);
                    if (adapter.getItemCount() == 0) {
                        adapter.setCourses(response.getData().getContent()); // Initial load
                    } else {
                        adapter.addCourses(response.getData().getContent()); // Load more
                    }
                    hasMoreData = !response.getData().isLast(); // Use isLast from response
                    isLoading = false;
                }
            }
        });

        // Observe bookmark changes
        bookmarkViewModel.getBookmarkStateChanged().observe(this, changed -> {
            if (changed) {
                // Refresh the bookmark list when bookmark state changes
                initializeBookmarkedCourses();
            }
        });

        // Add scroll listener for pagination
        binding.rvBookmarkedCourses.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    // Check if we need to load more items
                    if (!isLoading && hasMoreData && lastVisibleItemPosition + 2 >= totalItemCount) {
                        loadMoreBookmark();
                    }
                }
            }
        });
    }

    private void loadMoreBookmark() {
        if (isLoading || !hasMoreData) return;
        isLoading = true;
        bookmarkViewModel.fetchBookmark(); // Load next page
    }

    private void initializeBookmarkedCourses() {
        isLoading = true;
        bookmarkViewModel.fetchBookmark(); // Start with first page
    }

    @Override
    public void onBookmarkClick(CourseResponse course, int position) {
        // Verify position is valid
        if (position >= 0 && position < adapter.getItemCount()) {
            showRemoveDialog(course, position);
        }
    }

    private void showRemoveDialog(CourseResponse course, int position) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_remove_bookmark);

        // Make dialog background transparent to show rounded corners
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Set course details in dialog
        TextView tvCourseTitle = dialog.findViewById(R.id.tvCourseTitle);
        TextView tvInstructor = dialog.findViewById(R.id.tvInstructor);
        TextView tvPrice = dialog.findViewById(R.id.tvPrice);
        ShapeableImageView ivThumbnail = dialog.findViewById(R.id.ivThumbnail);
        TextView tvBestSeller = dialog.findViewById(R.id.tvBestSeller);

        if (tvCourseTitle != null) tvCourseTitle.setText(course.getCourseName());
        if (tvInstructor != null) tvInstructor.setText("Huy");
        if (tvPrice != null) tvPrice.setText(String.format("$%.2f", course.getCoursePrice()));
        if (ivThumbnail != null) {
            Glide.with(this)
                    .load(course.getCourseImg())
                    .into(ivThumbnail);
        }
        if (tvBestSeller != null) {
            tvBestSeller.setVisibility(course.isBestSeller() ? View.VISIBLE : View.GONE);
        }

        // Handle button clicks
        MaterialButton btnCancel = dialog.findViewById(R.id.btnCancel);
        MaterialButton btnRemove = dialog.findViewById(R.id.btnRemove);

        if (btnCancel != null) {
            btnCancel.setOnClickListener(v -> dialog.dismiss());
        }
        if (btnRemove != null) {
            btnRemove.setOnClickListener(v -> {
                // Remove the course from bookmarks
                bookmarkViewModel.toggleBookmark(course.getCourseId(), false);
                
                // Update the adapter's data
                adapter.removeCourse(position);
                
                // Show feedback
                showMessage(getString(R.string.bookmark_removed));
                
                // Dismiss dialog
                dialog.dismiss();
                
                // Update empty state
                updateEmptyState();
            });
        }
        dialog.show();
    }

    private void updateEmptyState() {
        binding.rvBookmarkedCourses.setVisibility(adapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
    }

    private void showMessage(String message) {
        NotificationUtils.showInfo(this, binding.getRoot(), message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
