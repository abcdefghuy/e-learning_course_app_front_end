package com.example.e_learningcourse.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.PopularCoursesAdapter;
import com.example.e_learningcourse.adapter.RecentSearchAdapter;
import com.example.e_learningcourse.databinding.FragmentRecentSearchBinding;
import com.example.e_learningcourse.model.Course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchRecentFragment extends Fragment {
    private FragmentRecentSearchBinding binding;
    private PopularCoursesAdapter recentViewAdapter;
    private RecentSearchAdapter recentSearchAdapter;
    private List<Course> recentCourses;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecentSearchList();
        setupRecentViewList();
    }

    private void setupRecentSearchList() {
        recentSearchAdapter = new RecentSearchAdapter();
        binding.recentSearchList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recentSearchList.setAdapter(recentSearchAdapter);

        // Add sample data
        List<String> recentSearches = new ArrayList<>(Arrays.asList(
                "UI UX Courses",
                "Social Media Marketing",
                "Angular Development"
        ));
        recentSearchAdapter.setSearches(recentSearches);
        // Handle item removal
        recentSearchAdapter.setOnItemRemoveListener(position ->
                recentSearchAdapter.removeItem(position));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupRecentViewList() {
        // Initialize adapter
        recentViewAdapter = new PopularCoursesAdapter();
        
        // Set up RecyclerView
        binding.rvRecentView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvRecentView.setAdapter(recentViewAdapter);

        // Add sample data
        recentCourses = new ArrayList<>();
        recentCourses.add(new Course("Introduction of Figma", "Jacob Jones", 180.00, R.drawable.ic_business, true));
        recentCourses.add(new Course("Logo Design Basics", "Eleanor Pena", 120.00, R.drawable.ic_business, true));
        recentCourses.add(new Course("Introduction of Figma", "Kathryn Murphy", 160.00, R.drawable.ic_business, true));
        recentCourses.add(new Course("User-Centered Design", "Marvin McKinney", 200.00, R.drawable.ic_business, true));
        
        // Debug logging
        System.out.println("Setting up recent view list with " + recentCourses.size() + " courses");
        
        // Set courses to adapter
        recentViewAdapter.setCourses(recentCourses);
        
        // Force layout update
        binding.rvRecentView.requestLayout();
        binding.rvRecentView.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
