package com.r.indian_rivers.views.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.r.indian_rivers.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashScreen extends AppCompatActivity {

    CircleImageView mimg;
    TextView mtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        mimg = findViewById(R.id.img);
        mtxt = findViewById(R.id.text);

       /* if (!mypref.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }*/

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null) {
            mimg.setImageResource(R.drawable.emoji);
            mtxt.setText("No Internet Connection");
        } else {

            new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

                @Override

                public void run() {

                    Intent i = new Intent(SplashScreen.this, MainOption.class);

                    startActivity(i);

                    // close this activity

                    finish();

                }

            }, 1 * 1000); // wait for 5 seconds
            /*mypref.setFirstTimeLaunch(false);*/
        }
    }
   /* public  void launchHomeScreen()
    {
        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(i);

    }*/
}
