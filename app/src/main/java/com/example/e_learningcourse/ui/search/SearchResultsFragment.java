package com.example.e_learningcourse.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.CourseAdapter;
import com.example.e_learningcourse.data.local.RecentCourseManager;
import com.example.e_learningcourse.databinding.FragmentSearchResultsBinding;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.ui.bookmark.BookmarkViewModel;
import com.example.e_learningcourse.ui.course.CourseViewModel;
import com.google.android.material.tabs.TabLayout;
import com.example.e_learningcourse.utils.NotificationUtils;

public class SearchResultsFragment extends Fragment implements CourseAdapter.OnItemSaveClickListener, CourseAdapter.OnBookmarkClickListener {
    private FragmentSearchResultsBinding binding;
    private CourseAdapter resultsAdapter;
    private CourseViewModel courseViewModel;
    private BookmarkViewModel bookmarkViewModel;
    private String currentQuery = "";
    private boolean isLoading = false;
    private boolean hasMoreData = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchResultsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        setupViews();
        observeData();

        // Observe bookmark changes
        bookmarkViewModel.getBookmarkStateChanged().observe(getViewLifecycleOwner(), changed -> {
            if (changed) {
                // Refresh the search results when bookmark state changes
                performSearch(currentQuery);
            }
        });
    }

    private void setupViews() {
        resultsAdapter = new CourseAdapter(requireContext());
        resultsAdapter.setOnItemSaveClickListener(this);
        resultsAdapter.setOnBookmarkClickListener(this);
        binding.rvResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvResults.setAdapter(resultsAdapter);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                performSearch(currentQuery);
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        binding.rvResults.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && !isLoading && hasMoreData &&
                        layoutManager.findLastVisibleItemPosition() + 2 >= layoutManager.getItemCount()) {
                    loadResults();
                }
            }
        });
    }

    public void updateSearch(String query) {
        if (binding == null) return;
        currentQuery = query;
        binding.tvResultsTitle.setText(String.format("Results for \"%s\"", query));
        RecentCourseManager.saveSearchKeyword(requireContext(), query);
        performSearch(query);
    }
    public void updateSearchCategory(String query) {
        if (binding == null) return;
        currentQuery = query;
        binding.tvResultsTitle.setText(String.format("Results for \"%s\"", query));
        RecentCourseManager.saveSearchKeyword(requireContext(), query);
        performSearchByCategoryName(query);
    }

    private void performSearchByCategoryName(String query) {
        if (binding == null) return;
        int selectedTab = binding.tabLayout.getSelectedTabPosition();
        if (selectedTab == 0) {
            isLoading = true;
            courseViewModel.fetchCoursesByCategory(query);
        } else {
            showNoData();
        }
    }

    private void observeData() {
        courseViewModel.getCourses().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getContent() != null) {
                if (response.getContent().isEmpty()) {
                    showNoData();
                } else {
                    resultsAdapter.setCourses(response.getContent());
                    binding.tvResultsCount.setText("(" + response.getContent().size() + ") Results Found");
                    binding.rvResults.setVisibility(View.VISIBLE);
                    binding.layoutNoData.setVisibility(View.GONE);
                    hasMoreData = !response.isLast();
                    isLoading = false;
                }
            } else {
                showNoData();
            }
        });
    }

    private void performSearch(String query) {
        if (binding == null) return;
        int selectedTab = binding.tabLayout.getSelectedTabPosition();
        if (selectedTab == 0) {
            isLoading = true;
            courseViewModel.fetchSearchCourse(query);
        } else {
            showNoData();
        }
    }

    private void loadResults() {
        if (!hasMoreData) return;
        isLoading = true;
        courseViewModel.fetchSearchCourse(currentQuery);
    }

    private void showNoData() {
        binding.rvResults.setVisibility(View.GONE);
        binding.layoutNoData.setVisibility(View.VISIBLE);
        binding.tvResultsCount.setText("0 Results Found");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSaveClick(CourseResponse course) {
        RecentCourseManager.saveCourse(requireContext(), course);
    }

    @Override
    public void onBookmarkClick(CourseResponse course, int position) {
        boolean newBookmarkState = !course.isBookmarked();
        course.setBookmarked(newBookmarkState); // cập nhật trạng thái
        // Gọi ViewModel để xử lý thêm hoặc xóa bookmark
        bookmarkViewModel.toggleBookmark(course.getCourseId(), newBookmarkState);
        // Thông báo và cập nhật UI
        String message = newBookmarkState ?
                getString(R.string.bookmark_added) :
                getString(R.string.bookmark_removed);
        showMessage(message);
        resultsAdapter.notifyItemChanged(position);
    }

    private void showMessage(String message) {
        NotificationUtils.showInfo(requireContext(), binding.getRoot(), message);
    }
}
