package com.r.indian_rivers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Suggestions extends AppCompatActivity {
    ProgressDialog progressBar;
    AdView adView;
    TextInputEditText memail,msug;
    Button btn;
  /*  AdRequest adRequest;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        getSupportActionBar().setSubtitle("Suggestion");
    /*    mAdView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        adView = new AdView(this, getResources().getString(R.string.fbAd_suggestionfragment), AdSize.BANNER_HEIGHT_50);

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
        memail=findViewById(R.id.from);
        msug=findViewById(R.id.suggestion);
        btn=findViewById(R.id.button);
        progressBar = new ProgressDialog(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String from=memail.getText().toString();

                String suggestion=msug.getText().toString();
                if(from.isEmpty()|| suggestion.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please enter your email id and sugeestion", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendRequest(from, suggestion);

                    progressBar.setCancelable(false);//you can cancel it by pressing back button
                    progressBar.setMessage("Mail sending...");
                    progressBar.show();
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

    private void sendRequest(final String from, final String suggestion){


        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://ritikanegi.com/one.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.dismiss();
                        memail.setText("");
                        msug.setText("");
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.thanku,  (ViewGroup)findViewById(R.id.custom_toast_container));
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.TOP, 0, 40);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        progressBar.dismiss();
                    }

                })
        {
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("from",from);
                params.put("suggetion", suggestion);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}
