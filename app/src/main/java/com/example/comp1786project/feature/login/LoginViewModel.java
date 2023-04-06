package com.example.comp1786project.feature.login;

import static org.koin.java.KoinJavaComponent.inject;

import com.example.comp1786project.feature.base.BaseViewModel;
import com.example.comp1786project.local.database.UserDao;
import com.example.comp1786project.local.prefs.AppPrefs;
import com.example.comp1786project.model.User;

import kotlin.Lazy;

public class LoginViewModel extends BaseViewModel {
    private final Lazy<AppPrefs> appPrefs = inject(AppPrefs.class);
    private final Lazy<UserDao> userDao = inject(UserDao.class);

    public void saveUsername(String username) {
        appPrefs.getValue().setUsername(username);
    }

    public byte[] getInitializationVector() {
        return appPrefs.getValue().getInitializationVector();
    }

    public String getUsername() {
        return appPrefs.getValue().getUsername();
    }

    public byte[] getPasswordEncrypt() {
        return appPrefs.getValue().getPasswordEncrypt();
    }

    public User getUserFromDb(String username) {
        return userDao.getValue().getUser(username);
    }
}
