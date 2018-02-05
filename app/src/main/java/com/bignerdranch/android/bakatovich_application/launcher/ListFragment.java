package com.bignerdranch.android.bakatovich_application.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.bakatovich_application.OffsetItemDecoration;
import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.data.Database;
import com.bignerdranch.android.bakatovich_application.data.Entry;
import com.bignerdranch.android.bakatovich_application.launcher.adapters.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private List<Entry> data = new ArrayList<>();
    private ListAdapter listAdapter;
    private String TAG;
    private final String DATA_THEME = "package";
    private BroadcastReceiver monitor = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case Intent.ACTION_PACKAGE_ADDED:
                        appAdded(context, intent);
                        break;
                    case Intent.ACTION_PACKAGE_REMOVED:
                        appRemoved(context, intent);
                        break;
                    default:
                        return;
                }
                listAdapter.notifyDataSetChanged();
            }
        }

        private void appAdded(final Context context, final Intent intent) {
            String packageName = Uri.parse(intent.getDataString()).getSchemeSpecificPart();
            try {
                Entry entry = getEntryFromPackageName(packageName);
                data.add(entry);
                Database.insertOrUpdate(entry);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void appRemoved(final Context context, final Intent intent) {
            for (Entry entry : data) {
                String packageName = Uri.parse(intent.getDataString()).getSchemeSpecificPart();
                if (packageName.equals(entry.getPackageName())) {
                    data.remove(entry);
                    Database.remove(entry);
                    break;
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createListLayout();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme(DATA_THEME);
        getContext().registerReceiver(monitor, intentFilter);

    }

    @Override
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(monitor);
        for (Entry entry : data) {
            Database.insertOrUpdate(entry);
        }
    }

    private void createListLayout() {
        final RecyclerView recyclerView = getActivity().findViewById(R.id.launcher_content);
        recyclerView.setHasFixedSize(true);
        final int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new OffsetItemDecoration(offset));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        generateData();
        listAdapter = new ListAdapter(data, getContext(), DATA_THEME);
        recyclerView.setAdapter(listAdapter);
    }

    public void generateData() {
        PackageManager packageManager = getContext().getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(0);
        for (ApplicationInfo applicationInfo : applicationInfoList) {
            try {
                // почитать что-нибудь про getLaunchIntentPackage()
                if (packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null) {
                    Entry entry = getEntryFromPackageName(applicationInfo.packageName);
                    if (!data.contains(entry)) {
                        data.add(entry);
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private Entry getEntryFromPackageName(final String packageName) throws PackageManager.NameNotFoundException {
        final PackageManager packageManager = getContext().getPackageManager();
        final String name = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        final long updatedTime = packageManager.getPackageInfo(packageName, 0).lastUpdateTime;
        final Drawable icon = packageManager.getApplicationIcon(packageName);
        return new Entry(name, packageName, updatedTime, icon);
    }
}
