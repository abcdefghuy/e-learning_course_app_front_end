package com.example.e_learningcourse.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.e_learningcourse.adapter.NumpadAdapter;
import com.example.e_learningcourse.databinding.FragmentNumpadBinding;
import com.example.e_learningcourse.enums.KeyType;
import com.example.e_learningcourse.model.NumpadKey;

import java.util.Arrays;
import java.util.List;

public class NumpadFragment extends Fragment {
    private FragmentNumpadBinding binding;
    private OtpInputListener otpInputListener;

    public interface OtpInputListener {
        List<EditText> getOtpFields();
    }

    private final List<NumpadKey> numpadKeys = Arrays.asList(
            new NumpadKey("1", 1, KeyType.NUMBER),
            new NumpadKey("2", 2, KeyType.NUMBER),
            new NumpadKey("3", 3, KeyType.NUMBER),
            new NumpadKey("4", 4, KeyType.NUMBER),
            new NumpadKey("5", 5, KeyType.NUMBER),
            new NumpadKey("6", 6, KeyType.NUMBER),
            new NumpadKey("7", 7, KeyType.NUMBER),
            new NumpadKey("8", 8, KeyType.NUMBER),
            new NumpadKey("9", 9, KeyType.NUMBER),
            new NumpadKey("", -1, KeyType.EMPTY),
            new NumpadKey("0", 0, KeyType.NUMBER),
            new NumpadKey("⌫", -2, KeyType.ACTION)
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNumpadBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            otpInputListener = (OtpInputListener) requireActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity phải implement OtpInputListener");
        }

        binding.recyclerNumpad.setLayoutManager(new GridLayoutManager(getContext(), 3));
        NumpadAdapter adapter = new NumpadAdapter(numpadKeys, this::onKeyPress);
        binding.recyclerNumpad.setAdapter(adapter);
    }

    private void onKeyPress(NumpadKey key) {
        List<EditText> otpFields = otpInputListener.getOtpFields();

        if (key.getType() == KeyType.NUMBER) {
            for (EditText editText : otpFields) {
                if (editText.getText().toString().isEmpty()) {
                    editText.setText(key.getLabel());
                    editText.requestFocus();
                    break;
                }
            }
        } else if (key.getType() == KeyType.ACTION) {
            for (int i = otpFields.size() - 1; i >= 0; i--) {
                if (!otpFields.get(i).getText().toString().isEmpty()) {
                    otpFields.get(i).setText("");
                    otpFields.get(i).requestFocus();
                    break;
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}