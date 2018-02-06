package com.bignerdranch.android.bakatovich_application.data;


import android.graphics.drawable.Drawable;


public class Entry {
    private String name;
    private String packageName;
    private Long updateTime;
    private Integer launched;
    private Drawable icon;

    public Entry(final String name, final String packageName, final long updateTime, final Drawable icon) {
        this.name = name;
        this.packageName = packageName;
        this.updateTime = updateTime;
        this.launched = Database.get(packageName);
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public Integer getLaunched() {
        return launched;
    }

    public void updateLaunched() {
        launched++;
    }

    public Drawable getIcon() {
        return icon;
    }
}
