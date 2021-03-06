package com.r.indian_rivers.views.activity;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.r.indian_rivers.BuildConfig;
import com.r.indian_rivers.R;
import com.r.indian_rivers.util.PrefManager;
import com.r.indian_rivers.views.alertdialog.AlertResponse;
import com.r.indian_rivers.views.alertdialog.ShowAlertDialog;
import com.r.indian_rivers.views.fragment.EMainList;
import com.r.indian_rivers.views.fragment.HMainList;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private View mNavHeader;
    private String mActivityTitle;
    BottomNavigationView navigation;
    private PrefManager prefManager;
    public static String lang = "";
    private TextView mNavTitle;

    int selectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setSubtitle("Notes");

        prefManager = new PrefManager(this);

        mNavigationView = findViewById(R.id.navigation_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        if (prefManager.getLang().equalsIgnoreCase("Hindi")) {
            MainActivity.lang = "Hindi";
            getSupportFragmentManager().beginTransaction().replace(R.id.activity, new HMainList()).commit();
            navigation.setSelectedItemId(R.id.navigation_hindi);
        } else {
            MainActivity.lang = "English";
            getSupportFragmentManager().beginTransaction().replace(R.id.activity, new EMainList()).commit();
            navigation.setSelectedItemId(R.id.navigation_english);
        }

        selectedId = navigation.getSelectedItemId();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager manager = getSupportFragmentManager();
                if (item.getItemId() == R.id.navigation_english && selectedId != item.getItemId()) {
                    MainActivity.lang = "English";
                    manager.beginTransaction().replace(R.id.activity, new EMainList()).commit();
                } else if (item.getItemId() == R.id.navigation_hindi && selectedId != item.getItemId()) {
                    MainActivity.lang = "Hindi";
                    manager.beginTransaction().replace(R.id.activity, new HMainList()).commit();
                }
                selectedId = item.getItemId();
                return true;
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        setupDrawer();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.malertad, null);
            AdView adView = alertLayout.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);

            AlertResponse response = new AlertResponse() {
                @Override
                public void onPositiveClick(String message) {
                    MainActivity.this.finish();
                }
            };

            ShowAlertDialog dialog = new ShowAlertDialog(MainActivity.this, alertLayout, response);
            dialog.showExitAlert();
        }
    }

    private void setupDrawer() {
        mActivityTitle = getTitle().toString();
        mNavHeader = mNavigationView.getHeaderView(0);
        mNavTitle = mNavHeader.findViewById(R.id.name);

        mNavTitle.setText(mActivityTitle);
        mNavigationView.bringToFront();
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_rate:
                        Uri uri = Uri.parse("market://details?id=" + getApplication().getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplication().getPackageName())));
                        }
                        break;
                    case R.id.nav_share:
                        try {
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                            String shareMessage = "\nDownload the Application\n\n";
                            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                            startActivity(Intent.createChooser(shareIntent, "choose one"));
                        } catch (Exception e) {
                            //e.toString();
                        }
                        break;
                    case R.id.nav_map:
                        startActivity(new Intent(MainActivity.this, Map.class));

                    default:
                        break;
                }

                mDrawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }

            ;
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }
}
