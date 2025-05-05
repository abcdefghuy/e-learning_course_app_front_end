package com.example.e_learningcourse.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ItemMentorBinding;
import com.example.e_learningcourse.model.response.MentorResponse;

import java.util.ArrayList;
import java.util.List;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorViewHolder> {

    private List<MentorResponse> mentorResponses = new ArrayList<>();

    public void setMentors(List<MentorResponse> mentorResponses) {
        this.mentorResponses = mentorResponses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMentorBinding binding = ItemMentorBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
            false
        );
        return new MentorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorViewHolder holder, int position) {
        holder.bind(mentorResponses.get(position));
    }

    @Override
    public int getItemCount() {
        return mentorResponses.size();
    }

    static class MentorViewHolder extends RecyclerView.ViewHolder {
        private final ItemMentorBinding binding;

        MentorViewHolder(ItemMentorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MentorResponse mentorResponse) {
            Glide.with(binding.getRoot().getContext())
                .load(mentorResponse.getMentorAvatar())
                .placeholder(R.drawable.avatar)
                .into(binding.ivMentorAvatar);
            binding.tvMentorName.setText(mentorResponse.getMentorName());
        }
    }
} 