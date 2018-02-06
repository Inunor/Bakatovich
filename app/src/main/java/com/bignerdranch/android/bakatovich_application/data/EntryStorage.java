package com.bignerdranch.android.bakatovich_application.data;


import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;


public class EntryStorage {
    private static ArrayList<Entry> data = new ArrayList<>();

    public static void generateData(Activity activity) {
        data.clear();
        PackageManager packageManager = activity.getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(0);
        for (ApplicationInfo applicationInfo : applicationInfoList) {
            try {
                if (packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null) {
                    Entry entry = getEntryFromPackageName(applicationInfo.packageName, activity);
                    if (!data.contains(entry)) {
                        data.add(entry);
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static Entry getEntryFromPackageName(final String packageName, final Activity activity) throws PackageManager.NameNotFoundException {
        final PackageManager packageManager = activity.getPackageManager();
        final String name = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        final long updatedTime = packageManager.getPackageInfo(packageName, 0).lastUpdateTime;
        final Drawable icon = packageManager.getApplicationIcon(packageName);
        return new Entry(name, packageName, updatedTime, icon);
    }

    public static ArrayList<Entry> getData() {
        return data;
    }

    public static void addEntry(final Entry entry) {
        data.add(entry);
    }

    public static void removeEntry(final Entry entry) {
        data.remove(entry);
    }
}
