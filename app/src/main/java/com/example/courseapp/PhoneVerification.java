package com.example.courseapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneVerification extends AppCompatActivity {

    private EditText phoneNumberEditText, otpEditText;
    private Button sendOtpButton, verifyOtpButton;

    private FirebaseAuth firebaseAuth;
    private String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_phone_authverify);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize Views
        phoneNumberEditText = findViewById(R.id.phone_number);
        otpEditText = findViewById(R.id.otp_code);
        sendOtpButton = findViewById(R.id.send_otp_button);
        verifyOtpButton = findViewById(R.id.verify_otp_button);

        // Send OTP Button Click
        sendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberEditText.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(PhoneVerification.this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendVerificationCode(phoneNumber);
            }
        });

        // Verify OTP Button Click
        verifyOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otpEditText.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(PhoneVerification.this, "Enter the OTP", Toast.LENGTH_SHORT).show();
                    return;
                }
                verifyCode(code);
            }
        });
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneNumber)         // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS)   // Timeout and unit
                        .setActivity(this)                  // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                                // Auto-retrieval or instant verification completed
                                signInWithCredential(credential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                    // Invalid request
                                    Toast.makeText(PhoneVerification.this, "Invalid phone number.", Toast.LENGTH_SHORT).show();
                                } else if (e instanceof FirebaseTooManyRequestsException) {
                                    // SMS quota exceeded
                                    Toast.makeText(PhoneVerification.this, "Quota exceeded. Try again later.", Toast.LENGTH_SHORT).show();
                                } else {
                                    // General error
                                    Toast.makeText(PhoneVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }


                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                super.onCodeSent(verificationId, token);
                                PhoneVerification.this.verificationId = verificationId;

                                // Show OTP input and verify button
                                otpEditText.setVisibility(View.VISIBLE);
                                verifyOtpButton.setVisibility(View.VISIBLE);
                                sendOtpButton.setVisibility(View.GONE);
                                phoneNumberEditText.setEnabled(false);
                                Toast.makeText(PhoneVerification.this, "OTP sent!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(PhoneVerification.this, "Phone Authentication Successful!", Toast.LENGTH_SHORT).show();
                        // Redirect to another activity
                      Intent intent = new Intent(PhoneVerification.this, MainActivity.class);
                      startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(PhoneVerification.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}