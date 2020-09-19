package com.r.indian_rivers.views.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.r.indian_rivers.BuildConfig;
import com.r.indian_rivers.R;
import com.r.indian_rivers.model.RiverSystem;
import com.r.indian_rivers.network.Api;
import com.r.indian_rivers.network.RetrofitInstance;
import com.r.indian_rivers.util.PrefManager;
import com.r.indian_rivers.views.alertdialog.AlertResponse;
import com.r.indian_rivers.views.alertdialog.ShowAlertDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class River_System extends AppCompatActivity {
    TextView mintro, mclassification;
    Button mtype1, mtype2;
    AdView adView;

    ArrayList<RiverSystem> result;

    ProgressDialog progressBar;
    LinearLayout msystem;
    LinearLayout adContainer;
    private NavigationView mNavigationView;
    private View mNavHeader;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private TextView mNavTitle;
    PrefManager mpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river__system);

        adView = new AdView(this, getResources().getString(R.string.River_System), AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);
        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback

            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        adView.loadAd();
        getSupportActionBar().setSubtitle("Drainage System");
        progressBar = new ProgressDialog(this);
        mNavigationView = findViewById(R.id.navigation_view);
        mintro = findViewById(R.id.introduction);
        msystem = findViewById(R.id.systemmain);
        mclassification = findViewById(R.id.classification);
        mtype1 = findViewById(R.id.type1);
        mtype2 = findViewById(R.id.type2);
        mpref = new PrefManager(this);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        fetchData();

        mtype1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(River_System.this, Himalayan.class);
                i.putExtra("Himalayan", result.get(0).getHimalayan());
                i.putExtra("Ganga", result.get(0).getGanga());
                i.putExtra("Brahmaputra", result.get(0).getBrahmaputra());
                i.putExtra("Indus", result.get(0).getIndus());
                startActivity(i);
            }
        });
        mtype2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(River_System.this, DetailPage.class);
                i.putExtra("detail", result.get(0).getPeninsular());
                startActivity(i);
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
            View alertLayout = inflater.inflate(R.layout.river_system_alert, null);

            AdView adView = new AdView(this, getResources().getString(R.string.fbAd_langalert), AdSize.BANNER_HEIGHT_50);
            // Find the Ad Container
            LinearLayout adContainer = (LinearLayout) alertLayout.findViewById(R.id.banner_container);

            adContainer.addView(adView);
            adView.loadAd();

            AlertResponse response = new AlertResponse() {
                @Override
                public void onPositiveClick(String message) {
                    River_System.this.finish();
                }
            };

            ShowAlertDialog dialog = new ShowAlertDialog(River_System.this, alertLayout, response);
            dialog.showExitAlert();
        }
    }

    private void fetchData() {
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Data Loading ...");
        progressBar.show();//displays the progress bar

        result = new ArrayList<RiverSystem>();
        result.clear();

        Api api = RetrofitInstance.getApi();

        Call<List<RiverSystem>> call = null;

        if (mpref.getLang().equals("Hindi")) {
            call = api.getHindiRiversSystem();
        } else {
            call = api.getEngRiversSystem();
        }

        call.enqueue(new Callback<List<RiverSystem>>() {
            @Override
            public void onResponse(Call<List<RiverSystem>> call, Response<List<RiverSystem>> response) {
                List<RiverSystem> riv = response.body();

                result.addAll(riv);

                setData(result);
                progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<List<RiverSystem>> call, Throwable t) {
                Snackbar.make(adContainer, "Something went wrong", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fetchData();
                            }
                        }).show();
                progressBar.dismiss();
            }
        });
    }

    public void setData(ArrayList<RiverSystem> result) {
        msystem.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mintro.setText(Html.fromHtml(result.get(0).getIntroduction(), Html.FROM_HTML_MODE_COMPACT));
            mclassification.setText(Html.fromHtml(result.get(0).getClassification(), Html.FROM_HTML_MODE_COMPACT));
            mtype1.setText(Html.fromHtml(result.get(0).getType1(), Html.FROM_HTML_MODE_COMPACT));
            mtype2.setText(Html.fromHtml(result.get(0).getType2(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            mintro.setText(Html.fromHtml(result.get(0).getIntroduction()));
            mclassification.setText(Html.fromHtml(result.get(0).getClassification()));
            mtype1.setText(Html.fromHtml(result.get(0).getType1()));
            mtype2.setText(Html.fromHtml(result.get(0).getType2()));
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
                        startActivity(new Intent(River_System.this, Map.class));

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
