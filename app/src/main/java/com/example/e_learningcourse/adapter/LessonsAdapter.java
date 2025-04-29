package com.example.e_learningcourse.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ItemLessonBinding;
import com.example.e_learningcourse.model.Lesson;
import com.example.e_learningcourse.model.Section;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.LessonResponse;

import java.util.ArrayList;
import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.LessonViewHolder> {

    private List<LessonResponse> sections = new ArrayList<>();
    private OnLessonClickListener listener;
    public interface OnLessonClickListener {
        void onLessonClick(LessonResponse currentLesson);
    }

    public void setOnLessonClickListener(OnLessonClickListener listener) {
        this.listener = listener;
    }
    public void addLessons(List<LessonResponse> newLessons) {
        int oldSize = sections.size();
        sections.addAll(newLessons);
        notifyItemRangeInserted(oldSize, newLessons.size());
    }


    public void setLesson(List<LessonResponse> sections) {
        this.sections = sections;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLessonBinding binding = ItemLessonBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
            false
        );
        return new LessonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        LessonResponse lesson = sections.get(position);
        holder.bindLesson(lesson);
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    class LessonViewHolder extends RecyclerView.ViewHolder {
        private final ItemLessonBinding binding;

        LessonViewHolder(ItemLessonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindLesson(LessonResponse lesson) {
            binding.tvLessonNumber.setText(String.valueOf(lesson.getLessonOrder()));
            binding.tvLessonTitle.setText(lesson.getLessonName());
            binding.tvLessonDuration.setText(String.valueOf(lesson.getDuration()));

            // Set lesson status icon
            binding.ivLessonStatus.setImageResource(
                lesson.getStatus().equals("UNLOCKED") ? R.drawable.ic_play_circle : R.drawable.ic_lock
            );
            binding.ivLessonStatus.setImageTintList(
                itemView.getContext().getColorStateList(
                    lesson.getStatus().equals("UNLOCKED") ? R.color.blue : R.color.grey
                )
            );

            // Set click listener
            itemView.setOnClickListener(v -> {
                if (listener != null && lesson.getStatus().equals("UNLOCKED")) {
                    Log.d("LessonsAdapter", "Lesson clicked: " + lesson.getLessonName());
                    listener.onLessonClick(lesson);
                }
            });
        }
    }
} 