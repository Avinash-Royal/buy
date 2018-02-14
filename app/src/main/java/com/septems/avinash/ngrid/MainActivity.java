package com.septems.avinash.ngrid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.transition.ChangeBounds;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;

import com.septems.avinash.ngrid.Assignment.AssignmentPost;
import com.septems.avinash.ngrid.Messaging.MessageOverview;
import com.septems.avinash.ngrid.Messaging.data.StaticConfig;
import com.septems.avinash.ngrid.Messaging.service.ServiceUtils;
import com.septems.avinash.ngrid.NewsFeed.ActivityPost;
import com.septems.avinash.ngrid.OrderForm.OrderPost;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static FragmentManager mFragmentManager;
    public static FragmentTransaction fragmentTransaction;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StaticConfig.signin = "false";
        ServiceUtils.updateUserStatus(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ServiceUtils.updateUserStatus(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Explode explode = new Explode();
        getWindow().setEnterTransition(explode);

        mFragmentManager = getSupportFragmentManager();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.setScrimColor(Color.TRANSPARENT);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemIconTintList(null);

        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.nav_Order, 0);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Slide slideTransition = new Slide(Gravity.RIGHT);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        Explode explode = new Explode();
        explode.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        ChangeBounds changeBoundsTransition = new ChangeBounds();
        changeBoundsTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        int id = item.getItemId();

        fragmentTransaction = MainActivity.mFragmentManager.beginTransaction();


//        if (id == R.id.nav_News) {
//            fragmentTransaction.replace(R.id.fragments, new ActivityPost()).commit();
//            getSupportActionBar().setTitle("News Feeds");
         if (id == R.id.nav_Order) {
            fragmentTransaction.replace(R.id.fragments, new OrderPost()).commit();
            getSupportActionBar().setTitle("Orders");
//        } else if (id == R.id.nav_message) {
//            fragmentTransaction.replace(R.id.fragments, new MessageOverview()).commit();
//            getSupportActionBar().setTitle("Messaging");
        } else if (id == R.id.nav_Settings) {
            fragmentTransaction.replace(R.id.fragments, new UserProfileFragment()).commit();
            getSupportActionBar().setTitle("Settings");
//        } else if (id == R.id.nav_Assignments) {
//            fragmentTransaction.replace(R.id.fragments, new AssignmentPost()).commit();
//            getSupportActionBar().setTitle("Assignments");
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

