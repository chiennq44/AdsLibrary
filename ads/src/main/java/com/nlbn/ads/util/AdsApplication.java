package com.nlbn.ads.util;

import android.app.Application;
import android.util.Log;

import java.util.List;

public abstract class AdsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.BUILD_DEBUG = buildDebug();
        Log.i("Application", " run debug: " + AppUtil.BUILD_DEBUG);
        Admob.getInstance().initAdmod(this, getListTestDeviceId());
        if (enableAdsResume()) {
            AppOpenManager.getInstance().init(this, getResumeAdId());
        }
        if (enableAdjustTracking()) {
            Adjust.getInstance().init(this, getAdjustToken());
        }

    }

    public abstract boolean enableAdsResume();

    public abstract boolean enableAdjustTracking();

    public abstract List<String> getListTestDeviceId();

    public abstract String getResumeAdId();

    public abstract String getAdjustToken();

    public abstract Boolean buildDebug();
}
