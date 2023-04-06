package com.example.comp1786project.feature.register;

import static org.koin.java.KoinJavaComponent.inject;

import com.example.comp1786project.feature.base.BaseViewModel;
import com.example.comp1786project.local.prefs.AppPrefs;

import kotlin.Lazy;

public class RegisterViewModel extends BaseViewModel {
    private Lazy<AppPrefs> appPrefs = inject(AppPrefs.class);
    public String username;
    public String password;

    public void saveUsername(String username) {
        appPrefs.getValue().setUsername(username);
    }

    public void savePasswordEncrypt(byte[] passwordEncrypt) {
        appPrefs.getValue().setPasswordEncrypt(passwordEncrypt);
    }

    public void saveInitializationVector(byte[] initializationVector) {
        appPrefs.getValue().setInitializationVector(initializationVector);
    }
}
