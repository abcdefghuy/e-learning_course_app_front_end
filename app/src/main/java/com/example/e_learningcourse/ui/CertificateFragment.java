package com.example.e_learningcourse.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.e_learningcourse.R;
import com.google.android.material.button.MaterialButton;

public class CertificateFragment extends Fragment {

    private TextView tvStudentName;
    private TextView tvCourseName;
    private TextView tvIssueDate;
    private TextView tvCertificateId;
    private MaterialButton btnDownload;

    public static CertificateFragment newInstance() {
        return new CertificateFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_certificate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        tvStudentName = view.findViewById(R.id.tvStudentName);
        tvCourseName = view.findViewById(R.id.tvCourseName);
        tvIssueDate = view.findViewById(R.id.tvIssueDate);
        tvCertificateId = view.findViewById(R.id.tvCertificateId);
        btnDownload = view.findViewById(R.id.btnDownload);

        // Set up click listener for download button
        btnDownload.setOnClickListener(v -> downloadCertificate());

        // Load certificate data
        loadCertificateData();
    }

    private void loadCertificateData() {
        // TODO: Load actual certificate data from your data source
        tvStudentName.setText("Jacob Jones");
        tvCourseName.setText("Design Thinking Fundamental");
        tvIssueDate.setText("Issued on January 25, 2025");
        tvCertificateId.setText("ID : ED452K.JH4521");
    }

    private void downloadCertificate() {
        // TODO: Implement certificate download functionality
    }
} 