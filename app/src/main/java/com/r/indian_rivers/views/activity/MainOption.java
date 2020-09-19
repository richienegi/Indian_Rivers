package com.r.indian_rivers.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.material.snackbar.Snackbar;
import com.r.indian_rivers.R;
import com.r.indian_rivers.util.PrefManager;
import com.r.indian_rivers.views.alertdialog.AlertResponse;
import com.r.indian_rivers.views.alertdialog.ShowAlertDialog;


public class MainOption extends AppCompatActivity {

    PrefManager prefManager;
    AdView adView;

    TextView mmainlanguage, mr_system_text, mnotes_text, mquiz_text, msuggesion_text;
    CardView mriver_system, mnotes, mquiz, msuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_option);
        adView = new AdView(this, getResources().getString(R.string.mainOptionsAd), AdSize.BANNER_HEIGHT_50);

        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        adContainer.addView(adView);
        adView.loadAd();

        prefManager = new PrefManager(this);
        getSupportActionBar().hide();

        mriver_system = findViewById(R.id.river_system);
        mnotes = findViewById(R.id.notes);
        mquiz = findViewById(R.id.quiz);
        msuggestion = findViewById(R.id.suggestion);
        mmainlanguage = findViewById(R.id.setMainlanguage);
        mr_system_text = findViewById(R.id.r_system_text);
        mnotes_text = findViewById(R.id.notes_text);
        mquiz_text = findViewById(R.id.quiz_text);
        msuggesion_text = findViewById(R.id.suggestion_text);

        if (!prefManager.getLang().isEmpty()) {
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
                Intent i = new Intent(MainOption.this, River_System.class);
                startActivity(i);
            }
        });

        mnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainOption.this, MainActivity.class);
                startActivity(i);
            }
        });
        mquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainOption.this, Questions.class);
                startActivity(i);
            }
        });
        msuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainOption.this, Suggestions.class);
                startActivity(i);
            }
        });
    }


    private void showAlertDialog() {
        AlertResponse response = new AlertResponse() {
            @Override
            public void onPositiveClick(String message) {
                //Action to perform on positive button click
                Snackbar.make(adView, getString(R.string.lang_set_toast, message), Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(MainOption.this, MainOption.class);  //your class
                startActivity(intent);
                finish();
            }
        };

        ShowAlertDialog dialog = new ShowAlertDialog(MainOption.this, response);
        dialog.showLanguageAlert();
    }

    @Override
    public void onBackPressed() {
        AlertResponse response = new AlertResponse() {
            @Override
            public void onPositiveClick(String message) {
                MainOption.this.finish();
            }
        };

        ShowAlertDialog dialog = new ShowAlertDialog(MainOption.this, response);
        dialog.showExitAlert();
    }
}
