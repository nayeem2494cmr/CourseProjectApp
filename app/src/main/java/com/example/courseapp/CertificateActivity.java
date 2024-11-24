package com.example.courseapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CertificateActivity extends AppCompatActivity {
    private EditText answerEditText;
    private Button submitButton;
    private CountDownTimer countDownTimer;
    private TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);

        answerEditText = findViewById(R.id.answer_edit_text);
        timerTextView = findViewById(R.id.timer_text);
        submitButton = findViewById(R.id.submit_button);
        submitButton.setEnabled(false);


        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time left: " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                answerEditText.setEnabled(false);
                submitButton.setEnabled(true);
                Toast.makeText(CertificateActivity.this, "Time is up! You can submit your answer.", Toast.LENGTH_SHORT).show();
            }
        };
        countDownTimer.start();

        submitButton.setOnClickListener(v -> {
            String answer = answerEditText.getText().toString();
            Toast.makeText(this, "Answer Submitted: " + answer, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CertificateActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
