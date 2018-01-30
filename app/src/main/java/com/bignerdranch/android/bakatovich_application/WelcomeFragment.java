package com.bignerdranch.android.bakatovich_application;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by anton on 28.01.2018.
 */

public class WelcomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        final Button nextButton = v.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new DescriptionFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).
                        addToBackStack("description").commit();
            }
        });
        return v;
    }
}
