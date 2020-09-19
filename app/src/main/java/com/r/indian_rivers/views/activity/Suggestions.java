package com.r.indian_rivers.views.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.r.indian_rivers.R;
import com.r.indian_rivers.network.Api;
import com.r.indian_rivers.network.RetrofitInstance;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Suggestions extends AppCompatActivity {
    ProgressDialog progressBar;
    AdView adView;
    TextInputEditText memail, msug;
    Button btn;
    LinearLayout adContainer;

    /*  AdRequest adRequest;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        getSupportActionBar().setTitle("Suggestion");
    /*    mAdView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        adView = new AdView(this, getResources().getString(R.string.fbAd_suggestionfragment), AdSize.BANNER_HEIGHT_50);

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

        memail = findViewById(R.id.from);
        msug = findViewById(R.id.suggestion);
        btn = findViewById(R.id.button);
        progressBar = new ProgressDialog(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String from = memail.getText().toString();

                String suggestion = msug.getText().toString();
                if (from.isEmpty() || suggestion.isEmpty()) {
                    Snackbar.make(adContainer, "Please enter your email id and suggestion", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(from).matches()) {
                        memail.setError("Email Address is not Valid", getDrawable(R.drawable.ic_error));
                        memail.requestFocus();
                        return;
                    }
                    sendRequest(from, suggestion);
                }
            }
        });
        TimerTask tt = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        adView.loadAd();
                    }
                });

            }
        };

        Timer t = new Timer();
        t.scheduleAtFixedRate(tt, 0, 1000 * 30);

    }

    private void sendRequest(final String from, final String suggestion) {
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Mail sending...");
        progressBar.show();

        Api api = RetrofitInstance.getApi();

        Call<ResponseBody> responseCall = api.sendSuggestion(from, suggestion);

        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                progressBar.dismiss();
                memail.setText("");
                msug.setText("");
                Snackbar.make(adContainer, getString(R.string.thanku), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
