package com.example.e_learningcourse.ui.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.CourseAdapter;
import com.example.e_learningcourse.adapter.PopularCoursesAdapter;
import com.example.e_learningcourse.adapter.RecentSearchAdapter;
import com.example.e_learningcourse.data.local.RecentCourseManager;
import com.example.e_learningcourse.databinding.FragmentRecentSearchBinding;
import com.example.e_learningcourse.model.Course;
import com.example.e_learningcourse.model.response.CourseDetailResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.ui.bookmark.BookmarkViewModel;
import com.example.e_learningcourse.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchRecentFragment extends Fragment implements RecentSearchAdapter.OnItemRemoveListener, CourseAdapter.OnBookmarkClickListener, RecentSearchAdapter.OnItemClickListener {
    private FragmentRecentSearchBinding binding;
    private CourseAdapter recentViewAdapter;
    private RecentSearchAdapter recentSearchAdapter;
    private List<CourseResponse> recentCourses;
    private BookmarkViewModel bookmarkViewModel;
    public interface OnRecentSearchInteractionListener {
        void onRecentKeywordClicked(String keyword, boolean isCategory);
    }
    private OnRecentSearchInteractionListener interactionListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnRecentSearchInteractionListener) {
            interactionListener = (OnRecentSearchInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnRecentSearchInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecentSearchList();
        setupRecentViewList();
        setupClearAllButton();
        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        recentSearchAdapter.setOnItemRemoveListener(this);
        recentViewAdapter.setOnBookmarkClickListener(this);
        recentSearchAdapter.setOnItemClickListener(this);

        // Observe bookmark changes
        bookmarkViewModel.getBookmarkStateChanged().observe(getViewLifecycleOwner(), changed -> {
            if (changed) {
                // Refresh the list when bookmark state changes
                setupRecentViewList();
            }
        });
    }

    private void setupClearAllButton() {
        binding.tvClearAll.setOnClickListener(v -> {
            // Xóa tất cả khóa học đã xem
            RecentCourseManager.clearRecentCourses(requireContext());
            // Cập nhật UI
            recentCourses.clear();
            recentViewAdapter.clearCourses();
            // Hiển thị layout no data
            binding.recentViewCard.setVisibility(View.GONE);
            binding.layoutNoRecentView.setVisibility(View.VISIBLE);
            // Hiển thị thông báo
            NotificationUtils.showInfo(requireContext(), binding.getRoot(), getString(R.string.recent_courses_cleared));
        });
    }

    private void setupRecentSearchList() {
        recentSearchAdapter = new RecentSearchAdapter();
        binding.recentSearchList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recentSearchList.setAdapter(recentSearchAdapter);

        // Add sample data
        List<String> recentSearches = RecentCourseManager.getRecentSearches(requireContext());
        if (recentSearches == null || recentSearches.isEmpty()) {
            // Show no data layout
            binding.layoutNoRecentSearch.setVisibility(View.VISIBLE);
            binding.recentSearchCard.setVisibility(View.GONE);
        } else {
            binding.layoutNoRecentSearch.setVisibility(View.GONE);
            binding.recentSearchCard.setVisibility(View.VISIBLE);
        }
        recentSearchAdapter.setSearches(recentSearches);
        // Handle item removal
        recentSearchAdapter.setOnItemRemoveListener(position ->
                recentSearchAdapter.removeItem(position));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupRecentViewList() {
        // Initialize adapter
        recentViewAdapter = new CourseAdapter(requireContext());
        // Set up RecyclerView
        binding.rvRecentView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvRecentView.setAdapter(recentViewAdapter);
        
        // Add sample data
        recentCourses = RecentCourseManager.getRecentCourses(requireContext());
        if (recentCourses != null && !recentCourses.isEmpty()) {
            // Show recent view list
            binding.recentViewCard.setVisibility(View.VISIBLE);
            binding.layoutNoRecentView.setVisibility(View.GONE);
            recentViewAdapter.addCourses(recentCourses);
        } else {
            // Show no data layout
            binding.recentViewCard.setVisibility(View.GONE);
            binding.layoutNoRecentView.setVisibility(View.VISIBLE);
        }
        
        // Force layout update
        binding.rvRecentView.requestLayout();
        binding.rvRecentView.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemRemove(int position) {
        if (position >= 0 && position < recentSearchAdapter.getItemCount()) {
            // Xoá phần tử trong RecentCourseManager
            List<String> currentSearches = RecentCourseManager.getRecentSearches(requireContext());
            if (currentSearches != null && position < currentSearches.size()) {
                String keywordToRemove = currentSearches.get(position);
                RecentCourseManager.removeSearch(requireContext(), keywordToRemove);
            }
            // Cập nhật adapter
            recentSearchAdapter.removeItem(position);
            // Kiểm tra lại danh sách tìm kiếm sau khi xóa
            List<String> updatedSearches = RecentCourseManager.getRecentSearches(requireContext());
            if (updatedSearches != null && !updatedSearches.isEmpty()) {
                recentSearchAdapter.setSearches(updatedSearches); // Cập nhật lại adapter
                binding.layoutNoRecentSearch.setVisibility(View.GONE); // Ẩn layout no data
                binding.recentSearchCard.setVisibility(View.VISIBLE); // Hiển thị danh sách tìm kiếm
            } else {
                binding.layoutNoRecentSearch.setVisibility(View.VISIBLE); // Hiển thị layout no data
                binding.recentSearchCard.setVisibility(View.GONE); // Ẩn danh sách tìm kiếm
            }
        }
    }

    @Override
    public void onBookmarkClick(CourseResponse course, int position) {
        // Toggle bookmark state
        boolean newBookmarkState = !course.isBookmarked();
        course.setBookmarked(newBookmarkState); // cập nhật trạng thái
        // Gọi ViewModel để xử lý thêm hoặc xóa bookmark
         bookmarkViewModel.toggleBookmark(course.getCourseId(), newBookmarkState);
        // Thông báo và cập nhật UI
        String message = newBookmarkState ?
                getString(R.string.bookmark_added) : getString(R.string.bookmark_removed);
        NotificationUtils.showInfo(requireContext(), binding.getRoot(), message);
        recentViewAdapter.updateCourse(position);
    }

    @Override
    public void onItemClick(String keyword) {
        if (!keyword.isEmpty() && interactionListener != null) {
            // Kiểm tra xem keyword có phải là tên danh mục không
            boolean isCategory = RecentCourseManager.isCategoryName(requireContext(), keyword);
            interactionListener.onRecentKeywordClicked(keyword, isCategory);
        }
    }
}
