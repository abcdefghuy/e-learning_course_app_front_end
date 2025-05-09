package com.example.e_learningcourse.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.e_learningcourse.R;
import com.google.android.material.snackbar.Snackbar;

public class NotificationUtils {
    public static void showSuccess(Context context, View rootView, String message) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.success));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.show();
    }

    public static void showError(Context context, View rootView, String message) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.error));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.show();
    }

    public static void showInfo(Context context, View rootView, String message) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.info));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.show();
    }

    public static void showWarning(Context context, View rootView, String message) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.warning));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.show();
    }
} 