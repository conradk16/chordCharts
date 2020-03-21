package com.realbook.jazz.chord.charts;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Global extends Application {

    String productID = "com.realbook.jazz.chord.charts.fullversion";
    Boolean hasFullVersion = true;

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
