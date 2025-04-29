package com.example.e_learningcourse.ui.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_learningcourse.R;
import com.example.e_learningcourse.databinding.ActivityHelpCenterBinding;
import com.google.android.material.chip.Chip;
import java.util.ArrayList;
import java.util.List;

public class HelpCenterActivity extends AppCompatActivity {
    private ActivityHelpCenterBinding binding;
    private List<FaqItem> faqItems;
    private String currentCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelpCenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupFaqItems();
        setupSearch();
        setupCategoryChips();
        setupBackButton();
    }

    private void setupFaqItems() {
        // Initialize FAQ items
        faqItems = new ArrayList<>();
        faqItems.add(new FaqItem(
            "Can I access my courses offline?",
            "Yes, you can download course materials and access them offline. This is great for learning on the go, especially when you don't have an internet connection.",
            "General"
        ));
        faqItems.add(new FaqItem(
            "How do I enroll in a course?",
            "To enroll in a course, simply browse our course catalog, select the course you're interested in, and click the 'Enroll' button. You'll need to complete the payment process if the course isn't free.",
            "Services"
        ));
        faqItems.add(new FaqItem(
            "Can I get a refund for a course?",
            "Yes, we offer a 30-day money-back guarantee for all paid courses. If you're not satisfied with the course content, you can request a refund through your account settings.",
            "Account"
        ));
        faqItems.add(new FaqItem(
            "How do I track my learning progress?",
            "You can track your progress through the course dashboard, which shows completed lessons, quiz scores, and overall course completion percentage.",
            "General"
        ));
        faqItems.add(new FaqItem(
            "What payment methods do you accept?",
            "We accept all major credit cards, PayPal, and various local payment methods depending on your region.",
            "Account"
        ));

        displayFaqItems();
    }

    private void displayFaqItems() {
        binding.faqContainer.removeAllViews();
        
        for (FaqItem item : faqItems) {
            if (currentCategory.equals("All") || item.getCategory().equals(currentCategory)) {
                View faqView = getLayoutInflater().inflate(R.layout.item_faq, binding.faqContainer, false);
                
                TextView question = faqView.findViewById(R.id.tvQuestion);
                TextView answer = faqView.findViewById(R.id.tvAnswer);
                ImageView arrow = faqView.findViewById(R.id.imgArrow);
                
                question.setText(item.getQuestion());
                answer.setText(item.getAnswer());
                
                new FaqItemAnimator(faqView, arrow, answer);
                binding.faqContainer.addView(faqView);
            }
        }
    }

    private void setupSearch() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFaqItems(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterFaqItems(String query) {
        binding.faqContainer.removeAllViews();
        
        for (FaqItem item : faqItems) {
            if ((currentCategory.equals("All") || item.getCategory().equals(currentCategory)) &&
                (item.getQuestion().toLowerCase().contains(query.toLowerCase()) ||
                 item.getAnswer().toLowerCase().contains(query.toLowerCase()))) {
                
                View faqView = getLayoutInflater().inflate(R.layout.item_faq, binding.faqContainer, false);
                
                TextView question = faqView.findViewById(R.id.tvQuestion);
                TextView answer = faqView.findViewById(R.id.tvAnswer);
                ImageView arrow = faqView.findViewById(R.id.imgArrow);
                
                question.setText(item.getQuestion());
                answer.setText(item.getAnswer());
                
                new FaqItemAnimator(faqView, arrow, answer);
                binding.faqContainer.addView(faqView);
            }
        }
    }

    private void setupCategoryChips() {
        binding.chipAll.setOnClickListener(v -> {
            currentCategory = "All";
            updateChipSelection(binding.chipAll);
            displayFaqItems();
        });

        binding.chipServices.setOnClickListener(v -> {
            currentCategory = "Services";
            updateChipSelection(binding.chipServices);
            displayFaqItems();
        });

        binding.chipGeneral.setOnClickListener(v -> {
            currentCategory = "General";
            updateChipSelection(binding.chipGeneral);
            displayFaqItems();
        });

        binding.chipAccount.setOnClickListener(v -> {
            currentCategory = "Account";
            updateChipSelection(binding.chipAccount);
            displayFaqItems();
        });

        // Set initial selection
        updateChipSelection(binding.chipAll);
    }

    private void updateChipSelection(Chip selectedChip) {
        binding.chipAll.setChipBackgroundColorResource(R.color.white);
        binding.chipServices.setChipBackgroundColorResource(R.color.white);
        binding.chipGeneral.setChipBackgroundColorResource(R.color.white);
        binding.chipAccount.setChipBackgroundColorResource(R.color.white);

        binding.chipAll.setTextColor(getResources().getColor(R.color.gray_600));
        binding.chipServices.setTextColor(getResources().getColor(R.color.gray_600));
        binding.chipGeneral.setTextColor(getResources().getColor(R.color.gray_600));
        binding.chipAccount.setTextColor(getResources().getColor(R.color.gray_600));

        selectedChip.setChipBackgroundColorResource(R.color.blue_500);
        selectedChip.setTextColor(getResources().getColor(R.color.blue));
    }

    private void setupBackButton() {
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private static class FaqItem {
        private final String question;
        private final String answer;
        private final String category;

        public FaqItem(String question, String answer, String category) {
            this.question = question;
            this.answer = answer;
            this.category = category;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }

        public String getCategory() {
            return category;
        }
    }
} 