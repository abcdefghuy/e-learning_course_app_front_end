package com.example.e_learningcourse.ui.lesson;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningcourse.base.BaseViewModel;
import com.example.e_learningcourse.model.response.CourseResponse;
import com.example.e_learningcourse.model.response.LessonResponse;
import com.example.e_learningcourse.model.response.PaginateResponse;
import com.example.e_learningcourse.model.response.QuizQuestionResponse;
import com.example.e_learningcourse.repository.LessonRepository;

import java.util.List;

public class LessonViewModel extends BaseViewModel {
    private LessonRepository lessonRepository;
    private final MutableLiveData<PaginateResponse<LessonResponse>> _lessons = new MutableLiveData<>();
    private final LiveData<PaginateResponse<LessonResponse>> lessons = _lessons;

    private final MutableLiveData<List<QuizQuestionResponse>> _quizQuestions = new MutableLiveData<>();
    public LiveData<List<QuizQuestionResponse>> quizQuestions = _quizQuestions;

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
    public LiveData<List<QuizQuestionResponse>> getQuizQuestions() {
        return quizQuestions;
    }

    public void fetchLessonsByCourse(Long courseId) {
        if (isLoading || isLastPage) return;
        isLoading = true;
        lessonRepository.getLessonByCourse(courseId,currentPage, pageSize).observeForever(response -> {
            if (response != null && response.isSuccess()) {
                isLastPage = response.getData().isLast();
                currentPage++;
                _lessons.setValue(response.getData());
            }
            isLoading = false;
        });
    }
    public void updateLessonProgress(Long lessonId) {
        lessonRepository.updateLessonProgress(lessonId).observeForever(response -> {
            if (response != null && response.isSuccess()) {
                _lessonUpdateResult.postValue(true); // Thành công
            } else {
                _lessonUpdateResult.postValue(false); // Thất bại
            }
        });
    }
    public void fetchLessonsDemoByCourseId(Long courseId) {
        lessonRepository.getLessonDemoByCourse(courseId, currentPage, pageSize).observeForever(response -> {
            if (response != null && response.isSuccess()) {
                isLastPage = response.getData().isLast();
                currentPage++;
                _lessons.setValue(response.getData());
            }
        });
    }
    public void fetchQuizQuestions(Long lessonId) {
        lessonRepository.getQuizByLesson(lessonId).observeForever(response -> {
            if (response != null && response.isSuccess()) {
                _quizQuestions.setValue(response.getData());
            }
        });
    }


}
