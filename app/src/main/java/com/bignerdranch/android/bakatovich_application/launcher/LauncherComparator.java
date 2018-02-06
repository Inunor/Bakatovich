package com.bignerdranch.android.bakatovich_application.launcher;


import com.bignerdranch.android.bakatovich_application.data.Entry;

import java.util.Comparator;


public class LauncherComparator {
    private final static String NO_SORT = "0";
    private final static String DATE = "1";
    private final static String AZ = "2";
    private final static String ZA = "3";
    private final static String LAUNCHED = "4";

    public static Comparator<Entry> getMethod(String code) {
        switch (code) {
            case LauncherComparator.NO_SORT:
                return new Comparator<Entry>() {
                    @Override
                    public int compare(Entry e1, Entry e2) {
                        return 0;
                    }
                };
            case LauncherComparator.DATE:
                return new Comparator<Entry>() {
                    @Override
                    public int compare(Entry e1, Entry e2) {
                        return e2.getUpdateTime().compareTo(e1.getUpdateTime());
                    }
                };
            case LauncherComparator.AZ:
                return new Comparator<Entry>() {
                    @Override
                    public int compare(Entry e1, Entry e2) {
                        return e1.getName().compareToIgnoreCase(e2.getName());
                    }
                };
            case LauncherComparator.ZA:
                return new Comparator<Entry>() {
                    @Override
                    public int compare(Entry e1, Entry e2) {
                        return e2.getName().compareToIgnoreCase(e1.getName());
                    }
                };
            case LauncherComparator.LAUNCHED:
                return new Comparator<Entry>() {
                    @Override
                    public int compare(Entry e1, Entry e2) {
                        return e2.getLaunched().compareTo(e1.getLaunched());
                    }
                };
            default:
                return getMethod(LauncherComparator.NO_SORT);

        }
    }

    public static String getNoSort() {
        return NO_SORT;
    }

    public static String getDATE() {
        return DATE;
    }

    public static String getAZ() {
        return AZ;
    }

    public static String getZA() {
        return ZA;
    }

    public static String getLAUNCHED() {
        return LAUNCHED;
    }
}
