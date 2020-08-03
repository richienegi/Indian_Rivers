package com.r.indian_rivers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;


public class MainOption extends AppCompatActivity {
    String lang="";

   PrefManager prefManager;
   AdView adView;
    /*AdView mAdView;
    AdRequest adRequest;*/
TextView mmainlanguage,mr_system_text,mnotes_text,mquiz_text,msuggesion_text;
CardView mriver_system,mnotes,mquiz,msuggestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_option);
       /* mAdView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        adView = new AdView(this, getResources().getString(R.string.mainOptionsAd), AdSize.BANNER_HEIGHT_50);

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

        prefManager = new PrefManager(this);
        getSupportActionBar().hide();
        mriver_system=findViewById(R.id.river_system);
        mnotes=findViewById(R.id.notes);
        mquiz=findViewById(R.id.quiz);
        msuggestion=findViewById(R.id.suggestion);
        mmainlanguage=findViewById(R.id.setMainlanguage);
        mr_system_text=findViewById(R.id.r_system_text);
        mnotes_text=findViewById(R.id.notes_text);
        mquiz_text=findViewById(R.id.quiz_text);
        msuggesion_text=findViewById(R.id.suggestion_text);

        if(!prefManager.getLang().isEmpty()) {
            if (prefManager.getLang().equals("Hindi")) {
                mr_system_text.setText(getResources().getText(R.string.hriver_system));
                mnotes_text.setText(getResources().getText(R.string.hnotes));
                mquiz_text.setText(getResources().getText(R.string.hquiz));
                msuggesion_text.setText(getResources().getText(R.string.hsuggestion));
            }
        }
        mmainlanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
        mriver_system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainOption.this,River_System.class);
                startActivity(i);
            }
        });

        mnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainOption.this,MainActivity.class);
                startActivity(i);
            }
        });
        mquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i=new Intent(MainOption.this,Questions.class);
            startActivity(i);
            }
        });
        msuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainOption.this,Suggestions.class);
                startActivity(i);
            }
        });
    }


    private void showAlertDialog() {

        /*LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.halertad, null);
        AdView adView = alertLayout.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
*/
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
       /* alertDialog.setView(alertLayout);*/
        alertDialog.setTitle("Default Language :");
        String[] items = {"Hindi","English"};
        int checkedItem = 1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        lang="Hindi";
                        break;
                    case 1:
                        lang="English";
                        break;
                }
            }
        });
        alertDialog.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        prefManager.setLang(lang);

                       Intent i = new Intent(MainOption.this, MainOption.class);  //your class
                        startActivity(i);
                        finish();
                        Toast.makeText(MainOption.this,lang +" is set as your default language", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }


}
