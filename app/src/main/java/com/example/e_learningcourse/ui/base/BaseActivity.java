package com.example.e_learningcourse.ui.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

public abstract class BaseActivity<B extends ViewDataBinding, VM extends ViewModel>
        extends AppCompatActivity {

    protected B binding;
    protected VM viewModel;

    protected abstract B getViewBinding();
    protected abstract VM getViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = getViewBinding();
        viewModel = getViewModel();

        setContentView(binding.getRoot());

        if (viewModel instanceof BaseViewModel) {
            BaseViewModel baseVM = (BaseViewModel) viewModel;

            baseVM.getIsLoading().observe(this, isLoading -> {
                // Tuỳ: show/hide progress bar
            });

            baseVM.getErrorMessage().observe(this, message -> {
                if (message != null && !message.isEmpty()) {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }

        binding.setLifecycleOwner(this);
    }
}
