package com.example.comp1786project.util.listener;

import androidx.biometric.BiometricPrompt;

public interface BiometricAuthListener {
    void onBiometricAuthenticationSuccess(BiometricPrompt.AuthenticationResult result);
    void onBiometricAuthenticationError(Integer errorCode, String errorMessage);
    void onBiometricAuthenticationFail();
}
