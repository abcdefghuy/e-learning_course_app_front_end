package com.example.e_learningcourse;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.databinding.ActivityMainBinding;
import com.example.e_learningcourse.ui.HomeFragment;
import com.example.e_learningcourse.ui.MessageFragment;
import com.example.e_learningcourse.ui.account.ProfileFragment;
import com.example.e_learningcourse.ui.mycourse.MyCourseFragment;
import com.example.e_learningcourse.ui.search.SearchActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                replaceFragment(new ProfileFragment());
            }
            return true;
        });

        String targetTab = getIntent().getStringExtra("targetTab");
        if ("courses".equals(targetTab)) {
            binding.bottomNavigationView.setSelectedItemId(R.id.courses);
        } else {
            binding.bottomNavigationView.setSelectedItemId(R.id.home);
        }
    }


    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}