package com.example.e_learningcourse.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.CategoryAdapter;
import com.example.e_learningcourse.adapter.ContinueLearningAdapter;
import com.example.e_learningcourse.adapter.MentorAdapter;
import com.example.e_learningcourse.adapter.PopularCoursesAdapter;
import com.example.e_learningcourse.databinding.FragmentHomeBinding;
import com.example.e_learningcourse.model.Course;
import com.example.e_learningcourse.model.Mentor;
import com.example.e_learningcourse.model.response.ContinueCourseResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.ui.bookmark.BookMarkActivity;
import com.example.e_learningcourse.ui.category.CategoryActivity;
import com.example.e_learningcourse.ui.category.CategoryViewModel;
import com.example.e_learningcourse.ui.course.CourseViewModel;
import com.example.e_learningcourse.ui.course.PopularCoursesActivity;
import com.example.e_learningcourse.ui.mycourse.ContinueCourseViewModel;
import com.example.e_learningcourse.ui.mycourse.ContinueLearningActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements PopularCoursesAdapter.OnCourseClickListener {
    private FragmentHomeBinding binding;
    private CategoryAdapter categoryAdapter;
    private PopularCoursesAdapter popularCoursesAdapter;
    private MentorAdapter mentorAdapter;
    private ContinueLearningAdapter continueLearningAdapter;
    private CategoryViewModel categoryViewModel;
    private CourseViewModel courseViewModel;
    private ContinueCourseViewModel continueCourseViewModel;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        continueCourseViewModel = new ViewModelProvider(this).get(ContinueCourseViewModel.class);
        setupRecyclerViews();
        loadSampleData();
        // Lấy View từ include
        View includedView = binding.getRoot().findViewById(R.id.popularCoursesSection);
        View categorySection = binding.getRoot().findViewById(R.id.categoriesSection);
        View  mentorSection= binding.getRoot().findViewById(R.id.topMentorSection);
        View continueLearningSection = binding.getRoot().findViewById(R.id.continueLearningSection);
        binding.ibBookmark.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), BookMarkActivity.class);
            startActivity(intent);
        });
        TextView tvTitle = includedView.findViewById(R.id.tvTitle);
// Từ View này, lấy TextView trong layout_section_header
        TextView tvSee = categorySection.findViewById(R.id.tvSeeAll);
        tvSee.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), CategoryActivity.class);
            startActivity(intent);
        });
        TextView tvTitle1 = includedView.findViewById(R.id.tvSeeAll);
        tvTitle1.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), PopularCoursesActivity.class);
            startActivity(intent);
        });
        tvTitle.setText("Popular Courses");
        TextView tvCategory = categorySection.findViewById(R.id.tvTitle);
        tvCategory.setText("Categories");
        TextView tvMentor = mentorSection.findViewById(R.id.tvTitle);
        tvMentor.setText("Top Mentors");
        TextView tvContinueLearning = continueLearningSection.findViewById(R.id.tvTitle);
        tvContinueLearning.setText("Continue Learning");
        TextView tvTitle2 = continueLearningSection.findViewById(R.id.tvSeeAll);
        tvTitle2.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ContinueLearningActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerViews() {
        categoryAdapter = new CategoryAdapter();
        popularCoursesAdapter = new PopularCoursesAdapter(requireContext());
        popularCoursesAdapter.setOnCourseClickListener(this);
        popularCoursesAdapter.setHeaderBookmarkView(binding.ibBookmark);
        continueLearningAdapter = new ContinueLearningAdapter(requireContext());
        mentorAdapter = new MentorAdapter();
        // Set up click listener for continue learning items
        binding.rvCategories.setAdapter(categoryAdapter);
        binding.rvPopularCourses.setAdapter(popularCoursesAdapter);
        binding.rvMentors.setAdapter(mentorAdapter);
        binding.rvContinueLearning.setAdapter(continueLearningAdapter);
    }
    private void loadSampleData() {
        loadCategories();
        loadPopularCourse();
        loadContinueLearning();
        // Sample Mentors
        mentorAdapter.setMentors(Arrays.asList(
                new Mentor(1, "David Wilson", R.drawable.avatar),
                new Mentor(2, "Emma Brown", R.drawable.avatar),
                new Mentor(3, "Michael Lee", R.drawable.avatar),
                new Mentor(4, "Lisa Chen", R.drawable.avatar)
        ));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        // Update the UI
        popularCoursesAdapter.notifyItemChanged(position);
        // Here you could also save the bookmark state to persistent storage
        // saveBookmarkState(course);
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
                isLastPage = response.isLast(); // Cập nhật trạng thái phân trang
                isLoading = false;
            }
        });
    }
    private void loadContinueLearning() {
        continueCourseViewModel.fetchCoursesInProgress();
        continueCourseViewModel.getCourseDetails().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                // Kiểm tra xem có dữ liệu hay không
                List<ContinueCourseResponse> courses = new ArrayList<>();
                courses.add(response);
                continueLearningAdapter.setCourses(courses);
            }
        });
    }

}

