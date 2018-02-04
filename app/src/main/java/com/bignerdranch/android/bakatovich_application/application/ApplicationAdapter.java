package com.bignerdranch.android.bakatovich_application.application;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.data.Database;
import com.bignerdranch.android.bakatovich_application.data.Entry;
import com.bignerdranch.android.bakatovich_application.Holder;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final List<Entry> data;
    private final Context context;
    private final String TAG;
    private final String DATA_THEME;

    public ApplicationAdapter(@NonNull final List<Entry> data, final Context context, final String DATA_THEME) {
        this.data = data;
        this.context = context;
        this.DATA_THEME = DATA_THEME;
        TAG = context.getString(R.string.title_application_adapter);
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_item, parent, false);
        return new Holder.ApplicationHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.ApplicationHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.ApplicationHolder applicationHolder, final int position) {
        final View view = applicationHolder.getImageView();
        final TextView textView = applicationHolder.getTextView();
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

    private void showPopUpMenu(final View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.context_menu);
        String title = (String) popupMenu.getMenu().findItem(R.id.nav_launch_count).getTitle();
        title += data.get(position).getLaunched();
        popupMenu.getMenu().findItem(R.id.nav_launch_count).setTitle(title);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.nav_delete:
                        Intent intent = new Intent(Intent.ACTION_DELETE);
                        intent.setData(Uri.parse(DATA_THEME + ":" + data.get(position).getPackageName()));
                        view.getContext().startActivity(intent);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
