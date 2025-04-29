package com.example.e_learningcourse.ui.bookmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.base.BaseViewModel;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.repository.BookmarkRepository;

public class BookmarkViewModel extends BaseViewModel {
    private BookmarkRepository bookmarkRepository;
    private final MutableLiveData<ApiResponse<PaginateResponse<CourseResponse>>> _courses = new MutableLiveData<>();
    private final LiveData<ApiResponse<PaginateResponse<CourseResponse>>> courses= _courses;
    private int currentPage = 0;
    private final int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    public BookmarkViewModel(){
        bookmarkRepository = new BookmarkRepository();
    }

    public LiveData<ApiResponse<PaginateResponse<CourseResponse>>> getCourses() {
        return courses;
    }
    public void resetPagination() {
        currentPage = 0;
        isLastPage = false;
        isLoading = false;
        _courses.setValue(null);
    }
    public void fetchBookmark() {
        if (isLoading || isLastPage) return;
        isLoading = true;
        bookmarkRepository.getBookmarkByUser(currentPage, pageSize).observeForever(response -> {
            if (response != null) {
                isLastPage = response.getData().isLast();
                currentPage++;
                _courses.setValue(response);
            }
            isLoading = false;
        });
    }
    public void addBookmark(Long courseId) {
        bookmarkRepository.addBookmark(courseId).observeForever(response -> {
            if (response != null && response.isSuccess()) {
                fetchBookmark();
            }
        });
    }
    public void toggleBookmark(Long courseId, boolean isBookmarked) {
        if (isBookmarked) {
            addBookmark(courseId);
        } else {
            removeBookmark(courseId);
        }
    }

    private void removeBookmark(Long courseId) {
        bookmarkRepository.removeBookmark(courseId).observeForever(response -> {
            if (response != null && response.isSuccess()) {
                fetchBookmark();
            }
        });
    }
}
