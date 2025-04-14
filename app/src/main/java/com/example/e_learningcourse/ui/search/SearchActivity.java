package com.example.e_learningcourse.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private SearchRecentFragment recentFragment;
    private SearchResultsFragment resultsFragment;
    private boolean isResultsFragmentShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Set window soft input mode to pan
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        
        setupFragments();
        setupViews();
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void setupFragments() {
        recentFragment = new SearchRecentFragment();
        resultsFragment = new SearchResultsFragment();
        // Show recent fragment by default
        showFragment(recentFragment);
    }

    private void setupViews() {
        // Handle back button click
        binding.btnBack.setOnClickListener(v -> finish());
        // Handle search text changes
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                if (!query.isEmpty()) {
                    if (!isResultsFragmentShown) {
                        isResultsFragmentShown = true;
                        showFragment(resultsFragment);
                    }
                    resultsFragment.updateSearch(query);
                } else {
                    isResultsFragmentShown = false;
                    showFragment(recentFragment);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        // Handle clear button click
        binding.btnClear.setOnClickListener(v -> binding.etSearch.setText(""));
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
