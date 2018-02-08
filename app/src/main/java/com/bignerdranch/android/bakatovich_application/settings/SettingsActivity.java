package com.bignerdranch.android.bakatovich_application.settings;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bignerdranch.android.bakatovich_application.MainActivity;
import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.launcher.LauncherActivity;


public class SettingsActivity extends AppCompatActivity {

    private static final String THEME_KEY = "theme";
    private static final String LAYOUT_KEY = "layout";
    private static final String SORT_KEY = "sort";
    private static final String WELCOME_PAGE_KEY = "welcome_page";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(SettingsFragment.getTheme(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.settings_fragment_container, new SettingsFragment())
                .commit();
    }

    public static String getThemeKey() {
        return THEME_KEY;
    }

    public static String getLayoutKey() {
        return LAYOUT_KEY;
    }

    public static String getWelcomePageKey() {
        return WELCOME_PAGE_KEY;
    }

    public static String getSortKey() {
        return SORT_KEY;
    }
}
