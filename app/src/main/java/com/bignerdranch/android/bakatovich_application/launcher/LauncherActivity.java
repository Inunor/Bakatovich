package com.bignerdranch.android.bakatovich_application.launcher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bignerdranch.android.bakatovich_application.MainActivity;
import com.bignerdranch.android.bakatovich_application.R;
import com.bignerdranch.android.bakatovich_application.data.Database;
import com.bignerdranch.android.bakatovich_application.settings.SettingsActivity;
import com.bignerdranch.android.bakatovich_application.settings.SettingsFragment;


public class LauncherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    Fragment fragment;
    Fragment gridFragment;
    Fragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(SettingsFragment.getTheme(this));
        super.onCreate(savedInstanceState);
        Database.initialize(this);
        setContentView(R.layout.activity_launcher);
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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });

        gridFragment = new GridFragment();
        listFragment = new ListFragment();

        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.drawer_layout_launcher);

        if (fragment == null) {
            fragment = gridFragment;
            fragmentManager.beginTransaction()
                    .add(R.id.drawer_layout_launcher, fragment)
                    .commit();
        }
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
        Intent intent;
        int id = item.getItemId();

        if (id == R.id.nav_grid) {
            fragmentManager.beginTransaction()
                    .replace(R.id.drawer_layout_launcher, gridFragment)
                    .commit();
        } else if (id == R.id.nav_list) {
            fragmentManager.beginTransaction()
                    .replace(R.id.drawer_layout_launcher, listFragment)
                    .commit();
        }
        if (id == R.id.nav_settings) {
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_launcher);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
