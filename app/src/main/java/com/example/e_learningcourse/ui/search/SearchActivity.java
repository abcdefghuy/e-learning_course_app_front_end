package com.example.e_learningcourse.ui.search;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity implements SearchRecentFragment.OnRecentSearchInteractionListener {
    private ActivitySearchBinding binding;
    private SearchRecentFragment recentFragment;
    private SearchResultsFragment resultsFragment;
    private boolean isResultsFragmentShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");
        setupFragments();
        setupViews();
        if (categoryName != null && !categoryName.isEmpty()) {
            searchByCategory(categoryName);
        }
    }

    private void searchByCategory(String categoryName) {
        // Chuyển sang fragment trước, sau đó dùng post để chờ fragment attach xong rồi gọi updateSearch
        isResultsFragmentShown = true;
        showFragment(resultsFragment);
        binding.fragmentContainer.post(() -> resultsFragment.updateSearchCategory(categoryName));
    }

    private void setupFragments() {
        recentFragment = new SearchRecentFragment();
        resultsFragment = new SearchResultsFragment();
        showFragment(recentFragment);
    }

    private void setupViews() {
        binding.btnBack.setOnClickListener(v -> finish());

        // Nhấn Enter để thực hiện tìm kiếm
        binding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                String query = binding.etSearch.getText().toString().trim();
                if (!query.isEmpty()) {
                    if (!isResultsFragmentShown) {
                        isResultsFragmentShown = true;
                        // Chuyển sang fragment trước, sau đó dùng post để chờ fragment attach xong rồi gọi updateSearch
                        showFragment(resultsFragment);
                        binding.fragmentContainer.post(() -> resultsFragment.updateSearch(query));
                    } else {
                        resultsFragment.updateSearch(query);
                    }
                    hideKeyboard(); // Ẩn bàn phím sau khi nhấn search
                }
                return true;
            }
            return false;
        });

        binding.btnClear.setOnClickListener(v -> {
            binding.etSearch.setText("");
            isResultsFragmentShown = false;
            showFragment(recentFragment);
        });
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    @Override
    public void onRecentKeywordClicked(String keyword) {
        if (!isResultsFragmentShown) {
            isResultsFragmentShown = true;
            showFragment(resultsFragment);
            binding.fragmentContainer.post(() -> resultsFragment.updateSearch(keyword));
        } else {
            resultsFragment.updateSearch(keyword);
        }
        hideKeyboard();
    }
}
