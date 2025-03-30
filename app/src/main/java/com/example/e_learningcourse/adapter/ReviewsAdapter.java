package com.example.e_learningcourse.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_learningcourse.databinding.ItemReviewBinding;
import com.example.e_learningcourse.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReviewBinding binding = ItemReviewBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
            false
        );
        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final ItemReviewBinding binding;

        ReviewViewHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Review review) {
            binding.tvReviewerName.setText(review.getReviewerName());
            binding.tvReviewDate.setText(review.getReviewDate());
            binding.ratingBar.setRating(review.getRating());
            binding.tvReviewContent.setText(review.getContent());

            // Load reviewer avatar
            Glide.with(binding.ivReviewerAvatar)
                .load(review.getReviewerAvatar())
                .circleCrop()
                .into(binding.ivReviewerAvatar);
        }
    }
} 