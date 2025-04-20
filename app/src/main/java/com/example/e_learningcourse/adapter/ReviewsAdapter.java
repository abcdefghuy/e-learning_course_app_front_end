package com.example.e_learningcourse.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_learningcourse.databinding.ItemReviewBinding;
import com.example.e_learningcourse.model.Review;
import com.example.e_learningcourse.model.response.ReviewResponse;
import com.example.e_learningcourse.repository.ReviewRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private List<ReviewResponse> reviews = new ArrayList<>();

    public void setReviews(List<ReviewResponse> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
    public void addReviews(List<ReviewResponse> newReviews){
        int oldSize = reviews.size();
        reviews.addAll(newReviews);
        notifyItemRangeInserted(oldSize, newReviews.size());
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

        void bind(ReviewResponse review) {
            binding.tvReviewerName.setText(review.getReviewUserName());
            Date date = review.getReviewDate(); // Giả sử kiểu Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String formattedDate = sdf.format(date);
            binding.tvReviewDate.setText(formattedDate);
            binding.ratingBar.setRating(review.getReviewScore());
            binding.tvReviewContent.setText(review.getReviewContent());

            // Load reviewer avatar
            Glide.with(binding.ivReviewerAvatar)
                .load(review.getReviewerAvatarUrl())
                .circleCrop()
                .into(binding.ivReviewerAvatar);
        }
    }
} 