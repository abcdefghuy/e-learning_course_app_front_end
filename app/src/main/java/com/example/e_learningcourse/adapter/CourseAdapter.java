package com.example.e_learningcourse.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ItemCourseBinding;
import com.example.e_learningcourse.model.Course;
import com.example.e_learningcourse.ui.CourseDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> courses = new ArrayList<>();
    private OnCourseClickListener listener;

    public interface OnCourseClickListener {
        void onBookmarkClick(Course course, int position);
    }

    public void setOnCourseClickListener(OnCourseClickListener listener) {
        this.listener = listener;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseBinding binding = ItemCourseBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new CourseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.bind(courses.get(position), position);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final ItemCourseBinding binding;
        private boolean isBookmarked = false;

        CourseViewHolder(ItemCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Course course, int position) {
            // Set course thumbnail
            binding.ivCourseThumbnail.setImageResource(course.getThumbnailResId());

            // Set rating
            binding.tvRating.setText(String.format(Locale.getDefault(), "%.1f", course.getRating()));

            // Set course title
            binding.tvCourseTitle.setText(course.getTitle());

            // Set instructor info
            binding.tvInstructorName.setText(course.getInstructor());
            binding.ivInstructorAvatar.setImageResource(course.getInstructorAvatar());
            // Format and set price
            binding.tvPrice.setText(String.format(Locale.US, "$%.2f", course.getPrice()));
            binding.ivBookmark.setSelected(course.isBookmarked());

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), CourseDetailsActivity.class);
                intent.putExtra("course_id", course.getId());
                intent.putExtra("course_title", course.getTitle());
                intent.putExtra("course_instructor", course.getInstructor());
                intent.putExtra("course_thumbnail", course.getThumbnailResId());
                intent.putExtra("course_rating", course.getRating());
                intent.putExtra("course_price", course.getPrice());
                v.getContext().startActivity(intent);
            });

                binding.ivBookmark.setImageResource(course.isBookmarked() ?
                        R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark);
                binding.ivBookmark.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onBookmarkClick(course, position);
                    }
                });

        }
    }
} 