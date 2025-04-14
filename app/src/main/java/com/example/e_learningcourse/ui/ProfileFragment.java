package com.example.e_learningcourse.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.FragmentProfileBinding;
import com.example.e_learningcourse.ui.auth.login.LoginActivity;
import com.google.android.material.button.MaterialButton;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private LinearLayout layoutYourProfile;
    private LinearLayout layoutPaymentMethod;
    private LinearLayout layoutLogout;
    private LinearLayout layoutSettings;
    private LinearLayout layoutHelp;
    private LinearLayout layoutPrivacyPolicy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        initViews();
        setupClickListeners();
        
        return binding.getRoot();
    }

    private void initViews() {
        layoutYourProfile = binding.llProfile;
        layoutPaymentMethod = binding.llPaymentMethods;
        layoutLogout = binding.llLogout;
        layoutSettings = binding.llSettings;
        layoutHelp = binding.llHelpCenter;
        layoutPrivacyPolicy = binding.llPrivacyPolicy;
    }

    private void setupClickListeners() {
        // Your Profile
//        layoutYourProfile.setOnClickListener(v -> {
//           Intent intent = new Intent(getActivity(), AccountActivity.class);
//              startActivity(intent);
//        });

        // Payment Method
//        layoutPaymentMethod.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), PaymentMethodActivity.class);
//            startActivity(intent);
//        });

        // Settings
        layoutSettings.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        });

        // Help Center
        layoutHelp.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HelpCenterActivity.class);
            startActivity(intent);
        });

//        // Privacy Policy
//        layoutPrivacyPolicy.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), PrivacyPolicyActivity.class);
//            startActivity(intent);
//        });

        // Logout
        layoutLogout.setOnClickListener(v -> {
            showLogoutConfirmationDialog();
        });
    }

    private void showLogoutConfirmationDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);

        // Set dialog width to match parent with margins
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        MaterialButton btnCancel = dialog.findViewById(R.id.btnCancel);
        MaterialButton btnLogout = dialog.findViewById(R.id.btnLogout);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnLogout.setOnClickListener(v -> {
            dialog.dismiss();
            // TODO: Implement logout logic
            // For example:
            // 1. Clear user session/preferences
            // 2. Navigate to login screen
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
