package com.example.e_learningcourse.ui.account;

import static androidx.media3.exoplayer.mediacodec.MediaCodecInfo.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.e_learningcourse.R;
import com.example.e_learningcourse.data.local.TokenManager;
import com.example.e_learningcourse.databinding.FragmentProfileBinding;
import com.example.e_learningcourse.ui.HomeFragment;
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
    private Dialog logoutDialog;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        loadData();
        initViews();
        setupClickListeners();
        animateProfileItems();
        return binding.getRoot();
    }
    private void loadData() {
        userViewModel.getUserInfo().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.isSuccess()) {
                String userName = response.getData().getFullName();
                binding.tvName.setText(userName);
                Glide.with(requireContext())
                        .load(response.getData().getAvatarUrl())
                        .placeholder(R.drawable.avatar)
                        .into(binding.ivProfile);
            } else {
                Log.e("asdsad", "Failed to load user data: " + response.getMessage());
            }
        });
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
        layoutYourProfile.setOnClickListener(v -> {
            // Replace the current fragment with AccountFragment
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AccountFragment())
                .addToBackStack(null)
                .commit();
        });

        // Payment Method
        layoutPaymentMethod.setOnClickListener(v -> {
            animateClick(v);
            // Intent intent = new Intent(getActivity(), PaymentMethodActivity.class);
            // startActivity(intent);
        });

        // Settings
        layoutSettings.setOnClickListener(v -> {
            animateClick(v);
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        });

        // Help Center
        layoutHelp.setOnClickListener(v -> {
            animateClick(v);
            Intent intent = new Intent(getActivity(), HelpCenterActivity.class);
            startActivity(intent);
        });

        // Privacy Policy
        layoutPrivacyPolicy.setOnClickListener(v -> {
            animateClick(v);
            Intent intent = new Intent(getActivity(), PrivacyPolicyActivity.class);
            startActivity(intent);
        });

        // Logout
        layoutLogout.setOnClickListener(v -> {
            animateClick(v);
            showLogoutConfirmationDialog();
        });
    }

    private void animateProfileItems() {
        View[] items = {
            layoutYourProfile, layoutPaymentMethod, layoutSettings,
            layoutHelp, layoutPrivacyPolicy, layoutLogout
        };

        for (int i = 0; i < items.length; i++) {
            View item = items[i];
            item.setAlpha(0f);
            item.setTranslationY(50f);
            item.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(300)
                .setStartDelay(i * 100L)
                .setInterpolator(new FastOutSlowInInterpolator())
                .start();
        }
    }

    private void animateClick(View view) {
        view.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(100)
            .withEndAction(() -> {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .start();
            })
            .start();
    }

    private void showLogoutConfirmationDialog() {
        logoutDialog = new Dialog(requireContext());
        logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logoutDialog.setContentView(R.layout.dialog_logout);

        Window window = logoutDialog.getWindow();
        if (window != null) {
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            
            // Add slide up animation
            Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
            window.getDecorView().startAnimation(slideUp);
        }

        MaterialButton btnCancel = logoutDialog.findViewById(R.id.btnCancel);
        AppCompatButton btnLogout = logoutDialog.findViewById(R.id.btnLogout);

        btnCancel.setOnClickListener(v -> {
            animateClick(v);
            dismissLogoutDialog();
        });

        btnLogout.setOnClickListener(v -> {
            animateClick(v);
            performLogout();
        });

        logoutDialog.show();
    }

    private void dismissLogoutDialog() {
        if (logoutDialog != null && logoutDialog.isShowing()) {
            Window window = logoutDialog.getWindow();
            if (window != null) {
                Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
                slideDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        logoutDialog.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                window.getDecorView().startAnimation(slideDown);
            } else {
                logoutDialog.dismiss();
            }
        }
    }

    private void performLogout() {
        // Show loading state
        AppCompatButton btnLogout = logoutDialog.findViewById(R.id.btnLogout);
        btnLogout.setEnabled(false);
        btnLogout.setText("Logging out...");

        // Simulate network delay
        btnLogout.postDelayed(() -> {
            // Clear user data
            TokenManager.getInstance(requireContext()).clearToken();

            // Show success message
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

            // Navigate to login
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            
            // Optional: Add activity transition animation
            if (getActivity() != null) {
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
