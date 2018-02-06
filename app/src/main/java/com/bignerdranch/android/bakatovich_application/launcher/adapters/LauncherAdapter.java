package com.bignerdranch.android.bakatovich_application.launcher.adapters;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.data.Entry;

import java.util.List;


public abstract class LauncherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    protected final List<Entry> data;
    protected final Activity activity;
    private final String DATA_THEME = "package";

    public LauncherAdapter(@NonNull final List<Entry> data, final Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    protected void showPopUpMenu(final View view, final int position) {
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
                    case R.id.info:
                        intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
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
