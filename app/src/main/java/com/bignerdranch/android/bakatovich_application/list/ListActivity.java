package com.bignerdranch.android.bakatovich_application.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bignerdranch.android.bakatovich_application.application.ApplicationActivity;
import com.bignerdranch.android.bakatovich_application.launcher.LauncherActivity;
import com.bignerdranch.android.bakatovich_application.MainActivity;
import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.data.DataStorage;
import com.bignerdranch.android.bakatovich_application.data.Item;
import com.bignerdranch.android.bakatovich_application.launcher.OffsetItemDecoration;
import com.bignerdranch.android.bakatovich_application.settings.SettingsActivity;
import com.bignerdranch.android.bakatovich_application.settings.SettingsFragment;


public class ListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListAdapter listAdapter;
    private DataStorage dataStorage;
    private String TAG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(SettingsFragment.getTheme(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        TAG = getString(R.string.title_activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_list);
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
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });


        dataStorage = DataStorage.get();
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = dataStorage.pushFront();
                listAdapter.notifyDataSetChanged();
                if (Log.isLoggable(TAG, Log.INFO)) {
                    Log.i(TAG, getString(R.string.add_new_item) + item.getColor());
                }
            }
        });

        createLinearLayout();
    }

    public void createLinearLayout() {
        final RecyclerView recyclerView = findViewById(R.id.list_content);
        recyclerView.setHasFixedSize(true);
        final int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new ListAdapter(dataStorage.getData(), getApplicationContext());
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_list);
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
        } else if (id == R.id.nav_application_grid) {
            intent = new Intent(this, ApplicationActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_settings) {
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_list);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
