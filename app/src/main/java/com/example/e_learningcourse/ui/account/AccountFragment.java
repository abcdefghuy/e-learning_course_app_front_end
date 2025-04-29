package com.example.e_learningcourse.ui.account;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.FragmentAccountBinding;
import com.example.e_learningcourse.model.request.UserRequest;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;
    private Calendar calendar;
    private UserViewModel userViewModel;
    private static final int MY_REQUEST_CODE = 100;
    private Uri mUri;
    private String mImageUrl;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        calendar = Calendar.getInstance();
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        setupViews();
        setupClickListeners();
        setupGenderDropdown();
        initCloudinaryConfig();
        animateContent();
        return binding.getRoot();
    }

    private void initCloudinaryConfig() {
        try {
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", "dnoyhgqbs");
            config.put("api_key", "487394153664677");
            config.put("api_secret", "qV8HWNChkFSIGAMwUMqkjY0lZhQ");
            MediaManager.init(requireContext(), config);
        } catch (Exception e) {
            Log.d("AccountFragment", "Cloudinary already initialized");
        }
    }

    private void setupViews() {
        userViewModel.getUserInfo().observe(getViewLifecycleOwner(), userDetail -> {
            if (userDetail.getData() != null) {
                binding.tvName.setText(userDetail.getData().getFullName());
                binding.tvEmail.setText(userDetail.getData().getEmail());
                binding.etFullName.setText(userDetail.getData().getFullName());
                binding.etEmail.setText(userDetail.getData().getEmail());
                binding.etPhone.setText(userDetail.getData().getPhoneNumber());
                binding.spinnerGender.setText(userDetail.getData().getGender(), false);
                Glide.with(requireContext())
                        .load(userDetail.getData().getAvatarUrl())
                        .placeholder(R.drawable.profile_placeholder)
                        .error(R.drawable.profile_placeholder)
                        .into(binding.imgProfile);
                mImageUrl = userDetail.getData().getAvatarUrl();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                String formattedUpdate = sdf.format(userDetail.getData().getDateOfBirth());
                binding.etDob.setText(formattedUpdate);
            }
        });
    }

    private void setupClickListeners() {
        binding.btnEditPhoto.setOnClickListener(v -> {
            animateClick(v);
            checkPermission();
        });

        binding.etDob.setOnClickListener(v -> {
            animateClick(v);
            showDatePickerDialog();
        });

        binding.btnSave.setOnClickListener(v -> {
            animateClick(v);
            saveChanges();
        });
    }

    private void setupGenderDropdown() {
        String[] genders = new String[]{"Male", "Female", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.item_dropdown, genders);
        binding.spinnerGender.setAdapter(adapter);
        binding.spinnerGender.setText("Male", false);
    }

    private void animateContent() {
        View[] views = {
                binding.imgProfile, binding.etFullName, binding.etEmail, binding.etPhone,
                binding.etDob, binding.spinnerGender, binding.btnSave
        };

        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            view.setAlpha(0f);
            view.setTranslationY(50f);
            view.animate().alpha(1f).translationY(0f).setDuration(300).setStartDelay(i * 100L).start();
        }
    }

    private void animateClick(View view) {
        view.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction(() -> {
            view.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
        }).start();
    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    binding.etDob.setText(selectedDate);
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }
    private void saveChanges() {
        if (!validateInputs()) return;

        binding.btnSave.setEnabled(false);
        binding.btnSave.setText("Saving...");

        if (mUri != null) {
            // Nếu có ảnh mới -> upload ảnh lên Cloudinary
            MediaManager.get().upload(mUri).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    String imageUrl = (String) resultData.get("secure_url");
                    Glide.with(requireContext()).load(imageUrl).into(binding.imgProfile);

                    // Khi upload ảnh xong -> cập nhật hồ sơ
                    updateUserProfile(imageUrl);
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    binding.btnSave.setEnabled(true);
                    binding.btnSave.setText("Save Changes");
                    Toast.makeText(requireContext(), "Upload image failed: " + error.getDescription(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                }
            }).dispatch();
        } else {
            // Không đổi ảnh -> cập nhật trực tiếp
            updateUserProfile(mImageUrl);
        }
    }


    private boolean validateInputs() {
        boolean isValid = true;

        String name = binding.etFullName.getText().toString().trim();
        if (name.isEmpty()) {
            binding.etFullName.setError("Name is required");
            isValid = false;
        }

        String email = binding.etEmail.getText().toString().trim();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Valid email is required");
            isValid = false;
        }

        String phone = binding.etPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            binding.etPhone.setError("Phone number is required");
            isValid = false;
        }
        return isValid;
    }

    private void updateUserProfile(String imageUrl) {
        UserRequest userDetail = new UserRequest();
        userDetail.setFullName(binding.etFullName.getText().toString());
        userDetail.setEmail(binding.etEmail.getText().toString());
        userDetail.setPhone(binding.etPhone.getText().toString());
        userDetail.setGender(binding.spinnerGender.getText().toString());
        userDetail.setAvatarUrl(imageUrl);

        String dobText = Objects.requireNonNull(binding.etDob.getText()).toString().trim();
        userDetail.setBirthday(dobText);
        userViewModel.updateUserInfo(userDetail);
        // Ở đây, ta sẽ quan sát loading và error nếu muốn
        observeViewModel();
    }

    private void observeViewModel() {
        userViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.btnSave.setEnabled(!isLoading);
            binding.btnSave.setText(isLoading ? "Saving..." : "Save Changes");
        });

        userViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                // Cập nhật lại thông tin người dùng trong ViewModel
                setupViews();
            }
        });
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                permissionRequestLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else { // Android 12 trở xuống
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                permissionRequestLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Image"));
    }

    private final ActivityResultLauncher<String> permissionRequestLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    openGallery();
                } else {
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    assert result.getData() != null;
                    Uri uri = result.getData().getData();
                    mUri = uri;
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                        binding.imgProfile.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            MediaManager.get().cancelAllRequests();
        } catch (Exception e) {
            Log.e("AccountFragment", "Error cleaning up MediaManager: " + e.getMessage());
        }
        binding = null;
    }
}
