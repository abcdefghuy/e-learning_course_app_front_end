package com.example.e_learningcourse.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar();
        setupRecyclerView();
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

    private void loadCategories() {
        List<Category> categories = new ArrayList<>();
        
        // Add all categories with their icons
        categories.add(new Category(1,"Art", R.drawable.ic_art));
        categories.add(new Category(1,"Coding", R.drawable.ic_coding));
        categories.add(new Category(1,"Marketing", R.drawable.ic_marketing));
        categories.add(new Category(1,"Business", R.drawable.ic_business));
        categories.add(new Category(1,"Accounting", R.drawable.ic_accounting));
//        categories.add(new Category(1,"Science", R.drawable.ic_science));
//        categories.add(new Category(1,"Maths", R.drawable.ic_maths));
//        categories.add(new Category(1,"English", R.drawable.ic_english));
        categories.add(new Category(1,"Photography", R.drawable.ic_photography));
//        categories.add(new Category(1,"Finance", R.drawable.ic_finance));
//        categories.add(new Category(1,"Writing", R.drawable.ic_writing));
//        categories.add(new Category(1,"AI/ML", R.drawable.ic_ai_ml));
        categories.add(new Category(1,"Health", R.drawable.ic_health));
        categories.add(new Category(1,"Music", R.drawable.ic_music));
//        categories.add(new Category(1,"Sales", R.drawable.ic_sales));
//        categories.add(new Category(1,"Fashion", R.drawable.ic_fashion));
//        categories.add(new Category(1,"VR", R.drawable.ic_vr));
//        categories.add(new Category(1,"HRM", R.drawable.ic_hrm));
//        categories.add(new Category(1,"Agriculture", R.drawable.ic_agriculture));
//        categories.add(new Category(1,"Architecture", R.drawable.ic_architecture));

        adapter.setCategories(categories);
    }
} 