package com.example.e_learningcourse.ui.course;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.data.local.UserManager;
import com.example.e_learningcourse.databinding.ActivityCourseDetailsBinding;
import com.example.e_learningcourse.model.response.CourseDetailResponse;
import com.example.e_learningcourse.ui.lesson.LessonsFragment;
import com.example.e_learningcourse.ui.payment.PaymentActivity;
import com.example.e_learningcourse.ui.review.ReviewsFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class CourseDetailsActivity extends AppCompatActivity {
    private ActivityCourseDetailsBinding binding;
    private boolean isBookmarked = false;
    Long courseId ;
    private CourseViewModel courseViewModel;
    private SharedCourseViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        courseId = getIntent().getLongExtra("courseId", -1);
        long userId = UserManager.getInstance(this).getUserId();
        Log.d("Course ID", "detail nhan: " + courseId); // ✅ đúng cú pháp
        if (courseId == -1) {
            // Handle error: courseId not found
            finish();
            return;
        }
        // Initialize ViewModel
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        sharedViewModel = new ViewModelProvider(this).get(SharedCourseViewModel.class);
        setupToolbar();
        setupTabs();
        setupClickListeners();
        loadCourseDetails(courseId, userId);
        // Show About fragment by default
        if (savedInstanceState == null) {
            showFragment(new AboutCourseFragment(), courseId);
        }
        loadCourseDetails(courseId, userId);
    }

    @SuppressLint("SetTextI18n")
    private void loadCourseDetails(Long courseId, Long userId) {
        courseViewModel.fetchCourseDetails(courseId, userId);
        courseViewModel.getCourseDetails().observe(this, courseDetailResponse -> {
            if (courseDetailResponse != null) {
                Log.d("EnrollmentStatus", "isEnrolled = " + courseDetailResponse.isEnrolled());
                binding.tvCourseTitle.setText(courseDetailResponse.getCourseName());
                binding.tvInstructorName.setText("huy");
                binding.tvRating.setText(String.valueOf(courseDetailResponse.getRating()));
                binding.ivInstructorAvatar.setImageResource(R.drawable.avatar);
                binding.tvTotalPrice.setText(String.valueOf(courseDetailResponse.getCoursePrice()));
                binding.tvRating.setText(String.valueOf(courseDetailResponse.getRating()));
                binding.tvLessonsCount.setText(String.valueOf(courseDetailResponse.getLessonCount()) + " lessons");
                binding.tvReviewCount.setText(String.valueOf(courseDetailResponse.getReviewCount()) + " reviews");
                isBookmarked = courseDetailResponse.isBookmarked();
                binding.btnBookmark.setImageResource(
                        isBookmarked ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark
                );
                sharedViewModel.setCourseDetail(courseDetailResponse);

                if (courseDetailResponse.isEnrolled()) {
                    binding.btnEnroll.setVisibility(View.GONE);
                } else {
                    binding.btnEnroll.setVisibility(View.VISIBLE);
                }
            } else {
                // Handle error
            }
        });
    }

    private void setupToolbar() {
        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnShare.setOnClickListener(v -> {
            // Implement share functionality
        });
        binding.btnBookmark.setOnClickListener(v -> {
            isBookmarked = !isBookmarked;
            binding.btnBookmark.setImageResource(
                    isBookmarked ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark
            );
        });
    }

    private void setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 1:
                        fragment = new LessonsFragment();
                        break;
                    case 2:
                        fragment = new ReviewsFragment();
                        break;
                    default:
                        fragment = new AboutCourseFragment();
                        break;
                }
                showFragment(fragment, courseId);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Select the first tab by default
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0));
    }

    private void setupClickListeners() {
        binding.btnEnroll.setOnClickListener(v -> {
            CourseDetailResponse course = sharedViewModel.getCourseDetail().getValue();

            if (course != null) {
                Intent intent = new Intent(this, PaymentActivity.class);
                long currentUserId = UserManager.getInstance(this).getUserId();
                intent.putExtra("userId", currentUserId);
                intent.putExtra("courseId", course.getCourseId());
                intent.putExtra("amount", course.getCoursePrice());
                intent.putExtra("title", course.getCourseName());
                intent.putExtra("category", TextUtils.join(", ", course.getCategoryNames()));
                intent.putExtra("imageUrl", course.getCourseImg());

                startActivity(intent);
            } else {
                Toast.makeText(this, "Course info not available", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showFragment(Fragment fragment, Long courseId) {
        Bundle bundle = new Bundle();
        bundle.putLong("courseId", courseId); // Đặt tên key bất kỳ
        fragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
} 