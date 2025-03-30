package com.example.e_learningcourse.ui;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.adapter.PopularCoursesAdapter;
import com.example.e_learningcourse.model.Course;

import java.util.ArrayList;
import java.util.List;

public class PopularCoursesActivity extends AppCompatActivity implements PopularCoursesAdapter.OnBookmarkClickListener {
    private PopularCoursesAdapter adapter;
    private List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_courses);

        // Set up back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize course list
        initializeCourses();

        // Set up adapter
        adapter = new PopularCoursesAdapter(courses);
        adapter.setOnBookmarkClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initializeCourses() {
        courses = new ArrayList<>();
        courses.add(new Course("Introduction of Figma", "Jacob Jones", 180.00, R.drawable.ic_business, true));
        courses.add(new Course("Logo Design Basics", "Eleanor Pena", 120.00, R.drawable.ic_business, true));
        courses.add(new Course("Introduction of Figma", "Kathryn Murphy", 160.00, R.drawable.ic_business, true));
        courses.add(new Course("User-Centered Design", "Marvin McKinney", 200.00, R.drawable.ic_business, true));
        courses.add(new Course("Introduction of Figma", "Jacob Jones", 180.00, R.drawable.ic_business, true));
    }
    @Override
    public void onBookmarkClick(Course course, int position) {
        // Toggle bookmark state
        boolean newBookmarkState = !course.isBookmarked();
        course.setBookmarked(newBookmarkState);
        
        // Show feedback to user
        String message = newBookmarkState ? 
            getString(R.string.bookmark_added) : 
            getString(R.string.bookmark_removed);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        
        // Update the UI
        adapter.notifyItemChanged(position);

        // Here you could also save the bookmark state to persistent storage
        // saveBookmarkState(course);
    }

    // Optional: Add method to save bookmark state to persistent storage
    /*
    private void saveBookmarkState(Course course) {
        // Save to SharedPreferences or Database
    }
    */
}