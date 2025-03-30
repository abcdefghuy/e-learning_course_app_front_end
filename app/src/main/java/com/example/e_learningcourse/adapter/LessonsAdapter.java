package com.example.e_learningcourse.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ItemLessonBinding;
import com.example.e_learningcourse.model.Lesson;
import com.example.e_learningcourse.model.Section;

import java.util.ArrayList;
import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.LessonViewHolder> {

    private List<Section> sections = new ArrayList<>();
    private OnLessonClickListener listener;

    public interface OnLessonClickListener {
        void onLessonClick(Lesson lesson);
    }

    public void setOnLessonClickListener(OnLessonClickListener listener) {
        this.listener = listener;
    }

    public void setSections(List<Section> sections) {
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
        // Calculate which section and lesson to show
        int currentPosition = 0;
        for (Section section : sections) {
            if (position == currentPosition) {
                // Show section header
                holder.bindSection(section);
                return;
            }
            currentPosition++;

            for (Lesson lesson : section.getLessons()) {
                if (position == currentPosition) {
                    // Show lesson
                    holder.bindLesson(lesson);
                    return;
                }
                currentPosition++;
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (Section section : sections) {
            // Add 1 for section header
            count++;
            // Add number of lessons in section
            count += section.getLessons().size();
        }
        return count;
    }

    class LessonViewHolder extends RecyclerView.ViewHolder {
        private final ItemLessonBinding binding;

        LessonViewHolder(ItemLessonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindSection(Section section) {
            binding.tvSectionTitle.setVisibility(View.VISIBLE);
            binding.tvSectionDuration.setVisibility(View.VISIBLE);
            binding.tvSectionTitle.setText(section.getTitle());
            binding.tvSectionDuration.setText(section.getDuration());

            // Hide lesson layout
            itemView.findViewById(R.id.lessonLayout).setVisibility(View.GONE);
        }

        void bindLesson(Lesson lesson) {
            binding.tvSectionTitle.setVisibility(View.GONE);
            binding.tvSectionDuration.setVisibility(View.GONE);

            // Show lesson layout
            itemView.findViewById(R.id.lessonLayout).setVisibility(View.VISIBLE);

            binding.tvLessonNumber.setText(lesson.getNumber());
            binding.tvLessonTitle.setText(lesson.getTitle());
            binding.tvLessonDuration.setText(lesson.getDuration());

            // Set lesson status icon
            binding.ivLessonStatus.setImageResource(
                lesson.isLocked() ? R.drawable.ic_lock : R.drawable.ic_play_circle
            );
            binding.ivLessonStatus.setImageTintList(
                itemView.getContext().getColorStateList(
                    lesson.isLocked() ? R.color.grey : R.color.blue
                )
            );

            // Set click listener
            itemView.setOnClickListener(v -> {
                if (listener != null && !lesson.isLocked()) {
                    listener.onLessonClick(lesson);
                }
            });
        }
    }
} 