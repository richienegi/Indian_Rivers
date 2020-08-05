package com.r.indian_rivers;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
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
import android.widget.Toast;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class River_System extends AppCompatActivity {
TextView mintro,mclassification;
Button mtype1,mtype2;
    AdView adView;

    ArrayList<RiverSystemclass> result;
    ProgressDialog progressBar;
    LinearLayout msystem;
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
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

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
        mintro=findViewById(R.id.introduction);
        msystem=findViewById(R.id.systemmain);
        mclassification=findViewById(R.id.classification);
        mtype1=findViewById(R.id.type1);
        mtype2=findViewById(R.id.type2);
        mpref=new PrefManager(this);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        if(mpref.getLang().equals("Hindi"))
        {
            Hdemo();
        }
        else {
            Edemo();
        }

    mtype1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(River_System.this,Himalayan.class);
            i.putExtra("Himalayan",result.get(0).Himalayan);
            i.putExtra("Ganga",result.get(0).Ganga);
            i.putExtra("Brahmaputra",result.get(0).Brahmaputra);
            i.putExtra("Indus",result.get(0).Indus);
            startActivity(i);
        }
    });
        mtype2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(River_System.this,DetailPage.class);
                i.putExtra("detail",result.get(0).Peninsular);
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
        }
        else {
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.river_system_alert, null);

           AdView adView = new AdView(this, getResources().getString(R.string.fbAd_langalert), AdSize.BANNER_HEIGHT_50);

            // Find the Ad Container
            LinearLayout adContainer = (LinearLayout) alertLayout.findViewById(R.id.banner_container);

            // Add the ad view to your activity layout
            adContainer.addView(adView);
            adView.loadAd();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(alertLayout);
            builder.setMessage("Are you sure you want to exit ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            River_System.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }


    public void Edemo()
    {
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Data Loading ...");
        progressBar.show();//displays the progress bar

        result = new ArrayList<RiverSystemclass>();
        result.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Eriver.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Eriver api = retrofit.create(Eriver.class);
        Call<List<RiverSystem>> call  = api.getRivers();
        call.enqueue(new Callback<List<RiverSystem>>() {
            @Override
            public void onResponse(Call<List<RiverSystem>> call, Response<List<RiverSystem>> response) {
                List<RiverSystem> riv= response.body();

                for (int i = 0; i < riv.size(); i++) {
                    RiverSystemclass ci = new RiverSystemclass();
                    ci.introduction =riv.get(i).getIntroduction();
                    ci.classification =riv.get(i).getClassification();
                    ci.type1 =riv.get(i).getType1();
                    ci.type2 =riv.get(i).getType2();
                    ci.Himalayan=riv.get(i).getHimalayan();
                    ci.Indus=riv.get(i).getIndus();
                    ci.Ganga=riv.get(i).getGanga();
                    ci.Brahmaputra=riv.get(i).getBrahmaputra();
                    ci.Peninsular=riv.get(i).getPeninsular();
                    result.add(ci);
                }

setData(result);
                progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<List<RiverSystem>> call, Throwable t) {
                Toast.makeText(River_System.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.dismiss();
            }
        });
    }

    public void Hdemo()
    {
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Data Loading ...");
        progressBar.show();//displays the progress bar

        result = new ArrayList<RiverSystemclass>();
        result.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Hriver.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Hriver api = retrofit.create(Hriver.class);
        Call<List<RiverSystem>> call  = api.getRivers();
        call.enqueue(new Callback<List<RiverSystem>>() {
            @Override
            public void onResponse(Call<List<RiverSystem>> call, Response<List<RiverSystem>> response) {
                List<RiverSystem> riv= response.body();

                for (int i = 0; i < riv.size(); i++) {
                    RiverSystemclass ci = new RiverSystemclass();
                    ci.introduction =riv.get(i).getIntroduction();
                    ci.classification =riv.get(i).getClassification();
                    ci.type1 =riv.get(i).getType1();
                    ci.type2 =riv.get(i).getType2();
                    ci.Himalayan=riv.get(i).getHimalayan();
                    ci.Indus=riv.get(i).getIndus();
                    ci.Ganga=riv.get(i).getGanga();
                    ci.Brahmaputra=riv.get(i).getBrahmaputra();
                    ci.Peninsular=riv.get(i).getPeninsular();
                    result.add(ci);
                }
                setData(result);
                progressBar.dismiss();

            }

            @Override
            public void onFailure(Call<List<RiverSystem>> call, Throwable t) {
                Toast.makeText(River_System.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.dismiss();
            }
        });
    }


    public void setData(ArrayList<RiverSystemclass> result)
    {
        msystem.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mintro.setText(Html.fromHtml(result.get(0).introduction,Html.FROM_HTML_MODE_COMPACT));
        } else {
            mintro.setText(Html.fromHtml(result.get(0).introduction));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mclassification.setText(Html.fromHtml(result.get(0).classification,Html.FROM_HTML_MODE_COMPACT));
        } else {
            mclassification.setText(Html.fromHtml(result.get(0).classification));
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mtype1.setText(Html.fromHtml(result.get(0).type1,Html.FROM_HTML_MODE_COMPACT));
        } else {
            mtype1.setText(Html.fromHtml(result.get(0).type1));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mtype2.setText(Html.fromHtml(result.get(0).type2,Html.FROM_HTML_MODE_COMPACT));
        } else {
            mtype2.setText(Html.fromHtml(result.get(0).type2));
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
                            String shareMessage= "\nDownload the Application\n\n";
                            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                            startActivity(Intent.createChooser(shareIntent, "choose one"));
                        } catch(Exception e) {
                            //e.toString();
                        }
                    case R .id.nav_map:
                        startActivity(new Intent(River_System.this,Map.class));

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
