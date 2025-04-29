package com.example.e_learningcourse.ui.lesson;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.LessonsAdapter;
import com.example.e_learningcourse.databinding.ActivityLessonPlayerBinding;
import com.example.e_learningcourse.model.response.LessonResponse;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LessonPlayerActivity extends AppCompatActivity {
    private ActivityLessonPlayerBinding binding;
    private LessonResponse currentLesson;
    private List<LessonResponse> nextLessons;
    private YouTubePlayerView youTubePlayerView;
    private float videoDuration = 0;
    private boolean lessonMarked = false;
    private LessonViewModel lessonViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLessonPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lessonViewModel = new ViewModelProvider(this).get(LessonViewModel.class);

        // Get lesson data from intent
        currentLesson = getIntent().getParcelableExtra("current_lesson");
        nextLessons = getIntent().getParcelableArrayListExtra("next_lessons");

        if (currentLesson == null) {
            Toast.makeText(this, "Lesson data not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupUI();
        initializeYouTubePlayer(currentLesson);
    }

    private void setupUI() {
        if (currentLesson == null) {
            Toast.makeText(this, "Lesson data not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set current lesson details
        binding.tvLessonTitle.setText(currentLesson.getLessonName());
        binding.tvLessonDuration.setText(String.valueOf(currentLesson.getDuration()));

        // Set up next lessons list
        if (nextLessons != null && !nextLessons.isEmpty()) {
            binding.rvLessons.setVisibility(View.VISIBLE);
            binding.rvLessons.setLayoutManager(new LinearLayoutManager(this));
            LessonsAdapter lessonsAdapter = new LessonsAdapter();
            lessonsAdapter.setOnLessonClickListener(lesson -> {
                currentLesson = lesson;
                setupUI();
                initializeYouTubePlayer(currentLesson);
            });
            lessonsAdapter.setLesson(nextLessons);
            binding.rvLessons.setAdapter(lessonsAdapter);
        } else {
            binding.rvLessons.setVisibility(View.GONE);
        }
    }

    private void initializeYouTubePlayer(LessonResponse lesson) {
        youTubePlayerView = binding.youtubePlayerView;
        youTubePlayerView.setEnableAutomaticInitialization(false);
        getLifecycle().addObserver(youTubePlayerView);
        View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.custom_player_ui);
        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                CustomPlayerUiController customPlayerUiController = new CustomPlayerUiController(customPlayerUi, youTubePlayer, youTubePlayerView);
                youTubePlayer.addListener(customPlayerUiController);
                YouTubePlayerUtils.loadOrCueVideo(youTubePlayer, getLifecycle(), extractVideoId(lesson.getLessonVideoUrl()), 0F);
            }

            @Override
            public void onVideoDuration(@NonNull YouTubePlayer youTubePlayer, float duration) {
                videoDuration = duration;
            }
            @Override
            public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float second) {
                if (videoDuration > 0 && second >= videoDuration - 1 && !lessonMarked) {
                    markLessonCompleted();
                }
            }
        };

        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
        youTubePlayerView.initialize(listener, options);
    }
    private void markLessonCompleted() {
        lessonViewModel.lessonUpdateResult.observe(LessonPlayerActivity.this, success -> {
            if (success != null && success) {
                Toast.makeText(LessonPlayerActivity.this, "Bài học đã được đánh dấu hoàn thành!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String extractVideoId(String url) {
        // Split the URL by "/"
        String[] parts = url.split("/");
        return parts[parts.length - 1];  // Return the last part as the video ID
    }


}