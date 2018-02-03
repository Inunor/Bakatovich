package com.bignerdranch.android.bakatovich_application.settings;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.bakatovich_application.R;


public class SettingsActivity extends AppCompatActivity {
    private static final String THEME_KEY = "theme";
    private static final String LAYOUT_KEY = "layout";
    private static final String WELCOME_PAGE_KEY = "welcome_page";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(SettingsFragment.getTheme(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
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
}
