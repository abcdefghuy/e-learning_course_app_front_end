package com.example.e_learningcourse.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.model.Course;
import com.example.e_learningcourse.ui.CoursesActivity;

import java.util.List;
import java.util.Locale;

public class ContinueLearningAdapter extends RecyclerView.Adapter<ContinueLearningAdapter.CourseViewHolder> {
    private final List<Course> courses;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Course course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ContinueLearningAdapter(List<Course> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_continue_learning, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.bind(course);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final ImageView courseImage;
        private final TextView courseCategory;
        private final TextView courseTitle;
        private final TextView instructorName;
        private final ProgressBar progressBar;
        private final TextView progressText;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.courseImage);
            courseCategory = itemView.findViewById(R.id.courseCategory);
            courseTitle = itemView.findViewById(R.id.courseTitle);
            instructorName = itemView.findViewById(R.id.instructorName);
            progressBar = itemView.findViewById(R.id.progressBar);
            progressText = itemView.findViewById(R.id.progressText);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(courses.get(position));
                }
            });
        }

        public void bind(Course course) {
            courseImage.setImageResource(course.getThumbnailResId());
            courseCategory.setText("Design"); // You can make this dynamic if needed
            courseTitle.setText(course.getTitle());
            instructorName.setText(course.getInstructor());
            
            // Set progress
            int progress = course.getProgress();
            progressBar.setProgress(progress);
            progressText.setText(String.format(Locale.getDefault(), "%d/25", progress));

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), CoursesActivity.class);
//                intent.putExtra("course_id", course.getId());
//                intent.putExtra("course_title", course.getTitle());
//                intent.putExtra("course_instructor", course.getInstructor());
//                intent.putExtra("course_thumbnail", course.getThumbnailResId());
//                intent.putExtra("course_rating", course.getRating());
//                intent.putExtra("course_price", course.getPrice());
                v.getContext().startActivity(intent);
            });
        }
    }
}