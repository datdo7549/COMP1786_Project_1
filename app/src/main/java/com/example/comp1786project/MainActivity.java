package com.example.comp1786project;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.comp1786project.databinding.ActivityMainBinding;
import com.example.comp1786project.feature.login.LoginFragment;

public class MainActivity extends BaseActivity {
    @Override
    public Fragment startDestination() {
        return new LoginFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        ActivityMainBinding viewBinding = ActivityMainBinding.inflate(inflater);
        setContentView(viewBinding.getRoot());
    }
}