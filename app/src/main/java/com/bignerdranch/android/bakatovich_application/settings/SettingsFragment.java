package com.bignerdranch.android.bakatovich_application.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.settings.Layout;
import com.bignerdranch.android.bakatovich_application.settings.SettingsActivity;
import com.bignerdranch.android.bakatovich_application.settings.Theme;


public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }


    public static boolean isAllSettings(final Activity activity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return sharedPreferences.contains( SettingsActivity.getThemeKey() )  &&
               sharedPreferences.contains( SettingsActivity.getLayoutKey() ) &&
               (!sharedPreferences.getBoolean(SettingsActivity.getWelcomePageKey(), false));
    }

    /*public static void setWelcomePageValue(final boolean value, final Activity activity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SettingsActivity.getWelcomePageKey(), value);
        editor.apply();
    }*/

    public static int getTheme(final Activity activity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String code = sharedPreferences.getString(SettingsActivity.getThemeKey(), Theme.getDefault());
        return Theme.getTheme(code);
    }

    public static void setTheme(final String theme, final Activity activity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SettingsActivity.getThemeKey(), theme);
        editor.apply();
    }

    public static boolean hasTheme(final Activity activity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return sharedPreferences.contains(SettingsActivity.getThemeKey());
    }

    public static int getLayoutColumn(final Activity activity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String code = sharedPreferences.getString(SettingsActivity.getLayoutKey(), Layout.getDefault());
        return Layout.getColumn(code);
    }

    public static void setLayout(final String layout, final Activity activity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SettingsActivity.getLayoutKey(), layout);
        editor.apply();
    }

    public static boolean hasLayout(final Activity activity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return sharedPreferences.contains( SettingsActivity.getLayoutKey() );
    }


}