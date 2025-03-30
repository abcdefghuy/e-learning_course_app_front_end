package com.example.e_learningcourse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.CategoryAdapter;
import com.example.e_learningcourse.adapter.ContinueLearningAdapter;
import com.example.e_learningcourse.adapter.CourseAdapter;
import com.example.e_learningcourse.adapter.MentorAdapter;
import com.example.e_learningcourse.databinding.FragmentHomeBinding;
import com.example.e_learningcourse.model.Category;
import com.example.e_learningcourse.model.Course;
import com.example.e_learningcourse.model.Mentor;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements CourseAdapter.OnCourseClickListener {
    private FragmentHomeBinding binding;
    private CategoryAdapter categoryAdapter;
    private CourseAdapter courseAdapter;
    private MentorAdapter mentorAdapter;
    private ContinueLearningAdapter continueLearningAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        includedView.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), CourseDetailsActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerViews() {
        categoryAdapter = new CategoryAdapter();
        courseAdapter = new CourseAdapter();
        courseAdapter.setOnCourseClickListener(this);
        mentorAdapter = new MentorAdapter();
        continueLearningAdapter = new ContinueLearningAdapter(List.of(
                new Course(
                        3,
                        "Flutter Mobile Development",
                        "Alex Turner",
                        R.drawable.ic_business,
                        0,
                        4.7f,
                        69.99,20
                )
        ));
        // Set up click listener for continue learning items
        binding.rvCategories.setAdapter(categoryAdapter);
        binding.rvPopularCourses.setAdapter(courseAdapter);
        binding.rvMentors.setAdapter(mentorAdapter);
        binding.rvContinueLearning.setAdapter(continueLearningAdapter);
    }

    private void loadSampleData() {
        // Sample Categories
        categoryAdapter.setCategories(Arrays.asList(
                new Category(1, "Design", R.drawable.avatar),
                new Category(2, "Development", R.drawable.ic_marketing),
                new Category(3, "Marketing", R.drawable.ic_marketing),
                new Category(4, "Business", R.drawable.ic_business)
        ));

        // Sample Courses
        courseAdapter.setCourses(Arrays.asList(
                new Course(
                        1,
                        "UI/UX for Mobile Development",
                        "Alex Turner",
                        R.drawable.avatar,
                        R.drawable.ic_business,
                        4.5f,
                        49.99
                ),
                new Course(
                        2,
                        "Full Stack Web Development",
                        "Jenny Wilson",
                        R.drawable.avatar,
                        R.drawable.ic_search,
                        4.7f,
                        69.99
                ),
                new Course(
                        3,
                        "Flutter Mobile Development",
                        "Alex Turner",
                        R.drawable.avatar,
                        R.drawable.ic_business,
                        4.7f,
                        69.99
                ),
                new Course(
                        4,
                        "Digital Marketing Fundamentals",
                        "Lisa Wong",
                        R.drawable.avatar,
                        R.drawable.ic_marketing,
                        4.2f,
                        59.99
                )
        ));

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
    public void onBookmarkClick(Course course, int position) {
        // Toggle bookmark state
        boolean newBookmarkState = !course.isBookmarked();
        course.setBookmarked(newBookmarkState);
        // Show feedback to user
        String message = newBookmarkState ?
                getString(R.string.bookmark_added) :
                getString(R.string.bookmark_removed);
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        // Update the UI
        courseAdapter.notifyItemChanged(position);
        // Here you could also save the bookmark state to persistent storage
        // saveBookmarkState(course);
    }
}
