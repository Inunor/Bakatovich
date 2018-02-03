package com.bignerdranch.android.bakatovich_application.settings;

import com.bignerdranch.android.bakatovich_application.R;

public class Theme {
    private static final String LIGHT = "0";
    private static final String DARK  = "1";

    public static int getTheme(String code) {
        switch (code) {
            case LIGHT: return R.style.AppTheme;
            case DARK:  return R.style.AppThemeDark;
            default:    return R.style.AppTheme;
        }
    }

    public static String getLight() {
        return LIGHT;
    }

    public static String getDark() {
        return DARK;
    }

    public static String getDefault() {
        return LIGHT;
    }
}
