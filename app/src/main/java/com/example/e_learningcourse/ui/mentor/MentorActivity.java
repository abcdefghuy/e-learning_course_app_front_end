package com.example.e_learningcourse.ui.mentor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.e_learningcourse.adapter.CategoryAdapter;
import com.example.e_learningcourse.adapter.MentorAdapter;
import com.example.e_learningcourse.databinding.ActivityCategoryBinding;
import com.example.e_learningcourse.databinding.ActivityMentorBinding;
import com.example.e_learningcourse.ui.category.CategoryActivity;
import com.example.e_learningcourse.ui.category.CategoryViewModel;
import com.example.e_learningcourse.ui.search.SearchActivity;

public class MentorActivity extends AppCompatActivity {
    private ActivityMentorBinding binding;
    private MentorAdapter adapter;
    private MentorViewModel mentorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMentorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupToolbar();
        setupRecyclerView();
        mentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
        loadCategories();
    }

    private void setupToolbar() {
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        adapter = new MentorAdapter();
        // Create a GridLayoutManager with 4 columns
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        binding.rvMentors.setLayoutManager(layoutManager);
        binding.rvMentors.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadCategories() {
       mentorViewModel.fetchMentors();
        mentorViewModel.getMentors().observe(this, mentors -> {
            adapter.setMentors(mentors.getContent());
            adapter.notifyDataSetChanged();
        });
    }
}
