package com.example.e_learningcourse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_learningcourse.R;
import com.example.e_learningcourse.model.Course;
import com.example.e_learningcourse.model.response.ContinueCourseResponse;
import com.example.e_learningcourse.ui.mycourse.CoursesActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContinueLearningAdapter extends RecyclerView.Adapter<ContinueLearningAdapter.CourseViewHolder> {
    private List<ContinueCourseResponse> courses;
    private OnItemClickListener listener;
    private final Context context;

    public void addCourses(List<ContinueCourseResponse> content) {
        int oldSize = content.size();
        courses.addAll(content);
        notifyItemRangeInserted(oldSize, content.size());
    }

    public interface OnItemClickListener {
        void onItemClick(ContinueCourseResponse course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ContinueLearningAdapter(Context context) {
        this.context = context;
        this.courses = new ArrayList<>();
    }
    public void setCourses(List<ContinueCourseResponse> courses) {
        this.courses = courses != null ? courses : new ArrayList<>();
        notifyDataSetChanged();
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
        ContinueCourseResponse course = courses.get(position);
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

        public void bind(ContinueCourseResponse course) {
            Glide.with(context)
                    .load(course.getCourseImageUrl())
                    //.placeholder(R.drawable.ic_placeholder)
                    .into(courseImage);
            courseCategory.setText(course.getCategoryName()); // You can make this dynamic if needed
            courseTitle.setText(course.getCourseTitle());
            instructorName.setText("Huy");
            
            // Set progress
            int progress = course.getProgress();
            progressBar.setProgress(progress);
            progressText.setText(String.format(Locale.getDefault(), "%d%%", progress));

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), CoursesActivity.class);
                intent.putExtra("courseId", course.getCourseId());
                v.getContext().startActivity(intent);
            });
        }
    }
}