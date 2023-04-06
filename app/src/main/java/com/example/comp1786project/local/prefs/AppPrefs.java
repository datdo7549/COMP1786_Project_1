package com.example.comp1786project.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

public class AppPrefs {

    private static final String KEY_USERNAME = "KEY_USERNAME";
    private static final String KEY_INITIALIZATION_VECTOR  = "KEY_INITIALIZATION_VECTOR";
    private static final String KEY_PASSWORD_ENCRYPT  = "KEY_PASSWORD_ENCRYPT";
    private final SharedPreferences sharedPreferences;

    public AppPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences("app-data-prefs", Context.MODE_PRIVATE);
    }

    public void setUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public void setInitializationVector(byte[] initializationVector) {
        String encodedString = Base64.encodeToString(initializationVector, Base64.DEFAULT);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_INITIALIZATION_VECTOR, encodedString);
        editor.apply();
    }

    public byte[] getInitializationVector() {
        String iv = sharedPreferences.getString(KEY_INITIALIZATION_VECTOR, "");
        // Convert Base64 encoded String to byte[]
        return Base64.decode(iv, Base64.DEFAULT);
    }

    public void setPasswordEncrypt(byte[] passwordEncrypt) {
        String encodedString = Base64.encodeToString(passwordEncrypt, Base64.DEFAULT);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PASSWORD_ENCRYPT, encodedString);
        editor.apply();
    }

    public byte[] getPasswordEncrypt() {
        String passwordEncrypt = sharedPreferences.getString(KEY_PASSWORD_ENCRYPT, "");
        // Convert Base64 encoded String to byte[]
        return Base64.decode(passwordEncrypt, Base64.DEFAULT);
    }
}
