package com.bignerdranch.android.bakatovich_application.launcher;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bignerdranch.android.bakatovich_application.MainActivity;
import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.data.Database;
import com.bignerdranch.android.bakatovich_application.data.Entry;
import com.bignerdranch.android.bakatovich_application.data.EntryStorage;
import com.bignerdranch.android.bakatovich_application.settings.SettingsActivity;
import com.bignerdranch.android.bakatovich_application.settings.SettingsFragment;

import java.util.Collections;


public class LauncherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static RecyclerView.Adapter launcherAdapter;
    private final String DATA_THEME = "package";

    private BroadcastReceiver monitor = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case Intent.ACTION_PACKAGE_ADDED:
                        appAdded(intent);
                        break;
                    case Intent.ACTION_PACKAGE_REMOVED:
                        appRemoved(intent);
                        break;
                    default:
                        return;
                }
                Collections.sort(EntryStorage.getData(), SettingsFragment.getSortMethod(LauncherActivity.this));
                if (launcherAdapter != null) {
                    launcherAdapter.notifyDataSetChanged();
                }
            }
        }

        private void appAdded(final Intent intent) {
            String packageName = Uri.parse(intent.getDataString()).getSchemeSpecificPart();
            try {
                Entry entry = EntryStorage.getEntryFromPackageName(packageName, LauncherActivity.this);
                EntryStorage.addEntry(entry);
                Database.insertOrUpdate(entry);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void appRemoved(final Intent intent) {
            for (Entry entry : EntryStorage.getData()) {
                String packageName = Uri.parse(intent.getDataString()).getSchemeSpecificPart();
                if (packageName.equals(entry.getPackageName())) {
                    EntryStorage.removeEntry(entry);
                    Database.remove(entry);
                    break;
                }
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(SettingsFragment.getTheme(this));
        super.onCreate(savedInstanceState);
        Database.initialize(this);
        setContentView(R.layout.activity_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        EntryStorage.generateData(this);
        Collections.sort(EntryStorage.getData(), SettingsFragment.getSortMethod(LauncherActivity.this));
        if (savedInstanceState == null) {
            setGridLayoutFragment();
        }
    }

    private void setGridLayoutFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        GridFragment fragment = new GridFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }

    private void setListLayoutFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ListFragment fragment = new ListFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addDataScheme(DATA_THEME);
        registerReceiver(monitor, intentFilter);

    }

    @Override
    public void onStop() {
        super.onStop();
        //unregisterReceiver(monitor);
        for (Entry entry : EntryStorage.getData()) {
            Database.insertOrUpdate(entry);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            setGridLayoutFragment();
        } else if (id == R.id.nav_list) {
            setListLayoutFragment();
        } else if (id == R.id.nav_settings) {
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
