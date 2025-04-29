package com.example.e_learningcourse.ui.common;

import android.annotation.SuppressLint;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.e_learningcourse.R;

public class PasswordToggleHelper {

    @SuppressLint("ClickableViewAccessibility")
    public static void setupPasswordToggle(EditText editText) {
        final boolean[] isPasswordVisible = {false};

        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int touchX = (int) event.getX();
                int width = editText.getWidth();
                int paddingEnd = editText.getPaddingEnd();
                int drawableWidth = editText.getCompoundDrawablesRelative()[2].getBounds().width();

                if (touchX >= width - paddingEnd - drawableWidth) {
                    isPasswordVisible[0] = !isPasswordVisible[0];

                    editText.setInputType(isPasswordVisible[0]
                            ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                            : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    editText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            R.drawable.ic_lock, 0,
                            isPasswordVisible[0] ? R.drawable.ic_hide : R.drawable.ic_show,
                            0
                    );

                    editText.setSelection(editText.getText().length());
                    return true;
                }
            }
            return false;
        });
    }
}
