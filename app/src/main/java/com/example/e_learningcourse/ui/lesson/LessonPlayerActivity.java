package com.example.e_learningcourse.ui.lesson;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.FrameLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.LessonsAdapter;
import com.example.e_learningcourse.databinding.ActivityLessonPlayerBinding;
import com.example.e_learningcourse.model.response.LessonResponse;
import com.example.e_learningcourse.ui.course.AboutCourseFragment;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.example.e_learningcourse.utils.NotificationUtils;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class LessonPlayerActivity extends AppCompatActivity {
    private ActivityLessonPlayerBinding binding;
    private LessonResponse currentLesson;
    private List<LessonResponse> nextLessons;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer currentPlayer;

    private float videoDuration = 0;
    private boolean lessonMarked = false;
    private boolean quizTriggered = false;
    private LessonViewModel lessonViewModel;

    private boolean isDemo;
    private long courseId;

    private final ActivityResultLauncher<Intent> quizLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK) {
                boolean passed = result.getData().getBooleanExtra("quiz_passed", false);
                if (passed) {
                    markLessonCompleted();
                } else {
                    showMessage("Bạn cần trả lời đúng ít nhất 70% số câu hỏi để hoàn thành bài học! Làm lại nhé.");
                    showQuizScreen();
                }
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = ActivityLessonPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lessonViewModel = new ViewModelProvider(this).get(LessonViewModel.class);

        // Cấu hình màn hình
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Get lesson data from intent
        currentLesson = getIntent().getParcelableExtra("current_lesson");
        nextLessons = getIntent().getParcelableArrayListExtra("next_lessons");
        isDemo = getIntent().getBooleanExtra("isDemo", false);
        courseId = currentLesson != null ? currentLesson.getCourseId() : -1;

        if (currentLesson == null) {
            showMessage("Lesson data not found");
            finish();
            return;
        }

        setupUI();
        initializeYouTubePlayer();
    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            // Ẩn phần nội dung bên dưới
            binding.scrollContent.setVisibility(View.GONE);

            // CardView chiếm toàn màn hình, bỏ bo góc
            binding.cardVideo.setRadius(0);
            CoordinatorLayout.LayoutParams cardParams = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            );
            binding.cardVideo.setLayoutParams(cardParams);

            // FrameLayout và YouTubePlayerView cũng full màn hình
            FrameLayout.LayoutParams videoParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            );
            binding.videoContainer.setLayoutParams(videoParams);
            binding.youtubePlayerView.setLayoutParams(videoParams);
        } else {
            // Hiện lại nội dung, đặt lại layout như cũ
            binding.scrollContent.setVisibility(View.VISIBLE);

            float radius = 8 * getResources().getDisplayMetrics().density;
            binding.cardVideo.setRadius(radius);
            CoordinatorLayout.LayoutParams cardParams = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            binding.cardVideo.setLayoutParams(cardParams);

            FrameLayout.LayoutParams videoParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            binding.videoContainer.setLayoutParams(videoParams);
            binding.youtubePlayerView.setLayoutParams(videoParams);
        }
    }

    private void setupUI() {
        if (currentLesson == null) {
            showMessage("Lesson data not found");
            finish();
            return;
        }

        // Set current lesson details
        binding.tvLessonTitle.setText(currentLesson.getLessonName());
        binding.tvLessonDuration.setText(AboutCourseFragment.formatDuration(currentLesson.getDuration()));
        binding.tvLessonDescription.setText(currentLesson.getLessonDescription());
        // Set up next lessons list
        if (nextLessons != null && !nextLessons.isEmpty()) {
            binding.rvLessons.setVisibility(View.VISIBLE);
            binding.rvLessons.setLayoutManager(new LinearLayoutManager(this));
            LessonsAdapter lessonsAdapter = new LessonsAdapter();
            lessonsAdapter.setOnLessonClickListener(lesson -> {
                currentLesson = lesson;
                updateLessonUI();
                loadNewLesson(lesson);
            });
            lessonsAdapter.setLesson(nextLessons);
            binding.rvLessons.setAdapter(lessonsAdapter);
        } else {
            binding.rvLessons.setVisibility(View.GONE);
        }
    }

    private void updateLessonUI() {
        binding.tvLessonTitle.setText(currentLesson.getLessonName());
        binding.tvLessonDuration.setText(AboutCourseFragment.formatDuration(currentLesson.getDuration()));
        binding.tvLessonDescription.setText(currentLesson.getLessonDescription());
    }

    private void initializeYouTubePlayer() {
        youTubePlayerView = binding.youtubePlayerView;
        youTubePlayerView.setEnableAutomaticInitialization(false);
        getLifecycle().addObserver(youTubePlayerView);

        View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.custom_player_ui);
        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                currentPlayer = youTubePlayer;
                CustomPlayerUiController customPlayerUiController = new CustomPlayerUiController(customPlayerUi, youTubePlayer, youTubePlayerView);
                youTubePlayer.addListener(customPlayerUiController);
                loadNewLesson(currentLesson);
            }

            @Override
            public void onVideoDuration(@NonNull YouTubePlayer youTubePlayer, float duration) {
                videoDuration = duration;
            }

            @Override
            public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float second) {
                if (videoDuration > 0 && second >= videoDuration - 1 && !lessonMarked && !quizTriggered) {
                    quizTriggered = true;
                    if (isDemo) {
                        showDemoEndDialog();
                    } else if (currentLesson.hasQuiz()) {
                        showQuizScreen();
                    } else {
                        markLessonCompleted();
                    }
                }
            }
        };

        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
        youTubePlayerView.initialize(listener, options);
    }

    private void loadNewLesson(LessonResponse lesson) {
        lessonMarked = false;
        quizTriggered = false;
        if (currentPlayer != null) {
            YouTubePlayerUtils.loadOrCueVideo(currentPlayer, getLifecycle(), extractVideoId(lesson.getLessonVideoUrl()), 0F);
        }
    }

    private void markLessonCompleted() {
        lessonMarked = true;
        lessonViewModel.updateLessonProgress(currentLesson.getLessonId());
        lessonViewModel.lessonUpdateResult.observe(LessonPlayerActivity.this, success -> {
            if (success != null && success) {
                showMessage("Bài học đã được đánh dấu hoàn thành!");
                refreshLessonList();
            }
        });
    }

    public String extractVideoId(String url) {
        // Split the URL by "/"
        String[] parts = url.split("/");
        return parts[parts.length - 1];  // Return the last part as the video ID
    }

    private void refreshLessonList() {
        lessonViewModel.fetchLessonsByCourse(currentLesson.getCourseId());
        lessonViewModel.getLessons().observe(LessonPlayerActivity.this, lessons -> {
            if (lessons != null && !lessons.getContent().isEmpty()) {
                // Cập nhật lại nextLessons
                nextLessons = new ArrayList<>();
                for (LessonResponse lesson : lessons.getContent()) {
                    if (!lesson.getLessonId().equals(currentLesson.getLessonId())) {
                        nextLessons.add(lesson);
                    }
                }
                // Cập nhật RecyclerView
                setupUI(); // gọi lại để cập nhật danh sách
            }
        });
    }

    private void showMessage(String message) {
        NotificationUtils.showInfo(this, binding.getRoot(), message);
    }

    private void showQuizScreen() {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("lessonId", currentLesson.getLessonId());
        quizLauncher.launch(intent);
    }

    private void showDemoEndDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_demo_end);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        MaterialButton btnEnroll = dialog.findViewById(R.id.btnEnroll);
        MaterialButton btnCancel = dialog.findViewById(R.id.btnCancel);

        tvTitle.setText("Demo Lesson Completed");
        tvMessage.setText("You've completed the demo lesson. Would you like to enroll in the full course to continue learning?");

        btnEnroll.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent();
            intent.putExtra("courseId", courseId);
            setResult(RESULT_OK, intent);
            finish();
        });

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });

        dialog.setCancelable(false);
        dialog.show();
    }
}