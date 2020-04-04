package com.realbook.jazz.chord.charts;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

public class SimpleDialogue {

    String dialogueTitle = "";
    String dialogueMessage = "";
    FragmentManager fm;
    Activity activity;

    public void showSimpleDialogue() {

        TextView title = new TextView(activity);
        title.setPadding(10, 20, 10, 20);
        title.setTextColor(Color.BLACK);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setTextSize(20);
        title.setText(dialogueTitle);
        title.setGravity(Gravity.CENTER);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        builder.setCustomTitle(title);
        builder.setPositiveButton("OK", null);
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

    }

    public SimpleDialogue(Activity a, FragmentManager fragman) {
        this.activity = a;
        this.fm = fragman;
    }
    public SimpleDialogue() {

    }
}