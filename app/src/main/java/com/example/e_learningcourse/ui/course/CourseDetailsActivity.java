package com.example.e_learningcourse.ui.course;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.e_learningcourse.R;
import com.example.e_learningcourse.data.local.UserManager;
import com.example.e_learningcourse.databinding.ActivityCourseDetailsBinding;
import com.example.e_learningcourse.model.response.CourseDetailResponse;
import com.example.e_learningcourse.ui.bookmark.BookmarkViewModel;
import com.example.e_learningcourse.ui.lesson.CourseLessonFragment;
import com.example.e_learningcourse.ui.lesson.MyLessonsFragment;
import com.example.e_learningcourse.ui.payment.PaymentActivity;
import com.example.e_learningcourse.ui.review.ReviewsFragment;
import com.example.e_learningcourse.utils.CurrencyUtils;
import com.google.android.material.tabs.TabLayout;
import com.example.e_learningcourse.utils.NotificationUtils;

import java.util.Objects;

public class CourseDetailsActivity extends AppCompatActivity {
    private ActivityCourseDetailsBinding binding;
    private boolean isBookmarked = false;
    private Long courseId ;
    private String mentorName, mentorAvatar;
    private Double rating;
    private boolean isEnrolled;
    private CourseViewModel courseViewModel;
    private SharedCourseViewModel sharedViewModel;
    private BookmarkViewModel bookmarkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        long userId = UserManager.getInstance(this).getUserId();
        rating = getIntent().getDoubleExtra("rating", 0);
        mentorName = getIntent().getStringExtra("mentorName");
        mentorAvatar = getIntent().getStringExtra("mentorAvatar");
        courseId = getIntent().getLongExtra("courseId", -1);
        isBookmarked = getIntent().getBooleanExtra("isBookmarked", false);
        Log.d("CourseDetailsActivity", "isBookmarked = " + isBookmarked);
        if (courseId == -1) {
            // Handle error: courseId not found
            finish();
            return;
        }
        // Initialize ViewModel
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        sharedViewModel = new ViewModelProvider(this).get(SharedCourseViewModel.class);
        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
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
                isEnrolled = courseDetailResponse.isEnrolled();
                binding.tvCourseTitle.setText(courseDetailResponse.getCourseName());
                binding.tvInstructorName.setText(mentorName);
                binding.tvRating.setText(String.valueOf(rating));
                Glide.with(this)
                        .load(mentorAvatar)
                        .placeholder(R.drawable.avatar)
                        .circleCrop()
                        .into(binding.ivInstructorAvatar);
                binding.tvTotalPrice.setText(CurrencyUtils.formatCurrencyVND(courseDetailResponse.getCoursePrice()));
                binding.tvRating.setText(String.valueOf(courseDetailResponse.getRating()));
                binding.tvLessonsCount.setText(String.valueOf(courseDetailResponse.getLessonCount()) + " lessons");
                binding.tvReviewCount.setText(String.valueOf(courseDetailResponse.getReviewCount()) + " reviews");
                binding.btnBookmark.setImageResource(
                        isBookmarked ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark
                );
                courseDetailResponse.setRating(rating);
                Glide.with(this)
                        .load(courseDetailResponse.getCourseImg())
                        .into(binding.ivCourseImage);
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
        binding.btnBookmark.setOnClickListener(v -> {
            isBookmarked = !isBookmarked;
            binding.btnBookmark.setImageResource(
                    isBookmarked ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark
            );
            bookmarkViewModel.toggleBookmark(courseId, isBookmarked);
            if (isBookmarked) {
                showMessage("Added to bookmark");
            } else {
                showMessage("Removed from bookmark");
            }
        });
    }

    private void setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 1:
                        if (isEnrolled) {
                            fragment = new MyLessonsFragment();
                        } else {
                            fragment = new CourseLessonFragment();
                        }
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
                showError("Course details not available");
            }
        });
        binding.btnShare.setOnClickListener(v -> {
            CourseDetailResponse course = sharedViewModel.getCourseDetail().getValue();
            if (course != null) {
                String shareUrl = "https://play.google.com/store/apps/details?id=";
                String shareText = "Check out this course: " + course.getCourseName() + "\n" +
                        "Price: " + CurrencyUtils.formatCurrencyVND(course.getCoursePrice()) + "\n" +
                        "Rating: " + course.getRating() + "\n";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Khóa học hay, không thể bỏ qua!");
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareText + shareUrl + getPackageName());
                sendIntent.setType("text/plain");
                
                // Tạo chooser để chọn ứng dụng chia sẻ
                Intent chooser = Intent.createChooser(sendIntent, "Chia sẻ khóa học");
                
                // Kiểm tra xem có ứng dụng nào có thể xử lý intent này không
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                } else {
                    showError("Không tìm thấy ứng dụng để chia sẻ");
                }
            } else {
                showError("Không thể lấy thông tin khóa học");
            }
        });

    }

    private void showFragment(Fragment fragment, Long courseId) {
        Bundle bundle = new Bundle();
        bundle.putLong("courseId", courseId); // Đặt tên key bất kỳ
        bundle.putString("mentorName", mentorName);
        bundle.putString("mentorAvatar", mentorAvatar);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void showError(String message) {
        NotificationUtils.showError(this, binding.getRoot(), message);
    }
    private void showMessage(String message) {
        NotificationUtils.showInfo(this, binding.getRoot(), message);
    }
} 