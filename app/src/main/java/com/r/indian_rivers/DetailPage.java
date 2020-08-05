package com.r.indian_rivers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;


public class DetailPage extends AppCompatActivity {
TextView mdeatil;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        adView = new AdView(this, getResources().getString(R.string.Detail_page), AdSize.BANNER_HEIGHT_50);

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

        mdeatil=findViewById(R.id.detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mdeatil.setText(Html.fromHtml(getIntent().getStringExtra("detail"),Html.FROM_HTML_MODE_COMPACT));
        } else {
            mdeatil.setText(Html.fromHtml(getIntent().getStringExtra("detail")));
        }
    }



}
