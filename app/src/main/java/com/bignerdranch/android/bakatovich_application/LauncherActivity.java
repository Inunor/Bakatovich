package com.bignerdranch.android.bakatovich_application;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_launcher);

        if (fragment == null) {
            fragment = new LauncherFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_launcher, fragment)
                    .commit();
        }
    }
}