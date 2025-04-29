package com.example.e_learningcourse.ui.course;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ActivityCourseDetailsBinding;
import com.example.e_learningcourse.ui.lesson.LessonsFragment;
import com.example.e_learningcourse.ui.review.ReviewsFragment;
import com.google.android.material.tabs.TabLayout;

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
        loadCourseDetails(courseId);
        // Show About fragment by default
        if (savedInstanceState == null) {
            showFragment(new AboutCourseFragment(), courseId);
        }
        loadCourseDetails(courseId);
    }

    private void loadCourseDetails(Long courseId) {
        courseViewModel.fetchCourseDetails(courseId);
        courseViewModel.getCourseDetails().observe(this, courseDetailResponse -> {
            if (courseDetailResponse != null) {
                binding.tvCourseTitle.setText(courseDetailResponse.getCourseName());
                binding.tvInstructorName.setText("huy");
                binding.tvRating.setText(String.valueOf(courseDetailResponse.getRating()));
                binding.ivInstructorAvatar.setImageResource(R.drawable.avatar);
                binding.tvTotalPrice.setText(String.valueOf(courseDetailResponse.getCoursePrice()));
                binding.tvRating.setText(String.valueOf(courseDetailResponse.getRating()));
                binding.tvLessonsCount.setText(String.valueOf(courseDetailResponse.getLessonCount()));
                binding.tvReviewCount.setText(String.valueOf(courseDetailResponse.getReviewCount()));
                isBookmarked = courseDetailResponse.isBookmarked();
                binding.btnBookmark.setImageResource(
                        isBookmarked ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark
                );
                sharedViewModel.setCourseDetail(courseDetailResponse);
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
            // Implement enrollment
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