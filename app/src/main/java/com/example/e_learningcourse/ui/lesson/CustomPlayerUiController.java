package com.example.e_learningcourse.ui.lesson;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.e_learningcourse.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.utils.FadeViewHelper;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBarListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class CustomPlayerUiController extends AbstractYouTubePlayerListener {
    private final YouTubePlayerTracker playerTracker;
    private final YouTubePlayer youTubePlayer;
    private final YouTubePlayerView youTubePlayerView;
    private boolean isFullScreen = false;

    CustomPlayerUiController(View controlsUi, YouTubePlayer youTubePlayer, YouTubePlayerView youTubePlayerView) {
        this.youTubePlayer = youTubePlayer;
        this.youTubePlayerView = youTubePlayerView;
        playerTracker = new YouTubePlayerTracker();
        youTubePlayer.addListener(playerTracker);

        initViews(controlsUi);
    }

    private void initViews(View view) {
        View container = view.findViewById(R.id.container);
        RelativeLayout relativeLayout = view.findViewById(R.id.root);
        YouTubePlayerSeekBar seekBar = view.findViewById(R.id.playerSeekbar);
        ImageButton pausePlay = view.findViewById(R.id.pausePlay);
        ImageButton fullScreen = view.findViewById(R.id.toggleFullScreen);
        youTubePlayer.addListener(seekBar);

        seekBar.setYoutubePlayerSeekBarListener(new YouTubePlayerSeekBarListener() {
            @Override
            public void seekTo(float v) {
                youTubePlayer.seekTo(v);
            }
        });

        pausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTracker.getState() == PlayerConstants.PlayerState.PLAYING) {
                    pausePlay.setImageResource(R.drawable.baseline_play_circle_filled_24);
                    youTubePlayer.pause();
                } else {
                    pausePlay.setImageResource(R.drawable.baseline_pause_circle_filled);
                    youTubePlayer.play();
                }
            }
        });

        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) view.getContext();

                if (isFullScreen) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    youTubePlayerView.wrapContent();
                } else {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    youTubePlayerView.matchParent();
                }
                isFullScreen = !isFullScreen;
            }
        });

        FadeViewHelper fadeViewHelper = new FadeViewHelper(container);
        fadeViewHelper.setAnimationDuration(FadeViewHelper.DEFAULT_ANIMATION_DURATION);
        fadeViewHelper.setFadeOutDelay(FadeViewHelper.DEFAULT_FADE_OUT_DELAY);
        youTubePlayer.addListener(fadeViewHelper);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeViewHelper.toggleVisibility();
            }
        });

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeViewHelper.toggleVisibility();
            }
        });
    }
}