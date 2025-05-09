package com.example.e_learningcourse.ui.lesson;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ActivityQuizBinding;
import com.example.e_learningcourse.databinding.DialogQuizResultBinding;
import com.example.e_learningcourse.model.response.QuizQuestionResponse;
import com.example.e_learningcourse.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private ActivityQuizBinding binding;
    private List<QuizQuestionResponse> questions;
    private int currentIndex = 0;
    private int correctCount = 0;
    private int[] userAnswers; // -1: chưa chọn
    private long lessonId;

    private LessonViewModel lessonViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        lessonViewModel = new ViewModelProvider(this).get(LessonViewModel.class);
        setContentView(binding.getRoot());

        // Get lessonId from intent
        lessonId = getIntent().getLongExtra("lessonId", -1);
        if (lessonId == -1) {
            NotificationUtils.showError(this, binding.getRoot(), "Invalid lesson ID");
            finish();
            return;
        }

        // Load questions from ViewModel
        lessonViewModel.getQuizQuestions().observe(this, questionsList -> {
            if (questionsList != null && !questionsList.isEmpty()) {
                questions = questionsList;
                userAnswers = new int[questions.size()];
                for (int i = 0; i < userAnswers.length; i++) userAnswers[i] = -1;
                currentIndex = 0;
                showQuestion();
            }
        });

        // Fetch questions from server
        lessonViewModel.fetchQuizQuestions(lessonId);

        setupClickListeners();
        setupBackPressHandler();
    }

    private void setupClickListeners() {
        binding.btnOption1.setOnClickListener(v -> selectAnswer(0));
        binding.btnOption2.setOnClickListener(v -> selectAnswer(1));
        binding.btnOption3.setOnClickListener(v -> selectAnswer(2));
        binding.btnOption4.setOnClickListener(v -> selectAnswer(3));
        
        binding.btnPrevious.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                showQuestion();
            }
        });
        
        binding.btnNext.setOnClickListener(v -> {
            if (currentIndex < questions.size() - 1) {
                currentIndex++;
                showQuestion();
            }
        });
        
        binding.btnSubmit.setOnClickListener(v -> submitQuiz());
    }

    private void setupBackPressHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Không làm gì hoặc show thông báo
                // Toast.makeText(QuizActivity.this, "Bạn phải hoàn thành quiz!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showQuestion() {
        if (questions == null || questions.isEmpty()) return;
        
        QuizQuestionResponse q = questions.get(currentIndex);
        binding.tvQuestionNumber.setText("Question: " + (currentIndex + 1) + "/" + questions.size());
        binding.tvQuestion.setText(q.getQuestionText());
        binding.btnOption1.setText(q.getOptions().get(0));
        binding.btnOption2.setText(q.getOptions().get(1));
        binding.btnOption3.setText(q.getOptions().get(2));
        binding.btnOption4.setText(q.getOptions().get(3));
        
        // Show/hide Submit button based on question position
        binding.btnSubmit.setVisibility(currentIndex == questions.size() - 1 ? View.VISIBLE : View.GONE);
        
        // Show/hide Previous and Next buttons
        binding.btnPrevious.setVisibility(currentIndex == 0 ? View.GONE : View.VISIBLE);
        binding.btnNext.setVisibility(currentIndex == questions.size() - 1 ? View.GONE : View.VISIBLE);
        
        // Highlight lựa chọn của user
        resetOptionColors();
        if (userAnswers[currentIndex] != -1) {
            highlightSelected(userAnswers[currentIndex]);
        }
    }

    private void selectAnswer(int option) {
        userAnswers[currentIndex] = option;
        highlightSelected(option);
    }

    private void highlightSelected(int option) {
        resetOptionColors();
        switch (option) {
            case 0: binding.btnOption1.setBackgroundTintList(getColorStateList(R.color.primary)); binding.btnOption1.setTextColor(getColor(R.color.white)); break;
            case 1: binding.btnOption2.setBackgroundTintList(getColorStateList(R.color.primary)); binding.btnOption2.setTextColor(getColor(R.color.white)); break;
            case 2: binding.btnOption3.setBackgroundTintList(getColorStateList(R.color.primary)); binding.btnOption3.setTextColor(getColor(R.color.white)); break;
            case 3: binding.btnOption4.setBackgroundTintList(getColorStateList(R.color.primary)); binding.btnOption4.setTextColor(getColor(R.color.white)); break;
        }
    }

    private void resetOptionColors() {
        binding.btnOption1.setBackgroundTintList(getColorStateList(R.color.button_material_light)); binding.btnOption1.setTextColor(getColor(R.color.black));
        binding.btnOption2.setBackgroundTintList(getColorStateList(R.color.button_material_light)); binding.btnOption2.setTextColor(getColor(R.color.black));
        binding.btnOption3.setBackgroundTintList(getColorStateList(R.color.button_material_light)); binding.btnOption3.setTextColor(getColor(R.color.black));
        binding.btnOption4.setBackgroundTintList(getColorStateList(R.color.button_material_light)); binding.btnOption4.setTextColor(getColor(R.color.black));
    }

    private void submitQuiz() {
        if (questions == null || questions.isEmpty()) return;
        
        correctCount = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers[i] == questions.get(i).getCorrectIndex()) correctCount++;
        }
        float percent = (float) correctCount / questions.size();
        showResultDialog(percent);
    }

    private void showResultDialog(float percent) {
        DialogQuizResultBinding dialogBinding = DialogQuizResultBinding.inflate(LayoutInflater.from(this));
        Dialog dialog = new Dialog(this);
        dialog.setContentView(dialogBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBinding.tvScore.setText("Your Score\n" + correctCount + "/" + questions.size());
        if (percent >= 0.7f) {
            dialogBinding.tvCongrat.setText("Congratulation");
            dialogBinding.tvMessage.setText("Great job! You Did It");
            dialogBinding.btnDialogAction.setText("Back to Lesson");
            dialogBinding.btnDialogAction.setOnClickListener(v -> {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("quiz_passed", true);
                setResult(Activity.RESULT_OK, resultIntent);
                dialog.dismiss();
                finish();
            });
        } else {
            dialogBinding.tvCongrat.setText("Chưa đạt!");
            dialogBinding.tvMessage.setText("Bạn cần đúng ít nhất 70% để hoàn thành. Làm lại nhé!");
            dialogBinding.btnDialogAction.setText("Làm lại");
            dialogBinding.btnDialogAction.setOnClickListener(v -> {
                for (int i = 0; i < userAnswers.length; i++) userAnswers[i] = -1;
                currentIndex = 0;
                showQuestion();
                dialog.dismiss();
            });
        }
        dialog.setCancelable(false);
        dialog.show();
    }
    
} 