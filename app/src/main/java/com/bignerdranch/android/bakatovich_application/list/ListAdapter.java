package com.bignerdranch.android.bakatovich_application.list;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.data.Item;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final List<Item> data;
    private final Context context;
    private final String TAG;

    public ListAdapter(@NonNull final List<Item> data, final Context context) {
        this.data = data;
        this.context = context;
        TAG = context.getString(R.string.title_list_adapter);
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new Holder.ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.ListHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.ListHolder listHolder, final int position) {
        final View view = listHolder.getImageView();
        final TextView textView = listHolder.getTitle();
        final int color = data.get(position).getColor();
        final String hexColor = Integer.toHexString(color).substring(2);
        ((GradientDrawable)view.getBackground()).setColor(color);
        textView.setText(hexColor);

        final View.OnClickListener snackBarOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.remove(position);
                ListAdapter.this.notifyDataSetChanged();
                if (Log.isLoggable(TAG, Log.INFO)) {
                    Log.i(TAG, context.getString(R.string.deleted_item) +
                            context.getString(R.string.position) + position + ", " + context.getString(R.string.color) + hexColor);
                }
            }
        };

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                Snackbar snackbar = Snackbar.make(v, R.string.delete_item, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.yes, snackBarOnClickListener)
                        .setDuration(5000);

                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        final String description;
                        switch (event) {
                            case Snackbar.Callback.DISMISS_EVENT_ACTION:
                                description = context.getString(R.string.action_click);
                                break;
                            case Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE:
                                description = context.getString(R.string.new_snack_bar);
                                break;
                            case Snackbar.Callback.DISMISS_EVENT_MANUAL:
                                description = context.getString(R.string.call_to_dismiss);
                                break;
                            case Snackbar.Callback.DISMISS_EVENT_SWIPE:
                                description = context.getString(R.string.swipe);
                                break;
                            case Snackbar.Callback.DISMISS_EVENT_TIMEOUT:
                                description = context.getString(R.string.timeout);
                                break;
                            default:
                                description = context.getString(R.string.unknown);
                                break;
                        }
                        if (Log.isLoggable(TAG, Log.INFO)) {
                            Log.i(TAG, context.getString(R.string.snack_bar_dismissed) + context.getString(R.string.cause) + description);
                        }
                    }
                });

                snackbar.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
