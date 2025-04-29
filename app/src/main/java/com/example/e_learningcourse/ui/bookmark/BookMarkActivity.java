package com.example.e_learningcourse.ui.bookmark;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.CourseAdapter;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class BookMarkActivity extends AppCompatActivity implements CourseAdapter.OnBookmarkClickListener {
    private CourseAdapter adapter;
    private List<CourseResponse> bookmarkedCourses;
    private RecyclerView recyclerView;
    private BookmarkViewModel bookmarkViewModel;

    private boolean isLoading = false;
    private boolean hasMoreData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        bookmarkViewModel = new  ViewModelProvider(this).get(BookmarkViewModel.class);
        // Set up back button
        ImageButton backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(v -> finish());
        // Set up RecyclerView
        recyclerView = findViewById(R.id.rvBookmarkedCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Initialize bookmarked courses
        initializeBookmarkedCourses();
        // Set up adapter
        adapter = new CourseAdapter(this);
        adapter.setCourses(bookmarkedCourses);
        adapter.setOnBookmarkClickListener(this);
        recyclerView.setAdapter(adapter);

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
                        loadMoreBookmark();
                    }
                }
            }
        });
        // Initialize bookmarked courses
        initializeBookmarkedCourses();
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
        showRemoveDialog(course, position);
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
                    .load(course.getCourseImg()) // URL ảnh
//                    .placeholder(R.drawable.placeholder_img) // ảnh mặc định khi đang load
//                    .error(R.drawable.error_img) // ảnh hiển thị nếu load lỗi
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
                bookmarkedCourses.remove(position);
                adapter.notifyItemRemoved(position);
                // Show feedback
                Toast.makeText(BookMarkActivity.this, R.string.bookmark_removed, Toast.LENGTH_SHORT).show();
                // Dismiss dialog
                dialog.dismiss();
                // Update empty state if needed
                updateEmptyState();
            });
        }
        dialog.show();
    }

    private void updateEmptyState() {
        // Find the empty state view
//        View emptyState = findViewById(R.id.emptyState);
//        if (emptyState != null) {
//            emptyState.setVisibility(bookmarkedCourses.isEmpty() ? View.VISIBLE : View.GONE);
//        }
        // Find the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvBookmarkedCourses);
        if (recyclerView != null) {
            recyclerView.setVisibility(bookmarkedCourses.isEmpty() ? View.GONE : View.VISIBLE);
        }
    }
}
