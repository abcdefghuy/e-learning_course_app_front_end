package com.example.e_learningcourse.ui;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.e_learningcourse.MainActivity;
import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.CategoryAdapter;
import com.example.e_learningcourse.adapter.ContinueLearningAdapter;
import com.example.e_learningcourse.adapter.MentorAdapter;
import com.example.e_learningcourse.adapter.PopularCoursesAdapter;
import com.example.e_learningcourse.databinding.FragmentHomeBinding;
import com.example.e_learningcourse.model.response.MentorResponse;
import com.example.e_learningcourse.model.response.ContinueCourseResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.ui.account.UserViewModel;
import com.example.e_learningcourse.ui.bookmark.BookMarkActivity;
import com.example.e_learningcourse.ui.bookmark.BookmarkViewModel;
import com.example.e_learningcourse.ui.category.CategoryActivity;
import com.example.e_learningcourse.ui.category.CategoryViewModel;
import com.example.e_learningcourse.ui.course.CourseViewModel;
import com.example.e_learningcourse.ui.course.PopularCoursesActivity;
import com.example.e_learningcourse.ui.mentor.MentorActivity;
import com.example.e_learningcourse.ui.mentor.MentorViewModel;
import com.example.e_learningcourse.ui.mycourse.ContinueCourseViewModel;
import com.example.e_learningcourse.ui.mycourse.ContinueLearningActivity;
import com.example.e_learningcourse.utils.NotificationUtils;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements PopularCoursesAdapter.OnCourseClickListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    
    private FragmentHomeBinding binding;
    private CategoryAdapter categoryAdapter;
    private PopularCoursesAdapter popularCoursesAdapter;
    private MentorAdapter mentorAdapter;
    private ContinueLearningAdapter continueLearningAdapter;
    
    private CategoryViewModel categoryViewModel;
    private CourseViewModel courseViewModel;
    private ContinueCourseViewModel continueCourseViewModel;
    private BookmarkViewModel bookmarkViewModel;
    private UserViewModel userViewModel;
    private MentorViewModel mentorViewModel;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private boolean hasShownProfileDialog = false;

    private final ActivityResultLauncher<Intent> popularCoursesLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadPopularCourse();
                }
            }
    );

    private final ActivityResultLauncher<Intent> bookmarkLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadPopularCourse();
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewModels();
        setupRecyclerViews();
        setupClickListeners();
        setupSectionTitles();
        loadSampleData();

        // Observe bookmark changes
        bookmarkViewModel.getBookmarkStateChanged().observe(getViewLifecycleOwner(), changed -> {
            if (changed) {
                loadPopularCourse();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload data when fragment becomes visible
        loadSampleData();
    }

    private void initializeViewModels() {
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        continueCourseViewModel = new ViewModelProvider(this).get(ContinueCourseViewModel.class);
        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        mentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
    }

    private void setupRecyclerViews() {
        categoryAdapter = new CategoryAdapter();
        popularCoursesAdapter = new PopularCoursesAdapter(requireContext());
        popularCoursesAdapter.setOnCourseClickListener(this);
        popularCoursesAdapter.setHeaderBookmarkView(binding.ibBookmark);
        continueLearningAdapter = new ContinueLearningAdapter(requireContext());
        mentorAdapter = new MentorAdapter();

        binding.rvCategories.setAdapter(categoryAdapter);
        binding.rvPopularCourses.setAdapter(popularCoursesAdapter);
        binding.rvMentors.setAdapter(mentorAdapter);
        binding.rvContinueLearning.setAdapter(continueLearningAdapter);
    }

    private void setupClickListeners() {
        binding.ibBookmark.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), BookMarkActivity.class);
            bookmarkLauncher.launch(intent);
        });
        
        View categorySection = binding.getRoot().findViewById(R.id.categoriesSection);
        View popularCoursesSection = binding.getRoot().findViewById(R.id.popularCoursesSection);
        View continueLearningSection = binding.getRoot().findViewById(R.id.continueLearningSection);
        View mentorSection = binding.getRoot().findViewById(R.id.topMentorSection);

        categorySection.findViewById(R.id.tvSeeAll).setOnClickListener(v -> navigateTo(CategoryActivity.class));
        popularCoursesSection.findViewById(R.id.tvSeeAll).setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), PopularCoursesActivity.class);
            popularCoursesLauncher.launch(intent);
        });
        continueLearningSection.findViewById(R.id.tvSeeAll).setOnClickListener(v -> navigateTo(ContinueLearningActivity.class));
        mentorSection.findViewById(R.id.tvSeeAll).setOnClickListener(v -> navigateTo(MentorActivity.class));
    }

    private void setupSectionTitles() {
        View popularCoursesSection = binding.getRoot().findViewById(R.id.popularCoursesSection);
        View categorySection = binding.getRoot().findViewById(R.id.categoriesSection);
        View mentorSection = binding.getRoot().findViewById(R.id.topMentorSection);
        View continueLearningSection = binding.getRoot().findViewById(R.id.continueLearningSection);

        ((TextView) popularCoursesSection.findViewById(R.id.tvTitle)).setText("Popular Courses");
        ((TextView) categorySection.findViewById(R.id.tvTitle)).setText("Categories");
        ((TextView) mentorSection.findViewById(R.id.tvTitle)).setText("Top Mentors");
        ((TextView) continueLearningSection.findViewById(R.id.tvTitle)).setText("Continue Learning");
    }

    private void loadSampleData() {
        loadCategories();
        loadPopularCourse();
        loadContinueLearning();
        loadUserData();
        loadMentors();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadMentors() {
        mentorViewModel.fetchTopMentors();
        mentorViewModel.getMentors().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                mentorAdapter.setMentors(response.getContent());
                mentorAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("SetTextI18n")
    private void loadUserData() {
        userViewModel.fetchUserInfo();
        userViewModel.getUserInfo().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.isSuccess()) {
                String userName = response.getData().getFullName();
                if((userName == null || userName.isEmpty()) && !hasShownProfileDialog) {
                    hasShownProfileDialog = true;
                    showProfileCompletionDialog();
                }
                binding.tvWelcome.setText(userName);
                Glide.with(requireContext())
                        .load(response.getData().getAvatarUrl())
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .into(binding.ivAvatar);
            } else {
                Log.e(TAG, "Failed to load user data: " + response.getMessage());
            }
        });
    }

    private void showProfileCompletionDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_profile_completion);
        
        // Set dialog width to 90% of screen width
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        // Find views
        ImageView ivIcon = dialog.findViewById(R.id.ivIcon);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        MaterialButton btnComplete = dialog.findViewById(R.id.btnComplete);
        MaterialButton btnLater = dialog.findViewById(R.id.btnLater);

        // Set click listeners
        btnComplete.setOnClickListener(v -> {
            dialog.dismiss();
            if (getActivity() != null) {
                ((MainActivity) getActivity()).navigateToAccount();
            }
        });

        btnLater.setOnClickListener(v -> dialog.dismiss());

        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onBookmarkClick(CourseResponse course, int position) {
        boolean newBookmarkState = !course.isBookmarked();
        course.setBookmarked(newBookmarkState);
        
        bookmarkViewModel.toggleBookmark(course.getCourseId(), newBookmarkState);
        
        String message = newBookmarkState ? 
                getString(R.string.bookmark_added) : 
                getString(R.string.bookmark_removed);
        showMessage(message);
        popularCoursesAdapter.notifyItemChanged(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadCategories() {
        categoryViewModel.fetchCategories();
        categoryViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            categoryAdapter.setCategories(categories);
            categoryAdapter.notifyDataSetChanged();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadPopularCourse() {
        courseViewModel.fetchTopSellingCourses();
        courseViewModel.getCourses().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                popularCoursesAdapter.setCourses(response.getContent());
                isLastPage = response.isLast();
                isLoading = false;
            }
        });
    }

    private void loadContinueLearning() {
        continueCourseViewModel.fetchCoursesInProgress();
        continueCourseViewModel.getCourseDetails().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                List<ContinueCourseResponse> courses = new ArrayList<>();
                courses.add(response);
                continueLearningAdapter.setCourses(courses);
            }
        });
    }

    private void navigateTo(Class<?> activityClass) {
        startActivity(new Intent(requireContext(), activityClass));
    }

    private void showMessage(String message) {
        NotificationUtils.showInfo(requireContext(), binding.getRoot(), message);
    }
}

