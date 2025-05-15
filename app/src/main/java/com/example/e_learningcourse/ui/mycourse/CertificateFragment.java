package com.example.e_learningcourse.ui.mycourse;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.FragmentCertificateBinding;
import com.example.e_learningcourse.model.request.ReviewRequest;
import com.example.e_learningcourse.ui.account.UserViewModel;
import com.example.e_learningcourse.ui.review.ReviewViewModel;
import com.google.android.material.button.MaterialButton;
import com.example.e_learningcourse.utils.NotificationUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CertificateFragment extends Fragment {

    private FragmentCertificateBinding binding;
    private ReviewViewModel reviewViewModel;
    private UserViewModel userViewModel;
    private Dialog reviewDialog;
    private Long courseId;
    private int progress;

    public static CertificateFragment newInstance(Long courseId) {
        CertificateFragment fragment = new CertificateFragment();
        Bundle args = new Bundle();
        args.putLong("courseId", courseId);
        args.putInt("progress", fragment.progress);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCertificateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get courseId from arguments
        courseId = getArguments() != null ? getArguments().getLong("courseId") : -1;
        progress = getArguments() != null ? getArguments().getInt("progress") : 0;
        if (courseId == -1) {
            showError("Invalid course ID");
            requireActivity().onBackPressed();
            return;
        }
        if (progress >= 50) {
            binding.btnReview.setVisibility(View.VISIBLE);
        } else {
            binding.btnReview.setVisibility(View.GONE);
        }
        // Initialize ViewModel
        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        if (progress == 100) {
            binding.btnDownload.setVisibility(View.VISIBLE);
        } else {
            binding.btnDownload.setVisibility(View.GONE);
        }
        // Set up click listeners
        binding.btnDownload.setOnClickListener(v -> downloadCertificate());
        binding.btnReview.setOnClickListener(v -> showReviewDialog());

        // Load certificate data
        loadCertificateData(courseId);
    }

    @SuppressLint("SetTextI18n")
    private void loadCertificateData(long courseId) {
        // TODO: Load actual certificate data from your data source
        userViewModel.fetchCertificate(courseId);
        userViewModel.getCertificate().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.isSuccess()) {
                // Update UI with certificate data
                binding.tvStudentName.setText(response.getData().getUserName());
                binding.tvCourseName.setText(response.getData().getCourseName());
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
                String formattedUpdate = sdf.format(response.getData().getCreatedAt());
                binding.tvIssueDate.setText("Issued on " + formattedUpdate);
                binding.tvCertificateId.setText("ID : " + response.getData().getCertificateId());
            } else {
                Toast.makeText(requireContext(), "Failed to load certificate data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadCertificate() {
        // Show loading state
        binding.btnDownload.setEnabled(false);
        binding.btnDownload.setText("Generating...");

        // Create a bitmap of the certificate view
        View certificateView = binding.certificateCard;
        certificateView.setDrawingCacheEnabled(true);
        certificateView.buildDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(
                certificateView.getWidth(),
                certificateView.getHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        certificateView.draw(canvas);

        // Create PDF document
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(
                bitmap.getWidth(),
                bitmap.getHeight(),
                1
        ).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas pdfCanvas = page.getCanvas();
        pdfCanvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // Save to external files directory (app-specific)
        String fileName = "Certificate_" + binding.tvStudentName.getText().toString().replace(" ", "_") + "_" +
                new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date()) + ".pdf";

        File file = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            fos.close();
            document.close();

            // Share the PDF using FileProvider
            Uri uri = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().getApplicationContext().getPackageName() + ".provider",
                    file
            );

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share Certificate"));

            Toast.makeText(requireContext(), "Certificate downloaded successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(requireContext(), "Failed to download certificate", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            // Reset button state
            binding.btnDownload.setEnabled(true);
            binding.btnDownload.setText("Download Certificate");
        }
    }
    private void showReviewDialog() {
        reviewDialog = new Dialog(requireContext());
        reviewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reviewDialog.setContentView(R.layout.dialog_review);

        Window window = reviewDialog.getWindow();
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

        RatingBar ratingBar = reviewDialog.findViewById(R.id.ratingBar);
        EditText etReview = reviewDialog.findViewById(R.id.etReview);
        MaterialButton btnSubmit = reviewDialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String review = etReview.getText().toString().trim();

            if (review.isEmpty()) {
                etReview.setError("Please add your review");
                return;
            }

            // Show loading state
            btnSubmit.setEnabled(false);
            btnSubmit.setText("Submitting...");

            // Create review request
            ReviewRequest reviewRequest = new ReviewRequest();
            reviewRequest.setCourseId(courseId);
            reviewRequest.setReviewScore((int) rating);
            reviewRequest.setReviewContent(review);

            // Submit review
            reviewViewModel.submitReview(reviewRequest).observe(getViewLifecycleOwner(), response -> {
                if (response != null && response.isSuccess()) {
                    Toast.makeText(requireContext(), "Review submitted successfully", Toast.LENGTH_SHORT).show();
                    dismissReviewDialog();
                } else {
                    Toast.makeText(requireContext(), "Failed to submit review", Toast.LENGTH_SHORT).show();
                    btnSubmit.setEnabled(true);
                    btnSubmit.setText("Submit");
                }
            });
        });

        reviewDialog.show();
    }

    private void dismissReviewDialog() {
        if (reviewDialog != null && reviewDialog.isShowing()) {
            Window window = reviewDialog.getWindow();
            if (window != null) {
                Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
                slideDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Log.d("ReviewDialog", "Animation ended, dismissing dialog.");
                        reviewDialog.dismiss();
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });

                window.getDecorView().startAnimation(slideDown);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    if (reviewDialog != null && reviewDialog.isShowing()) {
                        Log.d("ReviewDialog", "Fallback dismiss after delay");
                        reviewDialog.dismiss();
                    }
                }, 300); // 250ms duration + buffer
            } else {
                reviewDialog.dismiss();
            }
        }
    }

    private void showError(String message) {
        NotificationUtils.showError(requireContext(), binding.getRoot(), message);
    }

    private void showSuccess(String message) {
        NotificationUtils.showSuccess(requireContext(), binding.getRoot(), message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clear binding reference
    }
}
