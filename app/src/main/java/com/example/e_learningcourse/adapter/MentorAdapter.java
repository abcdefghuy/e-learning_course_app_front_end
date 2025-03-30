package com.example.e_learningcourse.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.databinding.ItemMentorBinding;
import com.example.e_learningcourse.model.Mentor;

import java.util.ArrayList;
import java.util.List;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorViewHolder> {

    private List<Mentor> mentors = new ArrayList<>();

    public void setMentors(List<Mentor> mentors) {
        this.mentors = mentors;
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
        holder.bind(mentors.get(position));
    }

    @Override
    public int getItemCount() {
        return mentors.size();
    }

    static class MentorViewHolder extends RecyclerView.ViewHolder {
        private final ItemMentorBinding binding;

        MentorViewHolder(ItemMentorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Mentor mentor) {
            binding.ivMentorAvatar.setImageResource(mentor.getAvatarResId());
            binding.tvMentorName.setText(mentor.getName());
        }
    }
} 