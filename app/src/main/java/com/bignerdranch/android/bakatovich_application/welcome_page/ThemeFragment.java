package com.bignerdranch.android.bakatovich_application.welcome_page;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.settings.SettingsFragment;
import com.bignerdranch.android.bakatovich_application.settings.Theme;


public class ThemeFragment extends Fragment {

    private LinearLayout lightThemeRadioCover;
    private LinearLayout darkThemeRadioCover;
    private RadioButton lightThemeRadioButton;
    private RadioButton darkThemeRadioButton;
    private String LIGHT_THEME = "0";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_theme, container, false);
        lightThemeRadioCover = v.findViewById(R.id.light_theme_radio_cover);
        darkThemeRadioCover = v.findViewById(R.id.dark_theme_radio_cover);
        lightThemeRadioButton = v.findViewById(R.id.light_theme_radio);
        darkThemeRadioButton = v.findViewById(R.id.dark_theme_radio);

        if (SettingsFragment.getTheme(getActivity()) == Theme.getTheme(LIGHT_THEME)) {
            setLightTheme();
        } else {
            setDarkTheme();
        }

        lightThemeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLightTheme();
                getActivity().recreate();
            }
        });

        darkThemeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDarkTheme();
                getActivity().recreate();
            }
        });

        lightThemeRadioCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLightTheme();
                getActivity().recreate();
            }
        });

        darkThemeRadioCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDarkTheme();
                getActivity().recreate();
            }
        });

        return v;
    }

    private void setLightTheme() {
        lightThemeRadioButton.setChecked(true);
        darkThemeRadioButton.setChecked(false);
        darkThemeRadioCover.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        darkThemeRadioCover.setBackground(getResources().getDrawable(R.drawable.borders, null));
        SettingsFragment.setTheme(Theme.getLight(), getActivity());
    }

    private void setDarkTheme() {
        lightThemeRadioButton.setChecked(false);
        darkThemeRadioButton.setChecked(true);
        darkThemeRadioCover.setBackgroundColor(getResources().getColor(R.color.colorGray));
        SettingsFragment.setTheme(Theme.getDark(), getActivity());
    }
}