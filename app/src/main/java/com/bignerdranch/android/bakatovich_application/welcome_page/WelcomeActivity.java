package com.bignerdranch.android.bakatovich_application.welcome_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.bakatovich_application.launcher.LauncherActivity;
import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.settings.SettingsFragment;

import java.util.ArrayList;
import java.util.List;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SettingsFragment.isAllSettings(this)) {
            startActivity(new Intent(this, LauncherActivity.class));
            finish();
        }
        setContentView(R.layout.activity_welcome);

        final List<Integer> data = new ArrayList<>();
        data.add(R.layout.fragment_welcome);
        data.add(R.layout.fragment_description);
        data.add(R.layout.fragment_theme);
        data.add(R.layout.fragment_layout);
        data.add(R.layout.fragment_congratulation);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        WelcomeViewPagerAdapter adapter = new WelcomeViewPagerAdapter(fragmentManager, data, getApplicationContext());

        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);
    }
}
