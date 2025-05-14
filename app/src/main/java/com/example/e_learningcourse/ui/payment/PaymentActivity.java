package com.example.e_learningcourse.ui.payment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.e_learningcourse.MainActivity;
import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ActivityPaymentBinding;
import com.example.e_learningcourse.ui.ResultScreenActivity;
import com.example.e_learningcourse.ui.base.BaseActivity;
import com.example.e_learningcourse.ui.course.CourseDetailsActivity;
import com.example.e_learningcourse.utils.NotificationUtils;

public class PaymentActivity extends BaseActivity<ActivityPaymentBinding, PaymentViewModel> {

    @Override
    protected ActivityPaymentBinding getViewBinding() {
        return ActivityPaymentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected PaymentViewModel getViewModel() {
        return new ViewModelProvider(this).get(PaymentViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setViewModel(viewModel);

        // Xử lý callback nếu quay lại từ VNPay (deep link)
        Uri uri = getIntent().getData();
        if (uri != null) {
            handlePaymentCallback(uri);
            return;
        }

        // Nhận dữ liệu khóa học
        long userId = getIntent().getLongExtra("userId", -1);
        long courseId = getIntent().getLongExtra("courseId", -1);
        double amount = getIntent().getDoubleExtra("amount", 0.0);
        String title = getIntent().getStringExtra("title");
        String category = getIntent().getStringExtra("category");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        if (userId == -1 || courseId == -1) {
            Toast.makeText(this, "Invalid data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Hiển thị thông tin khóa học
        binding.tvCourseTitle.setText(title);
        binding.tvCourseCategory.setText(category);
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.bg_grey_16)
                .into(binding.ivCourseImage);

        viewModel.userId.setValue(userId);
        viewModel.courseId.setValue(courseId);
        viewModel.amount.setValue(amount);
        viewModel.provider.setValue("VNPAY");

        // Bắt đầu thanh toán
        binding.btnEnroll.setOnClickListener(v -> viewModel.createPayment());

        viewModel.getPaymentResponse().observe(this, response -> {
            if (response.isSuccess()) {
                openVNPay(response.getData());
            } else {
                showError(response.getMessage());
                finish();
            }
        });

        binding.backButton.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); // Quan trọng!
        handlePaymentCallback(intent.getData());
    }

    private void openVNPay(String url) {
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .setShowTitle(true)
                .build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    private void handlePaymentCallback(Uri uri) {
        if (uri == null || !"myapp".equals(uri.getScheme())) return;

        String status = uri.getQueryParameter("status");
        Log.d("PaymentCallback", "status=" + status);

        Intent intent = new Intent(this, ResultScreenActivity.class);

        if ("success".equals(status)) {
            intent.putExtra("title", "Successful purchase!");
            intent.putExtra("icon", R.drawable.ic_check);
            intent.putExtra("buttonText", "Continue");
            intent.putExtra("targetActivity", CourseDetailsActivity.class.getName());
            showSuccess("Payment successful!");
        } else {
            intent.putExtra("title", "Payment failed!");
            intent.putExtra("icon", R.drawable.ic_error);
            intent.putExtra("buttonText", "Try again");
            intent.putExtra("targetActivity", MainActivity.class.getName());
            showError("Payment failed. Please try again later.");
        }

        startActivity(intent);
        finish();
    }

    private void showError(String message) {
        NotificationUtils.showError(this, binding.getRoot(), message);
    }

    private void showSuccess(String message) {
        NotificationUtils.showSuccess(this, binding.getRoot(), message);
    }
}
