package com.example.e_learningcourse.ui.account;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

public class FaqItemAnimator {
    private final View faqView;
    private final ImageView arrow;
    private final TextView answer;
    private boolean isExpanded = false;

    public FaqItemAnimator(View faqView, ImageView arrow, TextView answer) {
        this.faqView = faqView;
        this.arrow = arrow;
        this.answer = answer;

        setupClickListener();
    }

    private void setupClickListener() {
        faqView.setOnClickListener(v -> toggleExpansion());
    }

    private void toggleExpansion() {
        if (isExpanded) {
            collapse();
        } else {
            expand();
        }
        isExpanded = !isExpanded;
    }

    private void expand() {
        answer.setVisibility(View.VISIBLE);
        arrow.animate().rotation(180).setDuration(200).start();

        final int initialHeight = answer.getHeight();
        final int targetHeight = answer.getLineCount() * answer.getLineHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                answer.getLayoutParams().height = initialHeight + (int) ((targetHeight - initialHeight) * interpolatedTime);
                answer.requestLayout();
            }
        };

        animation.setDuration(200);
        answer.startAnimation(animation);
    }

    private void collapse() {
        arrow.animate().rotation(0).setDuration(200).start();

        final int initialHeight = answer.getHeight();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    answer.setVisibility(View.GONE);
                } else {
                    answer.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    answer.requestLayout();
                }
            }
        };

        animation.setDuration(200);
        answer.startAnimation(animation);
    }
} 