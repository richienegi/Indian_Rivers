package com.r.indian_rivers;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Questions extends AppCompatActivity implements View.OnClickListener {
    LinearLayout li;

    private final String TAG = MainActivity.class.getSimpleName();
    private InterstitialAd interstitialAd;
TextView mtimer,mquestion_no,mquestion;
Button mans1,mans2,mans3,mans4,mskip,mnext;
    private CountDownTimer countDownTimer;
    Animation animation1;


    String ans="";
    static ArrayList<String> answerselected=new ArrayList<>();
    int  questionindex=0;
    static ArrayList<QuizClass> result;
    private long timeLeftInMillis;
    ProgressDialog progressBar;
    PrefManager mpref;
    AdView adView;
    private static final long COUNTDOWN_IN_MILLIS =300000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        if(savedInstanceState!=null) {
            savedInstanceState.clear();
        }

        interstitialAd = new InterstitialAd(this, getResources().getString(R.string.Quiz_interstitial));
       /* mAdView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        adView = new AdView(this, getResources().getString(R.string.Quiz_page), AdSize.BANNER_HEIGHT_50);

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

        li=findViewById(R.id.main);
        getSupportActionBar().hide();
        progressBar = new ProgressDialog(Questions.this);
        mtimer=findViewById(R.id.timeer);
        mquestion=findViewById(R.id.question);
        mquestion_no=findViewById(R.id.no_of_question);
        mans1=findViewById(R.id.ans1);
        mans2=findViewById(R.id.ans2);
        mans3=findViewById(R.id.ans3);
        mans4=findViewById(R.id.ans4);
        mskip=findViewById(R.id.skip);
        mnext=findViewById(R.id.next);
        mans1.setOnClickListener(this);
        mans2.setOnClickListener(this);
        mans3.setOnClickListener(this);
        mans4.setOnClickListener(this);
        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        mpref=new PrefManager(this);
        if(mpref.getLang().equals("Hindi"))
        {
            Hdemo();
        }
        else {
            Edemo();
        }
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

    @Override
    public void onClick(View view) {
    int id=view.getId();
    switch (id)
    {
        case R.id.ans1:
            mans1.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.selected_button));
            mans2.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.default_button));
            mans3.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.default_button));
            mans4.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.default_button));
            mnext.setClickable(true);
            mnext.setEnabled(true);
            ans=mans1.getText().toString();
            break;
        case R.id.ans2:
            mans1.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.default_button));
            mans2.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.selected_button));
            mans3.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.default_button));
            mans4.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.default_button));
            mnext.setClickable(true);
            mnext.setEnabled(true);
            ans=mans2.getText().toString();
            break;
        case R.id.ans3:
            mans1.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.default_button));
            mans2.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.default_button));
            mans3.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.selected_button));
            mans4.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.default_button));
            mnext.setClickable(true);
            mnext.setEnabled(true);
            ans=mans3.getText().toString();
            break;

        case R.id.ans4:
            mans1.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.default_button));
            mans2.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.default_button));
            mans3.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.default_button));
            mans4.setBackground(ContextCompat.getDrawable(getApplication(),R.drawable.selected_button));
            mnext.setClickable(true);
            mnext.setEnabled(true);
            ans=mans4.getText().toString();
            break;
    }
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
        if(timeLeftInMillis < 30000) {
            mtimer.setTextColor(Color.RED);
            mtimer.startAnimation(animation1);
        } else {
            mtimer.setTextColor(Color.BLACK);
            mtimer.clearAnimation();

        }
        //textViewCountDown.clearAnimation();
    }
    public void answerSheet()
    {


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
        Intent i=new Intent(Questions.this,AnswerSheet.class);
        startActivity(i);


    }

    public void showDialog(Activity activity){
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


    public void Edemo()
    {
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Data Loading ...");
        progressBar.show();//displays the progress bar
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(null)
                .build();
        result = new ArrayList<QuizClass>();
        result.clear();
        answerselected.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiQuizE.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        ApiQuizE apiQuizE = retrofit.create(ApiQuizE.class);

        Call<List<Quiz>> call = apiQuizE.getQuestion();
        call.enqueue(new Callback<List<Quiz>>() {
            @Override
            public void onResponse(Call<List<Quiz>> call, Response<List<Quiz>> response) {
                List<Quiz> riv= response.body();

                for (int i = 0; i < riv.size(); i++) {
                    QuizClass ci = new QuizClass();
                    ci.id =riv.get(i).getId();
                    ci.question =riv.get(i).getQuestion();
                    ci.option1 =riv.get(i).getOption1();
                    ci.option2 =riv.get(i).getOption2();
                    ci.option3=riv.get(i).getOption3();
                    ci.option4=riv.get(i).getOption4();
                    ci.answer=riv.get(i).getAnswer();
                    result.add(ci);
                }
                progressBar.dismiss();
                li.setVisibility(View.VISIBLE);
                startCountDown();
                getQuestion();

            }

            @Override
            public void onFailure(Call<List<Quiz>> call, Throwable t) {
                Toast.makeText(Questions.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("myerror",t.getMessage());
                progressBar.dismiss();
            }
        });
    }



    public void Hdemo()
    {
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Data Loading ...");
        progressBar.show();//displays the progress bar




        OkHttpClient client = new OkHttpClient.Builder()
                .cache(null)
                .build();
        result = new ArrayList<QuizClass>();
        result.clear();
        answerselected.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiQuizH.BASE_URL)
            .client(client)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        ApiQuizH apiQuizH = retrofit.create(ApiQuizH.class);

        Call<List<Quiz>> call = apiQuizH.getQuestion();
        call.enqueue(new Callback<List<Quiz>>() {
            @Override
            public void onResponse(Call<List<Quiz>> call, Response<List<Quiz>> response) {
                List<Quiz> riv= response.body();

                for (int i = 0; i < riv.size(); i++) {
                    QuizClass ci = new QuizClass();
                    ci.id =riv.get(i).getId();
                    ci.question =riv.get(i).getQuestion();
                    ci.option1 =riv.get(i).getOption1();
                    ci.option2 =riv.get(i).getOption2();
                    ci.option3=riv.get(i).getOption3();
                    ci.option4=riv.get(i).getOption4();
                    ci.answer=riv.get(i).getAnswer();
                    result.add(ci);
                }
                progressBar.dismiss();
                li.setVisibility(View.VISIBLE);
                startCountDown();
                getQuestion();

            }

            @Override
            public void onFailure(Call<List<Quiz>> call, Throwable t) {
                Toast.makeText(Questions.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("myerror",t.getMessage());
                progressBar.dismiss();
            }
        });
    }
    public  void getQuestion()
    {
        if(questionindex<result.size()) {
            mnext.setClickable(false);
            mnext.setEnabled(false);
            mquestion_no.setText(String.valueOf(questionindex+1)+"/"+ String.valueOf(result.size()));
            mans1.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.default_button));
            mans2.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.default_button));
            mans3.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.default_button));
            mans4.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.default_button));
            mquestion.setText(result.get(questionindex).question);
            mans1.setText(result.get(questionindex).option1);
            mans2.setText(result.get(questionindex).option2);
            mans3.setText(result.get(questionindex).option3);
            mans4.setText(result.get(questionindex).option4);
            questionindex++;
        }
        else {
            answerSheet();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
