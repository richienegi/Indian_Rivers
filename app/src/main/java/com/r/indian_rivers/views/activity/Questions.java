package com.r.indian_rivers.views.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.material.snackbar.Snackbar;
import com.r.indian_rivers.R;
import com.r.indian_rivers.model.Quiz;
import com.r.indian_rivers.network.Api;
import com.r.indian_rivers.network.RetrofitInstance;
import com.r.indian_rivers.util.PrefManager;
import com.r.indian_rivers.views.alertdialog.AlertResponse;
import com.r.indian_rivers.views.alertdialog.ShowAlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Questions extends AppCompatActivity implements View.OnClickListener {
    LinearLayout li;

    private final String TAG = MainActivity.class.getSimpleName();
    private InterstitialAd interstitialAd;
    TextView mtimer, mquestion_no, mquestion;
    Button mans1, mans2, mans3, mans4, mskip, mnext;
    private CountDownTimer countDownTimer;
    Animation animation1;
    LinearLayout adContainer;

    String ans = "";
    static ArrayList<String> answerselected = new ArrayList<>();
    static ArrayList<Quiz> result;

    int questionindex = 0;
    private long timeLeftInMillis;
    ProgressDialog progressBar;
    PrefManager mpref;
    AdView adView;

    private static final long COUNTDOWN_IN_MILLIS = 300000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        if (savedInstanceState != null) {
            savedInstanceState.clear();
        }

        interstitialAd = new InterstitialAd(this, getResources().getString(R.string.Quiz_interstitial));
       /* mAdView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        adView = new AdView(this, getResources().getString(R.string.Quiz_page), AdSize.BANNER_HEIGHT_50);

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

        li = findViewById(R.id.main);
        getSupportActionBar().hide();
        progressBar = new ProgressDialog(Questions.this);
        mtimer = findViewById(R.id.timeer);
        mquestion = findViewById(R.id.question);
        mquestion_no = findViewById(R.id.no_of_question);
        mans1 = findViewById(R.id.ans1);
        mans2 = findViewById(R.id.ans2);
        mans3 = findViewById(R.id.ans3);
        mans4 = findViewById(R.id.ans4);
        mskip = findViewById(R.id.skip);
        mnext = findViewById(R.id.next);
        mans1.setOnClickListener(this);
        mans2.setOnClickListener(this);
        mans3.setOnClickListener(this);
        mans4.setOnClickListener(this);
        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        mpref = new PrefManager(this);

        fetchData();

        mskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuestion();
                answerselected.add("skipped");

            }
        });
        mnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuestion();
                answerselected.add(ans);
            }
        });
    }

    private void fetchData() {
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Data Loading ...");
        progressBar.show();//displays the progress bar

        result = new ArrayList<Quiz>();
        result.clear();
        answerselected.clear();

        Api api = RetrofitInstance.getApi();

        Call<List<Quiz>> call = null;

        if (mpref.getLang().equals("Hindi")) {
            call = api.getHindiQuestion();
        } else {
            call = api.getEngQuestion();
        }

        call.enqueue(new Callback<List<Quiz>>() {
            @Override
            public void onResponse(Call<List<Quiz>> call, Response<List<Quiz>> response) {
                List<Quiz> riv = response.body();

                result.addAll(riv);

                progressBar.dismiss();
                li.setVisibility(View.VISIBLE);

                startCountDown();
                getQuestion();

            }

            @Override
            public void onFailure(Call<List<Quiz>> call, Throwable t) {
                Snackbar.make(adContainer, "Something went wrong\nPlease Check internt connection.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fetchData();
                            }
                        }).show();
                Log.d("myerror", t.getMessage());
                progressBar.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        setButtonBackground();
        mnext.setClickable(true);
        mnext.setEnabled(true);
        mnext.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.selected_button));
        mnext.setTextColor(Color.WHITE);
        switch (id) {
            case R.id.ans1:
                mans1.setTextColor(Color.WHITE);
                mans1.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.selected_button));
                ans = mans1.getText().toString();
                break;
            case R.id.ans2:
                mans2.setTextColor(Color.WHITE);
                mans2.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.selected_button));
                ans = mans2.getText().toString();
                break;
            case R.id.ans3:
                mans3.setTextColor(Color.WHITE);
                mans3.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.selected_button));
                ans = mans3.getText().toString();
                break;

            case R.id.ans4:
                mans4.setTextColor(Color.WHITE);
                mans4.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.selected_button));
                ans = mans4.getText().toString();
                break;
        }
    }

    private void setButtonBackground() {
        mnext.setTextColor(Color.BLACK);
        mans1.setTextColor(Color.BLACK);
        mans2.setTextColor(Color.BLACK);
        mans3.setTextColor(Color.BLACK);
        mans4.setTextColor(Color.BLACK);

        mnext.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.default_button));
        mans1.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.default_button));
        mans2.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.default_button));
        mans3.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.default_button));
        mans4.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.default_button));
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                showDialog(Questions.this);

            }
        }.start();
    }

    private void updateCountDownText() {
        int minuts = (int) (timeLeftInMillis / 1000) / 60;
        int second = (int) (timeLeftInMillis / 1000) % 60;

        String timeformated = String.format(Locale.getDefault(), "%02d:%02d", minuts, second);
        mtimer.setText(timeformated);
        if (timeLeftInMillis < 30000) {
            mtimer.setTextColor(Color.RED);
            mtimer.startAnimation(animation1);
        } else {
            mtimer.setTextColor(Color.BLACK);
            mtimer.clearAnimation();

        }
        //textViewCountDown.clearAnimation();
    }

    public void answerSheet() {
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();


        finish();
        Intent i = new Intent(Questions.this, AnswerSheet.class);
        startActivity(i);
    }

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.timerup);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText("Time Over");

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                answerSheet();
            }
        });

        dialog.show();
    }

    public void getQuestion() {
        if (questionindex < result.size()) {

            setButtonBackground();

            mnext.setClickable(false);
            mnext.setEnabled(false);
            mquestion_no.setText((questionindex + 1) + "/" + result.size());
            mquestion.setText(result.get(questionindex).getQuestion());
            mans1.setText(result.get(questionindex).getOption1());
            mans2.setText(result.get(questionindex).getOption2());
            mans3.setText(result.get(questionindex).getOption3());
            mans4.setText(result.get(questionindex).getOption4());
            questionindex++;
        } else {
            answerSheet();
        }

    }

    @Override
    public void onBackPressed() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.malertad, null);
        com.google.android.gms.ads.AdView adView = alertLayout.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        AlertResponse response = new AlertResponse() {
            @Override
            public void onPositiveClick(String message) {
                Questions.this.finish();
            }
        };

        ShowAlertDialog dialog = new ShowAlertDialog(Questions.this, alertLayout, response);
        dialog.showExitAlert();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
