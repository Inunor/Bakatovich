package com.bignerdranch.android.bakatovich_application.settings;


import com.bignerdranch.android.bakatovich_application.R;

public class Layout {
    private static final String STANDARD = "0";
    private static final String DENSE = "1";

    public static int getColumn(String code) {
        switch (code) {
            case STANDARD: return R.integer.standard_count;
            case DENSE:    return R.integer.dense_count;
            default:       return R.integer.standard_count;
        }
    }

    public static String getStandard() {
        return STANDARD;
    }

    public static String getDense() {
        return DENSE;
    }

    public static String getDefault() {
        return STANDARD;
    }
}
