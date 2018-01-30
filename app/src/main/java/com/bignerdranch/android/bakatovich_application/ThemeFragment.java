package com.bignerdranch.android.bakatovich_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * Created by anton on 29.01.2018.
 */

public class ThemeFragment extends Fragment {
    private final static String IS_STANDARD_LAYOUT = "is_standard_layout";

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_theme_chooser, container, false);
        final Button nextButton = v.findViewById(R.id.next_button);
        final RadioButton standardLayoutRadioButton = v.findViewById(R.id.standard_layout_radio);
        final RadioButton denseLayoutRadioButton = v.findViewById(R.id.dense_layout_radio);

        standardLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                standardLayoutRadioButton.setChecked(true);
                denseLayoutRadioButton.setChecked(false);
            }
        });

        denseLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                standardLayoutRadioButton.setChecked(false);
                denseLayoutRadioButton.setChecked(true);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LauncherActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK );
                intent.putExtra(IS_STANDARD_LAYOUT, standardLayoutRadioButton.isChecked());
                startActivity(intent);
            }
        });

        return v;
    }
}
