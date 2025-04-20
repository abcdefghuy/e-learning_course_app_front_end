package com.example.e_learningcourse.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ItemCourseBinding;
import com.example.e_learningcourse.databinding.ItemCourseShimmerBinding;
import com.example.e_learningcourse.databinding.ItemPopularCourseBinding;
import com.example.e_learningcourse.model.Course;
import com.example.e_learningcourse.model.response.CourseDetailResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.ui.course.CourseDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PopularCoursesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_SHIMMER = 1;

    private List<CourseResponse> courses = new ArrayList<>();
    private OnCourseClickListener listener;
    private OnItemClickListener listeners;

    private View headerBookmarkView;
    private Context context;
    private boolean showShimmer = false;

    public PopularCoursesAdapter(Context context) {
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(CourseResponse course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listeners = listener;
    }

    public interface OnCourseClickListener {
        void onBookmarkClick(CourseResponse course, int position);
    }

    public void setOnCourseClickListener(OnCourseClickListener listener) {
        this.listener = listener;
    }

    public void setHeaderBookmarkView(View view) {
        this.headerBookmarkView = view;
    }

    public void setCourses(List<CourseResponse> courses) {
        this.courses = courses != null ? courses : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void showShimmer(boolean show) {
        this.showShimmer = show;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return showShimmer ? VIEW_TYPE_SHIMMER : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SHIMMER) {
            ItemCourseShimmerBinding binding = ItemCourseShimmerBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
            return new ShimmerViewHolder(binding);
        } else {
            ItemPopularCourseBinding binding = ItemPopularCourseBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
            return new CourseViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CourseViewHolder) {
            CourseResponse course = courses.get(position);
            ((CourseViewHolder) holder).bind(course, position);
        } else if (holder instanceof ShimmerViewHolder) {
            ((ShimmerViewHolder) holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        return showShimmer ? 5 : courses.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final ItemPopularCourseBinding binding;

        public CourseViewHolder(ItemPopularCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CourseResponse course, int position) {
            binding.tvCourseTitle.setText(course.getCourseName());
            binding.tvInstructorName.setText("Huy");
            binding.tvRating.setText(String.format(Locale.getDefault(), "%.1f", course.getRating()));

            Log.d("PopularCoursesAdapter", "Binding course: " + course.getCourseName());
            Log.d("PopularCoursesAdapter", "Course price: " + course.getCoursePrice());
            Log.d("PopularCoursesAdapter", "Is best seller: " + course.isBestSeller());

            String priceText = String.format(Locale.getDefault(), "$%.2f", course.getCoursePrice());
            binding.tvPrice.setText(priceText);
            binding.tvPrice.setVisibility(View.VISIBLE);
            Log.d("PopularCoursesAdapter", "Price text set to: " + priceText);

            boolean isBestSeller = course.isBestSeller();
            binding.tvBestSeller.setVisibility(isBestSeller ? View.VISIBLE : View.GONE);
            Log.d("PopularCoursesAdapter", "Best seller visibility: " + (isBestSeller ? "VISIBLE" : "GONE"));
            
            binding.ivBookmark.setSelected(course.isBookmarked());
            Glide.with(context)
                    .load(course.getCourseImg())
                    //.placeholder(R.drawable.placeholder_img)
                    .into(binding.ivCourseThumbnail);

            Glide.with(context)
                    .load(R.drawable.avatar)
                    .placeholder(R.drawable.avatar)
                    .into(binding.ivInstructorAvatar);

            binding.ivBookmark.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onBookmarkClick(course, position);
                }
            });

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), CourseDetailsActivity.class);
                intent.putExtra("courseId", course.getCourseId());
                v.getContext().startActivity(intent);
            });
        }
    }

    class ShimmerViewHolder extends RecyclerView.ViewHolder {
        private final ItemCourseShimmerBinding binding;

        public ShimmerViewHolder(ItemCourseShimmerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            binding.shimmerThumbnail.startShimmer();
            binding.shimmerTitle.startShimmer();
            binding.shimmerAvatar.startShimmer();
            binding.shimmerInstructor.startShimmer();
            binding.shimmerPrice.startShimmer();
        }
    }
}