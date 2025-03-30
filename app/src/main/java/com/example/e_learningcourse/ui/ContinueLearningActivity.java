package com.example.e_learningcourse.ui;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.ContinueLearningAdapter;
import com.example.e_learningcourse.model.Course;

import java.util.ArrayList;
import java.util.List;

public class ContinueLearningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_learning);

        // Set up back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create sample courses with progress
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "Introduction of Figma", "Jacob Jones", R.drawable.avatar,
                R.drawable.ic_business, 4.5f, 0, 20));
        courses.add(new Course(2, "Introduction of Figma", "Eleanor Pena", R.drawable.avatar,
                R.drawable.ic_business, 4.5f, 0, 20));
        courses.add(new Course(3, "Figma Design Tool", "Kathryn Murphy", R.drawable.avatar,
                R.drawable.ic_business, 4.5f, 0, 20));
        courses.add(new Course(4, "User-Centered Design", "Marvin McKinney", R.drawable.avatar,
                R.drawable.ic_business, 4.5f, 0, 20));
        courses.add(new Course(5, "Introduction of Figma", "Jacob Jones", R.drawable.avatar,
                R.drawable.ic_business, 4.5f, 0, 20));

        // Set up adapter
        ContinueLearningAdapter adapter = new ContinueLearningAdapter(courses);
        recyclerView.setAdapter(adapter);
    }
}