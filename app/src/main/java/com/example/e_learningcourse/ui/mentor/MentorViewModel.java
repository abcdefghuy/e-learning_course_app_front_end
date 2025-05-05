package com.example.e_learningcourse.ui.mentor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.model.response.CourseDetailResponse;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.MentorResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.repository.MentorRepository;
import com.example.e_learningcourse.ui.base.BaseViewModel;

public class MentorViewModel extends BaseViewModel {
    private MentorRepository mentorRepository;

    public MentorViewModel() {
        mentorRepository = new MentorRepository();
    }
    private final MutableLiveData<PaginateResponse<MentorResponse>> _mentors = new MutableLiveData<>();
    private final LiveData<PaginateResponse<MentorResponse>> mentors = _mentors;
    private int currentPage = 0;
    private final int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    public LiveData<PaginateResponse<MentorResponse>> getMentors() {
        return mentors;
    }
    public void fetchMentors() {
        if (isLoading || isLastPage) return;
        isLoading = true;
        mentorRepository.getAllMentors().observeForever(response -> {
            if (response != null) {
                isLastPage = response.getData().isLast();
                currentPage++;
                _mentors.setValue(response.getData());
            }
            isLoading = false;
        });
    }
    public void fetchTopMentors() {
        mentorRepository.getTopMentors().observeForever(response -> {
            if (response != null) {
                _mentors.setValue(response.getData());
            }
        });
    }
}
