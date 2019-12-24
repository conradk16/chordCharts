package com.realbook.jazz.chord.charts;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.Gravity;
import android.widget.TextView;

import com.anjlab.android.iab.v3.BillingProcessor;

public class Dialogues {

    public void showNotificationDialogue(String messageTitle, String messageBody, Activity activity) {
        TextView title = new TextView(activity);
        title.setText(messageTitle);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setPadding(10, 20, 10, 20);
        title.setGravity(Gravity.CENTER);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        builder.setCustomTitle(title);
        builder.setMessage(messageBody);
        builder.setPositiveButton("Ok", null);

        androidx.appcompat.app.AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogue);

        TextView messageView = dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
        messageView.setPadding(10, 10, 10, 10);
        messageView.setTextColor(Color.BLACK);
        messageView.setTextSize(16);

        // button1 = positive, button2 = negative, button3 = neutral
        TextView positiveButton = dialog.findViewById(android.R.id.button1);
        positiveButton.setTextColor(Color.parseColor("#324CCF"));
        positiveButton.setAllCaps(false);
        positiveButton.setTextSize(18);
    }

    public void showRateDialogue(String messageTitle, String messageBody, final Activity activity, final Global global) {
        TextView title = new TextView(activity);
        title.setText(messageTitle);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setPadding(10, 20, 10, 20);
        title.setGravity(Gravity.CENTER);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        builder.setCustomTitle(title);
        builder.setMessage(messageBody);
        builder.setNeutralButton("Never ask again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                neverAskAgainAction(global);
            }
        });
        builder.setNegativeButton("Remind me later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                remindMeLaterAction(global);
            }
        });
        builder.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                rateAction(activity, global);
            }
        });

        androidx.appcompat.app.AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogue);

        TextView messageView = dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
        messageView.setPadding(10, 10, 10, 10);
        messageView.setTextColor(Color.BLACK);
        messageView.setTextSize(16);

        // button1 = positive, button2 = negative, button3 = neutral
        TextView positiveButton = dialog.findViewById(android.R.id.button1);
        positiveButton.setTextColor(Color.parseColor("#324CCF"));
        positiveButton.setAllCaps(false);
        positiveButton.setTextSize(18);

        TextView neutralButton = dialog.findViewById(android.R.id.button3);
        neutralButton.setTextColor(Color.parseColor("#324CCF"));
        neutralButton.setAllCaps(false);
        neutralButton.setTextSize(18);

        TextView negativeButton = dialog.findViewById(android.R.id.button2);
        negativeButton.setTextColor(Color.parseColor("#324CCF"));
        negativeButton.setAllCaps(false);
        negativeButton.setTextSize(18);
    }

    public void showUpgradeDialogue(String upgradeTitle, String upgradeMessage, final Activity activity, final BillingProcessor bp, final Global global) {
        TextView title = new TextView(activity);
        title.setText(upgradeTitle);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setPadding(10, 20, 10, 20);
        title.setGravity(Gravity.CENTER);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        builder.setCustomTitle(title);
        builder.setMessage(upgradeMessage);
        builder.setNeutralButton("No thanks", null);
        builder.setPositiveButton("Upgrade", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                upgradeClicked(bp, global, activity);
            }
        });

        androidx.appcompat.app.AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogue);

        TextView messageView = dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
        messageView.setPadding(10, 10, 10, 10);
        messageView.setTextColor(Color.BLACK);
        messageView.setTextSize(16);

        // button1 = positive, button2 = negative, button3 = neutral
        TextView positiveButton = dialog.findViewById(android.R.id.button1);
        positiveButton.setTextColor(Color.parseColor("#324CCF"));
        positiveButton.setAllCaps(false);
        positiveButton.setTextSize(18);

        TextView neutralButton = dialog.findViewById(android.R.id.button3);
        neutralButton.setTextColor(Color.parseColor("#324CCF"));
        neutralButton.setAllCaps(false);
        neutralButton.setTextSize(18);
    }

    public void upgradeClicked(BillingProcessor bp, Global global, Activity activity) {
        boolean isOneTimePurchaseSupported = bp.isOneTimePurchaseSupported();
        if(isOneTimePurchaseSupported) {
            if (bp.isPurchased(global.productID)) {
                global.giveProduct();
                String title = activity.getResources().getString(R.string.already_purchased_title);
                String message = activity.getResources().getString(R.string.already_purchased_message);
                showNotificationDialogue(title, message, activity);
            } else {
                bp.purchase(activity, global.productID);
            }
        } else {
            String title = activity.getResources().getString(R.string.already_purchased_title);
            String message = activity.getResources().getString(R.string.already_purchased_message);
            showNotificationDialogue(title, message, activity);
        }
    }

    public void rateAction(Activity activity, Global global) {
        try {
            // open in google play app
            String url = "market://details?id=" + activity.getPackageName();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // open in browser
            String url = "http:play.google.com/store/apps/details?id=" + activity.getPackageName();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            activity.startActivity(intent);
        }
        neverAskAgainAction(global);
    }

    public void remindMeLaterAction(Global global) {
        global.saveReviewPointsThreshold(global.reviewPointsThreshold * 2);
    }

    public void neverAskAgainAction(Global global) {
        global.saveReviewPointsThreshold(1000000); // threshold practically impossible to reach
    }
}
