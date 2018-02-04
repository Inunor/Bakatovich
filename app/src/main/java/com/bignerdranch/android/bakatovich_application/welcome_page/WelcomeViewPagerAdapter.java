package com.bignerdranch.android.bakatovich_application.welcome_page;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bignerdranch.android.bakatovich_application.R;

import java.util.List;


public class WelcomeViewPagerAdapter extends FragmentPagerAdapter {

    @NonNull
    private final List<Integer> data;
    Context context;

    public WelcomeViewPagerAdapter(@NonNull final FragmentManager fm,
                                   @NonNull final List<Integer> data,
                                   Context context) {
        super(fm);
        this.data = data;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:  return WelcomeFragment.newInstance(data.get(position));
            case 1:  return DescriptionFragment.newInstance(data.get(position));
            case 2:  return ThemeFragment.newInstance(data.get(position));
            case 3:  return LayoutFragment.newInstance(data.get(position));
            case 4:  return CongratulationFragment.newInstance(data.get(position));
            default: return WelcomeFragment.newInstance(data.get(position));
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:  return context.getString(R.string.welcome_title);
            case 1:  return context.getString(R.string.description_title);
            case 2:  return context.getString(R.string.theme_title);
            case 3:  return context.getString(R.string.layout_title);
            case 4:  return context.getString(R.string.congratulation_title);
            default: return context.getString(R.string.welcome_title);
        }
    }
}
