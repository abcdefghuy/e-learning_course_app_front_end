package com.example.e_learningcourse.ui.category;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.CategoryAdapter;
import com.example.e_learningcourse.databinding.ActivityCategoryBinding;
import com.example.e_learningcourse.model.Category;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private ActivityCategoryBinding binding;
    private CategoryAdapter adapter;
    private CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupToolbar();
        setupRecyclerView();
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        loadCategories();
    }

    private void setupToolbar() {
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        adapter = new CategoryAdapter();
        // Create a GridLayoutManager with 4 columns
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        binding.rvCategories.setLayoutManager(layoutManager);
        binding.rvCategories.setAdapter(adapter);
        adapter.setOnCategoryClickListener(category -> {
            // Handle category click
            // For example, start a new activity with the selected category
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadCategories() {
        categoryViewModel.fetchCategories();
        categoryViewModel.getCategories().observe(this, categories -> {
            adapter.setCategories(categories);
            adapter.notifyDataSetChanged();
        });
    }
} 