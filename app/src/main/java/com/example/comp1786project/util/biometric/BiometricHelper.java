package com.example.comp1786project.util.biometric;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.comp1786project.R;
import com.example.comp1786project.util.listener.BiometricAuthListener;

import java.util.concurrent.Executor;

public class BiometricHelper {
    private final Fragment fragment;
    private final Context context;
    private final BiometricManager biometricManager;

    public BiometricHelper(Fragment fragment) {
        this.fragment = fragment;
        context = fragment.getContext();
        assert context != null;
        biometricManager = BiometricManager.from(context);
    }

    private BiometricType getBiometricType() {
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
            return BiometricType.ENROLLED;
        }
        return BiometricType.NOT_AVAILABLE;
    }

    public BiometricStatus biometricEnable() {
        if (getBiometricType() == BiometricType.ENROLLED) {
            return BiometricStatus.ENROLLED;
        }
        return BiometricStatus.NOT_AVAILABLE;
    }

    private BiometricPrompt.PromptInfo setBiometricPromptInfo() {
        BiometricPrompt.PromptInfo.Builder builder = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric")
                .setSubtitle("Biometric settings")
                .setDescription("Biometric")
                .setConfirmationRequired(true);

        builder.setNegativeButtonText(context.getResources().getString(R.string.cancel_title));
        return builder.build();
    }

    private BiometricPrompt initBiometricPrompt(BiometricAuthListener biometricAuthListener) {
        Executor executor = ContextCompat.getMainExecutor(context);

        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                biometricAuthListener.onBiometricAuthenticationError(
                        errorCode,
                        errString.toString()
                );
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                biometricAuthListener.onBiometricAuthenticationSuccess(result);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                biometricAuthListener.onBiometricAuthenticationFail();
            }
        };
        return new BiometricPrompt(fragment, executor, callback);
    }

    public void showBiometricPrompt(BiometricAuthListener listener, BiometricPrompt.CryptoObject cryptoObject) {
        BiometricPrompt.PromptInfo promptInfo = setBiometricPromptInfo();
        BiometricPrompt biometricPrompt = initBiometricPrompt(listener);
        if (cryptoObject == null) {
            biometricPrompt.authenticate(promptInfo);
        } else {
            biometricPrompt.authenticate(promptInfo, cryptoObject);
        }
    }
}
