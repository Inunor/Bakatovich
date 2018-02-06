package com.bignerdranch.android.bakatovich_application.launcher.adapters;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.Holder;
import com.bignerdranch.android.bakatovich_application.data.Database;
import com.bignerdranch.android.bakatovich_application.data.Entry;

import java.util.List;


public class ListAdapter extends LauncherAdapter {

    public ListAdapter(@NonNull final List<Entry> data, final Activity activity) {
        super(data, activity);
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new Holder.ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindListView((Holder.ListHolder) holder, position);
    }

    private void bindListView(@NonNull final Holder.ListHolder listHolder, final int position) {
        final View view = listHolder.getImageView();
        final TextView title = listHolder.getTitle();
        final TextView text = listHolder.getText();
        view.setBackground(data.get(position).getIcon());
        title.setText(data.get(position).getName());
        text.setText(data.get(position).getPackageName());

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

    @Override
    public int getItemCount() {
        return data.size();
    }
}
