package com.example.comp1786project.feature.register;

import static org.koin.java.KoinJavaComponent.inject;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricPrompt;
import androidx.lifecycle.ViewModelProvider;

import com.example.comp1786project.databinding.FragmentRegisterBinding;
import com.example.comp1786project.feature.base.BaseFragment;
import com.example.comp1786project.local.database.UserDao;
import com.example.comp1786project.model.User;
import com.example.comp1786project.util.BiometricSecurityUtils;
import com.example.comp1786project.util.biometric.BiometricHelper;
import com.example.comp1786project.util.biometric.BiometricStatus;
import com.example.comp1786project.util.listener.BiometricAuthListener;

import java.util.Objects;

import javax.crypto.Cipher;

import kotlin.Lazy;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding, RegisterViewModel> implements BiometricAuthListener {
    private RegisterViewModel viewModel;
    private final Lazy<BiometricSecurityUtils> biometricSecurityUtils = inject(BiometricSecurityUtils.class);
    private final Lazy<UserDao> userDao = inject(UserDao.class);

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }
    @Override
    protected RegisterViewModel viewModel() {
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        return viewModel;
    }

    @Override
    public FragmentRegisterBinding onCreateViewBinding(LayoutInflater inflater) {
        return FragmentRegisterBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        viewBinding.btnRegister.setOnClickListener(v -> {
            String username = Objects.requireNonNull(viewBinding.edtUsername.getText()).toString();
            String password = Objects.requireNonNull(viewBinding.edtPassword.getText()).toString();
            String confirmPassword = Objects.requireNonNull(viewBinding.edtConfirmPassword.getText()).toString();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill to all field", Toast.LENGTH_SHORT).show();
                return;
            }
            if (username.length() < 6 || password.length() < 6 || confirmPassword.length() < 6) {
                Toast.makeText(requireContext(), "Please input valid field", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.equals(confirmPassword)) {
                viewModel.username = username;
                viewModel.password = password;

                BiometricHelper biometricHelper = new BiometricHelper(this);
                if (biometricHelper.biometricEnable() == BiometricStatus.ENROLLED) {
                    BiometricPrompt.CryptoObject cryptoObject = new BiometricPrompt.CryptoObject(
                            Objects.requireNonNull(biometricSecurityUtils.getValue().getInitializedCipherForEncryption())
                    );
                    biometricHelper.showBiometricPrompt(this, cryptoObject);
                } else {
                    Toast.makeText(requireContext(), "Please check biometric feature on your phone", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Confirm password doesn't match", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBiometricAuthenticationSuccess(BiometricPrompt.AuthenticationResult result) {
        Cipher cipher = Objects.requireNonNull(result.getCryptoObject()).getCipher();
        if (cipher != null) {
            Pair<byte[], byte[]> encryptData = biometricSecurityUtils.getValue().encryptData(cipher, viewModel.password);
            byte[] passwordEncrypt = encryptData.first;

            viewModel.saveInitializationVector(encryptData.second);
            viewModel.saveUsername(viewModel.username);
            viewModel.savePasswordEncrypt(passwordEncrypt);

            User user = new User();
            user.username = viewModel.username;
            user.password = viewModel.password;
            userDao.getValue().insertUser(user);
            navigateUp();
        }
    }

    @Override
    public void onBiometricAuthenticationError(Integer errorCode, String errorMessage) {
        Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBiometricAuthenticationFail() {
        Toast.makeText(requireContext(), "Fail!", Toast.LENGTH_SHORT).show();
    }
}