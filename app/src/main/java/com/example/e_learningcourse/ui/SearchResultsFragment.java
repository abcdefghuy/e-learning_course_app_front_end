package com.example.e_learningcourse.ui;

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
import com.example.e_learningcourse.databinding.FragmentSearchResultsBinding;
import com.example.e_learningcourse.model.Course;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsFragment extends Fragment {
    private FragmentSearchResultsBinding binding;
    private PopularCoursesAdapter resultsAdapter;
    private String currentQuery = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchResultsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    private void setupViews() {
        // Initialize adapter
        resultsAdapter = new PopularCoursesAdapter();
        binding.rvResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvResults.setAdapter(resultsAdapter);

        // Setup tab selection listener
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                performSearch(currentQuery);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    public void updateSearch(String query) {
        if (binding == null) return;
        
        currentQuery = query;
        binding.tvResultsTitle.setText(String.format("Results for \"%s\"", query));
        performSearch(query);
    }

    private void performSearch(String query) {
        if (binding == null) return;

        // Get selected tab position
        int selectedTab = binding.tabLayout.getSelectedTabPosition();
        
        if (selectedTab == 0) { // Courses tab
            List<Course> searchResults = new ArrayList<>();
            
            // Add sample courses that match the search query
            if (query.toLowerCase().contains("design") || query.toLowerCase().contains("figma")) {
                searchResults.add(new Course("Introduction to Figma", "Jacob Jones", 180.00, R.drawable.ic_business, true));
                searchResults.add(new Course("Logo Design Basics", "Eleanor Pena", 120.00, R.drawable.ic_business, true));
                searchResults.add(new Course("Advanced Figma", "Kathryn Murphy", 160.00, R.drawable.ic_business, true));
                searchResults.add(new Course("User-Centered Design", "Marvin McKinney", 200.00, R.drawable.ic_business, true));
            }
            
            resultsAdapter.setCourses(searchResults);
            binding.tvResultsCount.setText(searchResults.size() + " Results Found");
        } else { // Mentors tab
            resultsAdapter.setCourses(new ArrayList<>()); // Clear results for now
            binding.tvResultsCount.setText("0 Results Found");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}