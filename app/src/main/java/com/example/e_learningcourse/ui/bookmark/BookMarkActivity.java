package com.example.e_learningcourse.ui.bookmark;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        // Set up back button
        ImageButton backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(v -> finish());
        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvBookmarkedCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Initialize bookmarked courses
        initializeBookmarkedCourses();
        // Set up adapter
        adapter = new CourseAdapter(this);
        adapter.setCourses(bookmarkedCourses);
        adapter.setOnBookmarkClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initializeBookmarkedCourses() {
        bookmarkedCourses = new ArrayList<>();
//        bookmarkedCourses.add(new CourseDetailResponse("Introduction of Figma", "Jacob Jones", 180.00, R.drawable.ic_business, true));
//        bookmarkedCourses.add(new CourseDetailResponse("Logo Design Basics", "Eleanor Pena", 120.00, R.drawable.ic_business, true));
//        bookmarkedCourses.add(new CourseDetailResponse("Introduction of Figma", "Kathryn Murphy", 160.00, R.drawable.ic_business, true));
//        bookmarkedCourses.add(new CourseDetailResponse("User-Centered Design", "Marvin McKinney", 200.00, R.drawable.ic_business, true));
//        bookmarkedCourses.add(new CourseDetailResponse("Introduction of Figma", "Jacob Jones", 180.00, R.drawable.ic_business, true));
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
                course.setBookmarked(false);
                bookmarkedCourses.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, bookmarkedCourses.size());

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
