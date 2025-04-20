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


    public void fetchLessonsByCourse(Long courseId) {
        if (isLoading || isLastPage) return;
        isLoading = true;
        lessonRepository.getLessonByCourse(courseId,currentPage, pageSize).observeForever(response -> {
            if (response != null) {
                isLastPage = response.isLast();
                currentPage++;
                _lessons.setValue(response);
            }
            isLoading = false;
        });
    }

}
