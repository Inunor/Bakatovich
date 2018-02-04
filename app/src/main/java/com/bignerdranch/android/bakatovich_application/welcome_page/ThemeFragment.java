package com.bignerdranch.android.bakatovich_application.welcome_page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.settings.SettingsFragment;
import com.bignerdranch.android.bakatovich_application.settings.Theme;

public class ThemeFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_theme, container, false);
        final LinearLayout lightThemeRadioCover = v.findViewById(R.id.light_theme_radio_cover);
        final LinearLayout darkThemeRadioCover = v.findViewById(R.id.dark_theme_radio_cover);
        final RadioButton lightThemeRadioButton = v.findViewById(R.id.light_theme_radio);
        final RadioButton darkThemeRadioButton = v.findViewById(R.id.dark_theme_radio);

        lightThemeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightThemeRadioButton.setChecked(true);
                darkThemeRadioButton.setChecked(false);
                darkThemeRadioCover.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                darkThemeRadioCover.setBackground(getResources().getDrawable(R.drawable.borders, null));
                SettingsFragment.setTheme(Theme.getLight(), getActivity());
            }
        });

        darkThemeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightThemeRadioButton.setChecked(false);
                darkThemeRadioButton.setChecked(true);
                darkThemeRadioCover.setBackgroundColor(getResources().getColor(R.color.colorGray));
                SettingsFragment.setTheme(Theme.getDark(), getActivity());
            }
        });

        return v;
    }

    public static ThemeFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        ThemeFragment fragment = new ThemeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}