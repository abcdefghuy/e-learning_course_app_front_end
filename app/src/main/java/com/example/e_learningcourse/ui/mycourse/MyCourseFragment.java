package com.example.e_learningcourse.ui.mycourse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.FragmentMycourseBinding;
import com.example.e_learningcourse.ui.mycourse.ContinueCourseFragment;
import com.example.e_learningcourse.ui.lesson.LessonsFragment;
import com.google.android.material.tabs.TabLayout;

public class MyCourseFragment extends Fragment {
    private FragmentMycourseBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMycourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTabLayout();
        // Show ContinueCourseFragment by default
        showFragment(new ContinueCourseFragment());
    }

    private void setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 1: // Completed tab
                        fragment = new CompleteCourseFragment(); // You can create a CompletedCoursesFragment later
                        break;
                    default:
                        fragment = new ContinueCourseFragment();
                        break;
                }
                showFragment(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void showFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
