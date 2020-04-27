package com.r.indian_rivers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class DetailActivity extends AppCompatActivity {
    protected TextView msource;
    protected TextView mlength;
    protected TextView mdrain;
    protected TextView mname;
    TextView mtributeries,mabout,mleft,mright,mdam,mmyth;
CardView ma,ml,mr,md,mm;
AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        msource = (TextView) findViewById(R.id.source);
        mlength = (TextView) findViewById(R.id.length);
        mdrain = (TextView) findViewById(R.id.drain);
       /* mname = (TextView) findViewById(R.id.Name);*/
        mtributeries = (TextView) findViewById(R.id.tributeries);
        mabout = (TextView) findViewById(R.id.about);
        mleft = (TextView) findViewById(R.id.left);
        mright = (TextView) findViewById(R.id.right);
        mdam = (TextView) findViewById(R.id.dam);
        mmyth = (TextView) findViewById(R.id.mythology);
        ma = (CardView) findViewById(R.id.card_about);
        ml = (CardView) findViewById(R.id.card_left);
        mr = (CardView) findViewById(R.id.card_right);
        md = (CardView) findViewById(R.id.card_dam);
        mm = (CardView) findViewById(R.id.card_mythology);
        Intent intent = getIntent();
      /*  mname.setText(intent.getStringExtra("name"));*/
        this.getSupportActionBar().setTitle(intent.getStringExtra("name"));
        msource.setText(intent.getStringExtra("source"));
        mlength.setText(intent.getStringExtra("length"));
        mdrain.setText(intent.getStringExtra("drain"));
        if (!getIntent().getStringExtra("summary").isEmpty()) {
mtributeries.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mtributeries.setText(Html.fromHtml(intent.getStringExtra("summary"),Html.FROM_HTML_MODE_COMPACT));
            } else {
                mtributeries.setText(Html.fromHtml(intent.getStringExtra("summary")));
            }
        }
        if (!getIntent().getStringExtra("about").isEmpty() && getIntent().getStringExtra("about")!= null) {
            ma.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mabout.setText(Html.fromHtml(intent.getStringExtra("about"),Html.FROM_HTML_MODE_COMPACT));
            } else {
                mabout.setText(Html.fromHtml(intent.getStringExtra("about")));
            }
        }
        if (!getIntent().getStringExtra("left_tributaries").isEmpty()&& getIntent().getStringExtra("left_tributaries")!=null ) {
            ml.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mleft.setText(Html.fromHtml(intent.getStringExtra("left_tributaries"),Html.FROM_HTML_MODE_COMPACT));
            } else {
                mleft.setText(Html.fromHtml(intent.getStringExtra("left_tributaries")));
            }

        }
        if (!getIntent().getStringExtra("right_tributaries").isEmpty() && intent.getStringExtra("right_tributaries")!=null) {
            mr.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mright.setText(Html.fromHtml(intent.getStringExtra("right_tributaries"),Html.FROM_HTML_MODE_COMPACT));
            } else {
                mright.setText(Html.fromHtml(intent.getStringExtra("right_tributaries")));
            }

        }
        if (!getIntent().getStringExtra("dam").isEmpty()&& getIntent().getStringExtra("dam")!=null) {
            md.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mdam.setText(Html.fromHtml(intent.getStringExtra("dam"),Html.FROM_HTML_MODE_COMPACT));
            } else {
                mdam.setText(Html.fromHtml(intent.getStringExtra("dam")));
            }

        }
        if (!getIntent().getStringExtra("mythology").isEmpty() && getIntent().getStringExtra("mythology")!=null && getIntent().getStringExtra("mythology").trim().length()!=0) {
            mm.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mmyth.setText(Html.fromHtml(intent.getStringExtra("mythology"),Html.FROM_HTML_MODE_COMPACT));
            } else {
                mmyth.setText(Html.fromHtml(intent.getStringExtra("mythology")));
            }

        }
    }
    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
