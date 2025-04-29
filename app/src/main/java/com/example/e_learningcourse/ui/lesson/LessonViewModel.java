package com.example.e_learningcourse.ui.lesson;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningcourse.base.BaseViewModel;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.LessonResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.repository.LessonRepository;

public class LessonViewModel extends BaseViewModel {
    private LessonRepository lessonRepository;
    private final MutableLiveData<PaginateResponse<LessonResponse>> _lessons = new MutableLiveData<>();
    private final LiveData<PaginateResponse<LessonResponse>> lessons = _lessons;
    private int currentPage = 0;
    private final int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    public LessonViewModel() {
        lessonRepository = new LessonRepository();
    }
    public LiveData<PaginateResponse<LessonResponse>> getLessons() {
        return lessons;
    }
    private MutableLiveData<Boolean> _lessonUpdateResult = new MutableLiveData<>();
    public LiveData<Boolean> lessonUpdateResult = _lessonUpdateResult;


    public void fetchLessonsByCourse(Long courseId) {
        if (isLoading || isLastPage) return;
        isLoading = true;
        lessonRepository.getLessonByCourse(courseId,currentPage, pageSize).observeForever(response -> {
            if (response != null) {
                isLastPage = response.getData().isLast();
                currentPage++;
                _lessons.setValue(response.getData());
            }
            isLoading = false;
        });
    }
    public void updateLessonProgress(Long lessonId) {
        lessonRepository.updateLessonProgress(lessonId).observeForever(response -> {
            if (response != null) {
                _lessonUpdateResult.postValue(true); // Thành công
            } else {
                _lessonUpdateResult.postValue(false); // Thất bại
            }
        });
    }

}
