package com.example.e_learningcourse.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.model.Course;
import com.example.e_learningcourse.ui.CourseDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PopularCoursesAdapter extends RecyclerView.Adapter<PopularCoursesAdapter.CourseViewHolder> {
    private List<Course> courses;
    private OnBookmarkClickListener bookmarkClickListener;

    public interface OnBookmarkClickListener {
        void onBookmarkClick(Course course, int position);
    }
    public PopularCoursesAdapter() {
        this.courses = new ArrayList<>();
    }

    public PopularCoursesAdapter(List<Course> courses) {
        this.courses = courses != null ? courses : new ArrayList<>();
    }

    public void setOnBookmarkClickListener(OnBookmarkClickListener listener) {
        this.bookmarkClickListener = listener;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses != null ? courses : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popular_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.bind(course, position);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void updateCourse(int position) {
        if (position >= 0 && position < courses.size()) {
            notifyItemChanged(position);
        }
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivThumbnail;
        private final TextView tvTitle;
        private final TextView tvInstructor;
        private final TextView tvPrice;
        private final TextView tvBestSeller;
        private final ImageButton btnBookmark;

        private final ShapeableImageView ivInstructorAvatar;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvInstructor = itemView.findViewById(R.id.tvInstructor);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvBestSeller = itemView.findViewById(R.id.tvBestSeller);
            btnBookmark = itemView.findViewById(R.id.btnBookmark);
            ivInstructorAvatar = itemView.findViewById(R.id.ivInstructorAvatar);
        }

        public void bind(final Course course, final int position) {
            if (ivThumbnail != null) {
                ivThumbnail.setImageResource(course.getThumbnailResId());
            }
            if (tvTitle != null) {
                tvTitle.setText(course.getTitle());
            }
            if (tvInstructor != null) {
                tvInstructor.setText(course.getInstructor());
            }
            if (tvPrice != null) {
                tvPrice.setText(String.format(Locale.US, "$%.2f", course.getPrice()));
            }
            if (tvBestSeller != null) {
                tvBestSeller.setVisibility(course.isBestSeller() ? View.VISIBLE : View.GONE);
            }

            if(ivInstructorAvatar != null) {
                ivInstructorAvatar.setImageResource(R.drawable.avatar);
            }
            if (btnBookmark != null) {
                btnBookmark.setImageResource(course.isBookmarked() ? 
                    R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark);
                btnBookmark.setOnClickListener(v -> {
                    if (bookmarkClickListener != null) {
                        bookmarkClickListener.onBookmarkClick(course, position);
                    }
                });
            }
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), CourseDetailsActivity.class);
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