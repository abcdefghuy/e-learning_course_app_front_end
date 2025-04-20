package com.example.e_learningcourse.ui.review;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.model.response.ReviewResponse;
import com.example.e_learningcourse.repository.ReviewRepository;
import com.example.e_learningcourse.ui.base.BaseViewModel;

public class ReviewViewModel extends BaseViewModel {
    private int currentPage = 0;
    private final int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    private ReviewRepository reviewRepository;
    private final MutableLiveData<PaginateResponse<ReviewResponse>> _reviews = new MutableLiveData<>();
    private final LiveData<PaginateResponse<ReviewResponse>> reviews = _reviews;

    public ReviewViewModel() {
        reviewRepository = new ReviewRepository();
    }
    public LiveData<PaginateResponse<ReviewResponse>> getReviews() {
        return reviews;
    }
    public void fetchReviewsByCourse(Long courseId) {
        if (isLoading || isLastPage) return;
        isLoading = true;
        reviewRepository.getReviewByLesson(courseId,currentPage, pageSize ).observeForever(response -> {
            if (response != null) {
                isLastPage = response.isLast();
                currentPage++;
                _reviews.setValue(response);
            }
            isLoading = false;
        });
    }

}
