package com.example.e_learningcourse;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.databinding.ActivityMainBinding;
import com.example.e_learningcourse.ui.HomeFragment;
import com.example.e_learningcourse.ui.MessageFragment;
import com.example.e_learningcourse.ui.MyCourseFragment;
import com.example.e_learningcourse.ui.search.SearchActivity;
import com.example.e_learningcourse.ui.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.getMenu().findItem(R.id.fab).setEnabled(true);
        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (id == R.id.courses) {
                replaceFragment(new MyCourseFragment());
            } else if (id == R.id.message) {
                replaceFragment(new MessageFragment());
            } else if (id == R.id.account) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}