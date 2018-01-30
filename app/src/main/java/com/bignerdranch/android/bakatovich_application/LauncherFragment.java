package com.bignerdranch.android.bakatovich_application;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.bakatovich_application.launcher.LauncherAdapter;
import com.bignerdranch.android.bakatovich_application.launcher.OffsetItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LauncherFragment extends Fragment {
    private RecyclerView colorRecyclerView;
    private final static String IS_STANDARD_LAYOUT = "is_standard_layout";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_list, container, false);
        colorRecyclerView = view.findViewById(R.id.color_recycler_view);
        colorRecyclerView.setHasFixedSize(true);

        final int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        boolean isStandardLayout = getActivity().getIntent().getBooleanExtra(IS_STANDARD_LAYOUT, true);
        colorRecyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        final int spanCount = getResources().getInteger(isStandardLayout ? R.integer.standard_count : R.integer.dense_count);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        colorRecyclerView.setLayoutManager(layoutManager);

        updateUI();
        return view;
    }

    private void updateUI() {
        final List<Integer> data = generateData();
        final LauncherAdapter launcherAdapter = new LauncherAdapter(data);
        colorRecyclerView.setAdapter(launcherAdapter);
    }

    @NonNull
    private List<Integer> generateData() {
        final List<Integer> colors = new ArrayList<>();
        final Random random = new Random();
        for (int i = 0; i < 1000; ++i) {
            int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt());
            colors.add(color);
        }
        return colors;
    }
}
