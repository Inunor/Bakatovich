package com.bignerdranch.android.bakatovich_application.welcome_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.list.ListActivity;
import com.bignerdranch.android.bakatovich_application.settings.Layout;
import com.bignerdranch.android.bakatovich_application.settings.SettingsFragment;
import com.bignerdranch.android.bakatovich_application.settings.Theme;


public class CongratulationFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_congratulation, container, false);
        Button startButton = v.findViewById(R.id.start_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SettingsFragment.hasTheme(getActivity())) {
                    SettingsFragment.setTheme(Theme.getDefault(), getActivity());
                }
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

    public static CongratulationFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        CongratulationFragment fragment = new CongratulationFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
