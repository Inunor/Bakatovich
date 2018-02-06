package com.bignerdranch.android.bakatovich_application.launcher.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.data.Database;
import com.bignerdranch.android.bakatovich_application.data.Entry;
import com.bignerdranch.android.bakatovich_application.Holder;

import java.util.List;

public class GridAdapter extends LauncherAdapter {

    public GridAdapter(@NonNull final List<Entry> data, final Activity activity) {
        super(data, activity);
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new Holder.GridHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.GridHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.GridHolder gridHolder, final int position) {
        final View view = gridHolder.getImageView();
        final TextView textView = gridHolder.getTextView();
        view.setBackground(data.get(position).getIcon());
        textView.setText(data.get(position).getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = activity.getPackageManager().getLaunchIntentForPackage(data.get(position).getPackageName());
                if (launchIntent != null) {
                    activity.startActivity(launchIntent);
                    data.get(position).updateLaunched();
                    Database.insertOrUpdate(data.get(position));
                }
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopUpMenu(v, position);
                return false;
            }
        });
    }
}
