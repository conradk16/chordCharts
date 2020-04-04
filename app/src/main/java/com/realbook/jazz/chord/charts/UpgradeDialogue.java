package com.realbook.jazz.chord.charts;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.anjlab.android.iab.v3.BillingProcessor;

public class UpgradeDialogue {

    String dialogueTitle = "";
    String dialogueMessage = "";
    BillingProcessor bp;
    Global g;
    Activity activity;
    FragmentManager fm;

    public void showUpgradeDialogue() {

        TextView title = new TextView(activity);
        title.setPadding(10, 20, 10, 20);
        title.setTextColor(Color.BLACK);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setTextSize(20);
        title.setText(dialogueTitle);
        title.setGravity(Gravity.CENTER);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        builder.setCustomTitle(title);
        builder.setNeutralButton("No thanks", null);
        builder.setPositiveButton("Upgrade", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                upgradeAction();
            }
        });
        builder.setMessage(dialogueMessage);
        androidx.appcompat.app.AlertDialog dialog = builder.show();

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogue);


        TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
        messageView.setPadding(10, 10, 10, 10);
        messageView.setTextColor(Color.BLACK);
        messageView.setTextSize(16);

        // button1 = positive, button2 = negative, button3 = neutral
        TextView positiveButton = (TextView)dialog.findViewById(android.R.id.button1);
        positiveButton.setTextColor(Color.parseColor("#324CCF"));
        positiveButton.setAllCaps(false);
        positiveButton.setTextSize(18);


        TextView negativeButton = (TextView)dialog.findViewById(android.R.id.button3);
        negativeButton.setTextColor(Color.parseColor("#324CCF"));
        negativeButton.setAllCaps(false);
        negativeButton.setTextSize(18);

    }

    public UpgradeDialogue(BillingProcessor billingProcessor, Global global, Activity a, FragmentManager fragman) {
        this.bp = billingProcessor;
        this.g = global;
        this.activity = a;
        this.fm = fragman;
    }
    public UpgradeDialogue() {

    }

    public void upgradeAction() {
        boolean isOneTimePurchaseSupported = bp.isOneTimePurchaseSupported();
        if(isOneTimePurchaseSupported) {
            if (bp.isPurchased(g.productID)) {
                g.giveProduct();
                openAlreadyPurchasedDialogue();
            } else {
                bp.purchase(activity, g.productID);
            }
        } else {
            openPaymentNotSupportedDialogue();
        }
    }

    public void openPaymentNotSupportedDialogue() {
        SimpleDialogue dialogue = new SimpleDialogue(activity, fm);
        dialogue.dialogueTitle = "In-app Billing Services Not Available";
        dialogue.dialogueMessage = "This device does not support in-app billing services";
        dialogue.showSimpleDialogue();
    }
    public void openAlreadyPurchasedDialogue() {
        SimpleDialogue dialogue = new SimpleDialogue(activity, fm);
        dialogue.dialogueTitle = "You've already purchased this";
        dialogue.dialogueMessage = "Your purchase has been restored";
        dialogue.showSimpleDialogue();
    }

}