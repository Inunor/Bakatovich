package com.bignerdranch.android.bakatovich_application.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.bakatovich_application.OffsetItemDecoration;
import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.data.EntryStorage;
import com.bignerdranch.android.bakatovich_application.launcher.adapters.ListAdapter;
import com.bignerdranch.android.bakatovich_application.settings.SettingsFragment;

import java.util.Collections;


public class ListFragment extends Fragment {

    private Activity activity;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        Collections.sort(EntryStorage.getData(), SettingsFragment.getSortMethod(activity));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.content_launcher, container, false);
        createListLayout();
        return view;
    }

    private void createListLayout() {
        final RecyclerView recyclerView = view.findViewById(R.id.launcher_content);
        recyclerView.setHasFixedSize(true);
        final int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new OffsetItemDecoration(offset));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ListAdapter listAdapter = new ListAdapter(EntryStorage.getData(), activity);
        recyclerView.setAdapter(listAdapter);
        LauncherActivity.launcherAdapter = listAdapter;
    }
}
