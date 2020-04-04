package com.realbook.jazz.chord.charts;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.ads.consent.ConsentInformation;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Global extends Application {

    String productID = "com.realbook.jazz.chord.charts.fullversion";
    Boolean hasFullVersion = false;
    int LOCK_FREQUENCY = 5;

    long timeLastAd;
    public InterstitialAd mInterstitialAd;
    public String personalizedAdsStatus = "not chosen";
    int MIN_TIME_BETWEEN_ADS = 60; // in seconds

    int reviewPoints = 0;
    int reviewPointsThreshold = 30;
    int pointsForViewingSong = 1;

    public static final int MAJOR7 = 0;
    public static final int MINOR7 = 1;
    public static final int HALFDIMINISHED7 = 2;
    public static final int DIMINISHED7 = 3;
    public static final int DOMINANT = 4;
    public static final int AUGMENTED7 = 5;
    public static final int MINMAJ7 = 6;
    public static final int MAJOR = 7;
    public static final int MINOR = 8;
    public static final int DIMINISHED = 9;
    public static final int AUGMENTED = 10;
    public static final int MAJOR6 = 11;
    public static final int MINOR6 = 12;
    public static final int MINOR9 = 13;
    public static final int DOMINANT9 = 14;
    public static final int MINOR11 = 15;
    public static final int DOMINANT13 = 16;

    public static final int CFLAT = 17;
    public static final int C = 0;
    public static final int CSHARP = 1;
    public static final int DFLAT = 2;
    public static final int D = 3;
    public static final int DSHARP = 4;
    public static final int EFLAT = 5;
    public static final int E = 6;
    public static final int F = 7;
    public static final int FSHARP = 8;
    public static final int GFLAT = 9;
    public static final int G = 10;
    public static final int GSHARP = 11;
    public static final int AFLAT = 12;
    public static final int A = 13;
    public static final int ASHARP = 14;
    public static final int BFLAT = 15;
    public static final int B = 16;

    public void loadFirstInterstitialAd() {
        mInterstitialAd = new InterstitialAd(this);

        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_test_ad_id)); // TEST AD ID

        boolean isInEEA = ConsentInformation.getInstance(getApplicationContext()).isRequestLocationInEeaOrUnknown();
        if (isInEEA && !personalizedAdsStatus.equals("true")) {
            Bundle extras = new Bundle();
            extras.putString("npa", "1");
            mInterstitialAd.loadAd(new AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                    .build());
        } else {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                timeLastAd = System.currentTimeMillis()/1000;
                boolean isInEEA = ConsentInformation.getInstance(getApplicationContext()).isRequestLocationInEeaOrUnknown();
                if (isInEEA && !personalizedAdsStatus.equals("true")) {
                    Bundle extras = new Bundle();
                    extras.putString("npa", "1");
                    mInterstitialAd.loadAd(new AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                            .build());
                } else {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            }

        });
    }

    public void showInterstitialAd() {
        if (mInterstitialAd == null) {
            loadFirstInterstitialAd();
        }
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            // reloading happens when onAdClosed() is called
        } else {
            System.out.println("Interstitial not loaded");
        }
    }

    public boolean timeToShowInterstitialAd() {
        if (hasFullVersion) {
            return false;
        }

        long timeSinceLastAd = (System.currentTimeMillis()/1000) - timeLastAd;
        return (timeSinceLastAd > MIN_TIME_BETWEEN_ADS);
    }

    public void loadAdsPreference() {
        SharedPreferences sp = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("adPersonalizationStatus", null);
        Type type = new TypeToken<String>() {}.getType();
        String storedValue = gson.fromJson(json, type);
        if (storedValue != null) {
            if (storedValue.equals("true")) {
                personalizedAdsStatus = "true";
            } else {
                personalizedAdsStatus = "false";
            }
        }
    }

    public void saveAdsPreference(Boolean personalized) {
        String saveState;
        if (personalized) {
            saveState = "true";
            personalizedAdsStatus = "true";
        } else {
            saveState = "false";
            personalizedAdsStatus = "false";
        }
        SharedPreferences sp = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(saveState);
        editor.putString("adPersonalizationStatus", json);
        editor.apply();
    }

    public void giveProduct() {
        savePurchasedStatus(true);
    }

    public void loadPurchasedStatus() {
        SharedPreferences sp = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("purchaseStatus", null);
        Type type = new TypeToken<String>() {}.getType();
        String storedValue = gson.fromJson(json, type);
        if (storedValue != null) {
            if (storedValue.equals("hasFullVersion")) {
                hasFullVersion = true;
            } else {
                hasFullVersion = false;
            }
        }
    }

    public void savePurchasedStatus(Boolean didPurchase) {
        String saveState = "";
        if (didPurchase) {
            saveState = "hasFullVersion";
            hasFullVersion = true;
        }
        SharedPreferences sp = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(saveState);
        editor.putString("purchaseStatus", json);
        editor.apply();
    }

    public void loadReviewPointsThreshold() {
        SharedPreferences sp = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Integer threshold = sp.getInt("reviewPointsThreshold", reviewPointsThreshold); // default is reviewPointsThreshold
        reviewPointsThreshold = threshold;
    }

    public void saveReviewPointsThreshold(Integer threshold) {
        SharedPreferences sp = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("reviewPointsThreshold", threshold);
        reviewPointsThreshold = threshold;
        editor.apply();
    }

    public void loadReviewPoints() {
        SharedPreferences sp = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Integer points = sp.getInt("reviewPoints", 0);
        reviewPoints = points;
    }

    public void saveReviewPoints(Integer points) {
        SharedPreferences sp = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("reviewCount", points);
        reviewPoints = points;
        editor.apply();
    }

    public boolean timeToAskForReview() {
        if (reviewPoints > reviewPointsThreshold) {
            return true;
        }
        return false;

    }
}
