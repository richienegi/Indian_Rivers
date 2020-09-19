package com.r.indian_rivers.views.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.r.indian_rivers.R;
import com.r.indian_rivers.util.PrefManager;

public class Himalayan extends AppCompatActivity {
    TextView mhimal;
    Button mganga, mbrah, mindus;
    PrefManager mpre;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_himalayan);
        adView = new AdView(this, getResources().getString(R.string.Himalyan_River), AdSize.BANNER_HEIGHT_50);

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


        mhimal = findViewById(R.id.himalyan);
        mganga = findViewById(R.id.ganga);
        mbrah = findViewById(R.id.bharm);
        mindus = findViewById(R.id.indus);
        String him = getIntent().getStringExtra("Himalayan");
        final String gang = getIntent().getStringExtra("Ganga");
        final String barh = getIntent().getStringExtra("Brahmaputra");
        final String ind = getIntent().getStringExtra("Indus");
        mpre = new PrefManager(this);


        if (mpre.getLang().equalsIgnoreCase("Hindi")) {
            mindus.setText(getResources().getString(R.string.hriver1));
            mganga.setText(getResources().getString(R.string.hriver2));
            mbrah.setText(getResources().getString(R.string.hriver3));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mhimal.setText(Html.fromHtml(him, Html.FROM_HTML_MODE_COMPACT));
        } else {
            mhimal.setText(Html.fromHtml(him));
        }

        mganga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetailpage(gang);
            }
        });
        mbrah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetailpage(barh);
            }
        });

        mindus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetailpage(ind);
            }
        });
    }

    public void getDetailpage(String data) {
        /*FragmentManager manager = getSupportFragmentManager();
        Bundle b=new Bundle();
        b.putString("detail",data);
        manager.beginTransaction().replace(R.id.mainHim, h).commit();
        h.setArguments(b);*/
        Intent i = new Intent(Himalayan.this, DetailPage.class);
        i.putExtra("detail", data);
        startActivity(i);
    }
}
