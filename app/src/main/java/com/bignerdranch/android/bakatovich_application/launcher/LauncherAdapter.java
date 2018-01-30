package com.bignerdranch.android.bakatovich_application.launcher;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.bakatovich_application.R;

import java.util.List;

public class LauncherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final List<Integer> data;

    public LauncherAdapter(@NonNull final List<Integer> data) {
        this.data = data;
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        // Изменить чтобы выполнялось как в книге
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
        final int color = data.get(position);
        view.setBackgroundColor(color);
        textView.setText(Integer.toHexString(color).substring(2));
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                Toast.makeText(v.getContext(), "color =  " + Integer.toHexString(color), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
