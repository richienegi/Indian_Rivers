package com.r.indian_rivers.util;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.r.indian_rivers.R;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this,
                getString(R.string.ad_app_id));
    }
}
