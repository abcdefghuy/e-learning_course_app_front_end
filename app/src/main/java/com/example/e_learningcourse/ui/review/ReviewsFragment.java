package com.example.e_learningcourse.ui.review;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.adapter.ReviewsAdapter;
import com.example.e_learningcourse.databinding.FragmentReviewsBinding;
import com.example.e_learningcourse.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewsFragment extends Fragment {

    private FragmentReviewsBinding binding;
    private ReviewsAdapter adapter;
    private boolean isLoading = false;
    private boolean hasMoreData = true;
    private ReviewViewModel reviewViewModel;
    Long courseId;

    public static ReviewsFragment newInstance() {
        return new ReviewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        courseId = getArguments() != null ? getArguments().getLong("courseId") : -1;
        if (courseId == -1) {
            // Handle error: courseId not found
            return;
        }
        setupRecyclerView();
        reviewViewModel.getReviews().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.getContent() == null || response.getContent().isEmpty()) {
                    showNoData(); // hoặc hiển thị layout báo không có đánh giá
                }
                else {
                    if (isLoading) {
                        // Hide shimmer loading
                        if (adapter.getItemCount() == 0) {
                            adapter.setReviews(response.getContent()); // Initial load
                            Log.d("PopularCoursesActivity", "Initial load: " + response.getContent().size());
                        } else {
                            adapter.addReviews(response.getContent()); // Load more
                            Log.d("PopularCoursesActivity", "Load more: " + response.getContent().size());
                        }
                        hasMoreData = !response.isLast(); // Use isLast from response
                        isLoading = false;
                    }
                    binding.tvReviewCount.setText(String.format("Reviews (%d)", response.getContent().size()));
                    // Show RecyclerView and hide no data layout
                    binding.rvReviews.setVisibility(View.VISIBLE);
                    binding.layoutNoData.setVisibility(View.GONE);
                }
            }
        });

        // Add scroll listener for pagination
        binding.rvReviews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                    // Check if we need to load more items
                    if (!isLoading && hasMoreData && lastVisibleItemPosition + 2 >= totalItemCount) {
                        loadMoreReviews(courseId);
                    }
                }
            }
        });
        loadReviews(courseId);
    }

    private void setupRecyclerView() {
        adapter = new ReviewsAdapter();
        binding.rvReviews.setAdapter(adapter);
    }

    private void loadReviews(Long courseId) {
        // Sample data - replace with actual data from your backend
        isLoading=true;
        reviewViewModel.fetchReviewsByCourse(courseId);
    }
    private void showNoData() {
        binding.rvReviews.setVisibility(View.GONE);
        binding.layoutNoData.setVisibility(View.VISIBLE);
        binding.tvReviewCount.setText("Reviews (0)");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void loadMoreReviews(Long courseId) {
        if (!hasMoreData) return;
        isLoading = true;
        reviewViewModel.fetchReviewsByCourse(courseId);
    }
} 