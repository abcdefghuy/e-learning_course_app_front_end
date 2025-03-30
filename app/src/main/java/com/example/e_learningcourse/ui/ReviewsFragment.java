package com.example.e_learningcourse.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.adapter.ReviewsAdapter;
import com.example.e_learningcourse.databinding.FragmentReviewsBinding;
import com.example.e_learningcourse.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewsFragment extends Fragment {

    private FragmentReviewsBinding binding;
    private ReviewsAdapter adapter;

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
        setupRecyclerView();
        loadReviews();
    }

    private void setupRecyclerView() {
        adapter = new ReviewsAdapter();
        binding.rvReviews.setAdapter(adapter);
    }

    private void loadReviews() {
        // Sample data - replace with actual data from your backend
        List<Review> reviews = new ArrayList<>();
        
        reviews.add(new Review(
            "Dale Thiel",
            "https://i.pravatar.cc/150?img=1",
            "11 months ago",
            5.0f,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt",
            true
        ));

        reviews.add(new Review(
            "Tiffany Nitzsche",
            "https://i.pravatar.cc/150?img=2",
            "11 months ago",
            5.0f,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt",
            true
        ));

        adapter.setReviews(reviews);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 