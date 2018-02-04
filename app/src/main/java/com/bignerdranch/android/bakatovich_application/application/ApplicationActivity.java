package com.bignerdranch.android.bakatovich_application.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bignerdranch.android.bakatovich_application.MainActivity;
import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.data.Database;
import com.bignerdranch.android.bakatovich_application.data.Entry;
import com.bignerdranch.android.bakatovich_application.launcher.LauncherActivity;
import com.bignerdranch.android.bakatovich_application.launcher.OffsetItemDecoration;
import com.bignerdranch.android.bakatovich_application.list.ListActivity;
import com.bignerdranch.android.bakatovich_application.settings.SettingsActivity;
import com.bignerdranch.android.bakatovich_application.settings.SettingsFragment;

import java.util.ArrayList;
import java.util.List;


public class ApplicationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Entry> data = new ArrayList<>();
    private ApplicationAdapter applicationAdapter;
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
                applicationAdapter.notifyDataSetChanged();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(SettingsFragment.getTheme(this));
        super.onCreate(savedInstanceState);
        Database.initialize(this);
        setContentView(R.layout.activity_application);
        TAG = getString(R.string.title_activity_application);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_application);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final View navigationHeaderView = navigationView.getHeaderView(0);
        final View profileImage = navigationHeaderView.findViewById(R.id.avatar);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ApplicationActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });

        createGridLayout();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme(DATA_THEME);
        registerReceiver(monitor, intentFilter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(monitor);
        for (Entry entry : data) {
            Database.insertOrUpdate(entry);
        }
    }

    private void createGridLayout() {
        final RecyclerView recyclerView = findViewById(R.id.application_content);
        recyclerView.setHasFixedSize(true);
        final int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        final int spanCount = getResources().getInteger(SettingsFragment.getLayoutColumn(this));
        final GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(layoutManager);

        generateData();
        applicationAdapter = new ApplicationAdapter(data, getApplicationContext(), DATA_THEME);
        recyclerView.setAdapter(applicationAdapter);
    }

    public void generateData() {
        PackageManager packageManager = getPackageManager();
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
        final PackageManager packageManager = getPackageManager();
        final String name = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        final long updatedTime = packageManager.getPackageInfo(packageName, 0).lastUpdateTime;
        final Drawable icon = packageManager.getApplicationIcon(packageName);
        return new Entry(name, packageName, updatedTime, icon);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_application);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();

        if (id == R.id.nav_grid) {
            intent = new Intent(this, LauncherActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_list) {
            intent = new Intent(this, ListActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_settings) {
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_application);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
