package com.example.e_learningcourse.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_learningcourse.R;

public class ResultScreenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        ImageView icon = findViewById(R.id.iconResult);
        TextView message = findViewById(R.id.textMessage);
        Button button = findViewById(R.id.buttonAction);

        String resultText = getIntent().getStringExtra("title");
        int iconRes = getIntent().getIntExtra("icon", R.drawable.ic_check);
        String buttonText = getIntent().getStringExtra("buttonText");
        String targetActivity = getIntent().getStringExtra("targetActivity");

        icon.setImageResource(iconRes);
        message.setText(resultText);
        button.setText(buttonText);

        button.setOnClickListener(v -> {
            try {
                Class<?> clazz = Class.forName(targetActivity);
                Intent intent = new Intent(ResultScreenActivity.this, clazz);

                String targetTab = getIntent().getStringExtra("targetTab");
                if (targetTab != null) {
                    intent.putExtra("targetTab", targetTab);
                }

                startActivity(intent);
                finish();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
