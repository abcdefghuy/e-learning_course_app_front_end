package com.example.e_learningcourse.ui;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MessageFragment extends Fragment {
    public static class FaqItemAnimator {
        private final View containerView;
        private final ImageView arrowView;
        private final TextView answerView;
        private boolean isExpanded;
        private ObjectAnimator arrowAnimator;

        public FaqItemAnimator(View containerView, ImageView arrowView, TextView answerView) {
            this.containerView = containerView;
            this.arrowView = arrowView;
            this.answerView = answerView;
            this.isExpanded = false;

            containerView.setOnClickListener(v -> toggleExpansion());
        }

        private void toggleExpansion() {
            isExpanded = !isExpanded;

            // Cancel any running animation
            if (arrowAnimator != null) {
                arrowAnimator.cancel();
            }

            // Animate the arrow
            arrowAnimator = ObjectAnimator.ofFloat(
                    arrowView,
                    View.ROTATION,
                    isExpanded ? 180f : 0f
            );
            arrowAnimator.setDuration(300);
            arrowAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            arrowAnimator.start();

            // Show/hide the answer
            answerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        }
    }
}
