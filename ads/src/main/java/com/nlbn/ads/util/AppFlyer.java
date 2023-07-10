package com.nlbn.ads.util;

import android.app.Application;
import android.util.Log;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.adrevenue.AppsFlyerAdRevenue;
import com.appsflyer.adrevenue.adnetworks.generic.MediationNetwork;
import com.appsflyer.adrevenue.adnetworks.generic.Scheme;
import com.google.android.gms.ads.AdValue;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AppFlyer {
    private static AppFlyer INSTANCE;
    private boolean enableTrackingAppFlyerRevenue = false;
    private static final String TAG = "AppFlyer";

    public static AppFlyer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppFlyer();
        }
        return INSTANCE;
    }

    public void initAppFlyer(Application context, String devKey, boolean enableTrackingAppFlyerRevenue) {
        this.enableTrackingAppFlyerRevenue = enableTrackingAppFlyerRevenue;
        initAppFlyerDebug(context, devKey, false);
    }

    public void initAppFlyerDebug(Application context, String devKey, boolean enableDebugLog) {
        AppsFlyerLib.getInstance().init(devKey, null, context);
        AppsFlyerLib.getInstance().start(context);

        AppsFlyerAdRevenue.Builder afRevenueBuilder = new AppsFlyerAdRevenue.Builder(context);
        AppsFlyerAdRevenue.initialize(afRevenueBuilder.build());
        AppsFlyerLib.getInstance().setDebugLog(enableDebugLog);
    }

    public void pushTrackEventAdmod(AdValue adValue, String adId, String adType) {
        Log.e(TAG, "Log tracking event AppFlyer: enableAppFlyer:" + this.enableTrackingAppFlyerRevenue + " --- value: " + adValue.getValueMicros() / 1000000);
        if (enableTrackingAppFlyerRevenue) {
            Map<String, String> customParams = new HashMap<>();
            customParams.put(Scheme.AD_UNIT, adId);
            customParams.put(Scheme.AD_TYPE, adType);
            AppsFlyerAdRevenue.logAdRevenue(
                    "Admod",
                    MediationNetwork.googleadmob,
                    Currency.getInstance(Locale.US),
                    adValue.getValueMicros() / 1000000.0,
                    customParams);
        }
    }
}
