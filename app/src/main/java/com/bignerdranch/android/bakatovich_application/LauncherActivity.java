package com.bignerdranch.android.bakatovich_application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bignerdranch.android.bakatovich_application.data.DataStorage;
import com.bignerdranch.android.bakatovich_application.data.Item;
import com.bignerdranch.android.bakatovich_application.launcher.LauncherAdapter;
import com.bignerdranch.android.bakatovich_application.launcher.OffsetItemDecoration;


public class LauncherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LauncherAdapter launcherAdapter;
    private DataStorage dataStorage;
    private String TAG;
    private final static String IS_STANDARD_LAYOUT = "is_standard_layout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        TAG = getString(R.string.title_activity_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_launcher);
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
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                //        | Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });


        dataStorage = DataStorage.get();
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = dataStorage.pushFront();
                launcherAdapter.notifyDataSetChanged();
                if (Log.isLoggable(TAG, Log.INFO)) {
                    Log.i(TAG, getString(R.string.add_new_item) + item.getColor());
                }
            }
        });

        createGridLayout();
    }

    private void createGridLayout() {
        final RecyclerView recyclerView = findViewById(R.id.launcher_content);
        recyclerView.setHasFixedSize(true);
        final int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        // Возможно скоро заменить на sharedPreferences
        boolean isStandard = getIntent().getBooleanExtra(IS_STANDARD_LAYOUT, true);
        final int spanCount = getResources().getInteger(isStandard? R.integer.standard_count : R.integer.dense_count);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(layoutManager);

        launcherAdapter = new LauncherAdapter(dataStorage.getData(), getApplicationContext());
        recyclerView.setAdapter(launcherAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_launcher);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_launcher);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
