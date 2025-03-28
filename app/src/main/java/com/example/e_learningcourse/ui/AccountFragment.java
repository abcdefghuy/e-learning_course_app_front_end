package com.example.e_learningcourse.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.databinding.FragmentAccountBinding;

import java.util.Calendar;

public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;
    private Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(getLayoutInflater());
        calendar = Calendar.getInstance();
        binding.etDob.setFocusable(false);
        binding.etDob.setOnClickListener(view -> showDatePickerDialog());

        return binding.getRoot();
    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, selectedYear, selectedMonth, selectedDay) -> {
            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            binding.etDob.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }
}
