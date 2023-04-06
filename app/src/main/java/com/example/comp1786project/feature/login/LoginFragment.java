package com.example.comp1786project.feature.login;

import static org.koin.java.KoinJavaComponent.inject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricPrompt;
import androidx.lifecycle.ViewModelProvider;

import com.example.comp1786project.databinding.FragmentLoginBinding;
import com.example.comp1786project.feature.base.BaseFragment;
import com.example.comp1786project.feature.homepage.HomeFragment;
import com.example.comp1786project.feature.register.RegisterFragment;
import com.example.comp1786project.model.User;
import com.example.comp1786project.util.BiometricSecurityUtils;
import com.example.comp1786project.util.biometric.BiometricHelper;
import com.example.comp1786project.util.biometric.BiometricStatus;
import com.example.comp1786project.util.listener.BiometricAuthListener;

import java.util.Objects;

import javax.crypto.Cipher;

import kotlin.Lazy;


public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> implements BiometricAuthListener {
    private LoginViewModel viewModel;
    private final Lazy<BiometricSecurityUtils> biometricSecurityUtils = inject(BiometricSecurityUtils.class);

    @Override
    protected LoginViewModel viewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        return viewModel;
    }

    @Override
    public FragmentLoginBinding onCreateViewBinding(LayoutInflater inflater) {
        return FragmentLoginBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        viewBinding.tvRegister.setOnClickListener(v ->
            navigate(RegisterFragment.newInstance(), false)
        );
        viewBinding.btnLogin.setOnClickListener(v -> {
            String username = Objects.requireNonNull(viewBinding.edtUsername.getText()).toString();
            String password = Objects.requireNonNull(viewBinding.edtPassword.getText()).toString();
            User user = viewModel.getUserFromDb(username);
            if (username.equals(user.username) && password.equals(user.password)) {
                viewModel.saveUsername(username);
                navigate(HomeFragment.newInstance(), false);
            } else {
                Toast.makeText(requireContext(), "Login fail!", Toast.LENGTH_SHORT).show();
            }
        });
        viewBinding.btnFingerprint.setOnClickListener(v -> {
            BiometricHelper biometricHelper = new BiometricHelper(this);
            if (biometricHelper.biometricEnable() == BiometricStatus.ENROLLED) {
                byte[] initializationVector = viewModel().getInitializationVector();
                BiometricPrompt.CryptoObject cryptoObject = new BiometricPrompt.CryptoObject(
                        Objects.requireNonNull(biometricSecurityUtils.getValue().getInitializedCipherForDecryption(initializationVector))
                );
                biometricHelper.showBiometricPrompt(this, cryptoObject);
            } else {
                Toast.makeText(requireContext(), "Please check biometric feature on your phone", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBiometricAuthenticationSuccess(BiometricPrompt.AuthenticationResult result) {
        Cipher cipher = Objects.requireNonNull(result.getCryptoObject()).getCipher();
        if (cipher != null) {
            byte[] passwordEncrypt = viewModel.getPasswordEncrypt();
            String username = viewModel.getUsername();
            String passwordDecrypt = biometricSecurityUtils.getValue().decryptData(cipher, passwordEncrypt);

            User user = viewModel.getUserFromDb(username);
            if (user != null) {
                if (username.equals(user.username) && passwordDecrypt.equals(user.password)) {
                    viewModel.saveUsername(username);
                    navigate(HomeFragment.newInstance(), false);
                } else {
                    Toast.makeText(requireContext(), "Login fail!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Please register new account", Toast.LENGTH_SHORT).show();
            }
            Log.d("asd", "onBiometricAuthenticationSuccess: ");
        }
    }

    @Override
    public void onBiometricAuthenticationError(Integer errorCode, String errorMessage) {

    }

    @Override
    public void onBiometricAuthenticationFail() {

    }
}