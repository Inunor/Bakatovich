package com.bignerdranch.android.bakatovich_application.launcher.adapters;


import android.content.Context;
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

    private final String TAG;

    public ListAdapter(@NonNull final List<Entry> data, final Context context, final String DATA_THEME) {
        super(data, context, DATA_THEME);
        TAG = context.getString(R.string.title_application_adapter);
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
        final TextView textView = listHolder.getTitle();
        view.setBackground(data.get(position).getIcon());
        textView.setText(data.get(position).getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(data.get(position).getPackageName());
                if (launchIntent != null) {
                    context.startActivity(launchIntent);
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
