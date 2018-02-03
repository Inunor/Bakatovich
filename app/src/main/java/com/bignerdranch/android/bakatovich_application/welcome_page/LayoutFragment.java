package com.bignerdranch.android.bakatovich_application.welcome_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.bignerdranch.android.bakatovich_application.list.ListActivity;
import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.settings.SettingsFragment;
import com.bignerdranch.android.bakatovich_application.settings.Layout;

public class LayoutFragment extends Fragment {
    private final static String IS_STANDARD_LAYOUT = "is_standard_layout";

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_layout, container, false);
        getActivity().setTheme(SettingsFragment.getTheme(getActivity()));
        final Button nextButton = v.findViewById(R.id.next_button);
        final RadioButton standardLayoutRadioButton = v.findViewById(R.id.standard_layout_radio);
        final RadioButton denseLayoutRadioButton = v.findViewById(R.id.dense_layout_radio);

        standardLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                standardLayoutRadioButton.setChecked(true);
                denseLayoutRadioButton.setChecked(false);
                SettingsFragment.setLayout(Layout.getStandard(), getActivity());
            }
        });

        denseLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                standardLayoutRadioButton.setChecked(false);
                denseLayoutRadioButton.setChecked(true);
                SettingsFragment.setLayout(Layout.getDense(), getActivity());
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SettingsFragment.hasLayout(getActivity())) {
                    SettingsFragment.setLayout(Layout.getDefault(), getActivity());
                }
                Intent intent = new Intent(view.getContext(), ListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });

        return v;
    }
}
