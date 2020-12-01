package com.example.fingerprint_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.biometric.BiometricPrompt.AuthenticationCallback;
import androidx.biometric.BiometricPrompt.AuthenticationResult;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class FingerPrintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print);

       TextView textMsg = findViewById(R.id.textMsg);
        Button loginButton = findViewById(R.id.loginBtn);

        // biometric manager.....to check if user can access fingerprint on phone
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                textMsg.setText(R.string.err_unavailable);
                loginButton.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                textMsg.setText(R.string.err_none_enrolled);
                loginButton.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                textMsg.setText(R.string.err_no_hardware);
                loginButton.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_SUCCESS:
                textMsg.setText(R.string.success);
                break;
        }

        // biometric dialog box
        Executor executor = ContextCompat.getMainExecutor(this);

        final BiometricPrompt biometricPrompt = new BiometricPrompt(FingerPrintActivity.this, executor,
                new AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(FingerPrintActivity.this, "Login Successful !!!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                    }
                });

        final BiometricPrompt.PromptInfo  promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription("Use your finger print to log into application ")
                .setNegativeButtonText("cancel")
                .build();

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}