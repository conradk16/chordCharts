package com.realbook.jazz.chord.charts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.Constants;
import com.anjlab.android.iab.v3.TransactionDetails;

public class SettingsActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler { // IAP

    BillingProcessor bp; // IAP
    Global g;
    Button upgradeButton;
    Button restoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //IAP
        bp = BillingProcessor.newBillingProcessor(this, getString(R.string.license_key), this);
        bp.initialize();
        //IAP

        g = (Global) getApplication();

        ImageView backBtn = findViewById(R.id.settings_backBtn);
        Button rateButton = findViewById(R.id.settingsRateButton);
        upgradeButton = findViewById(R.id.settingsUpgradeButton);
        restoreButton = findViewById(R.id.settingsRestoreButton);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMainActivity();
            }
        });

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateAction();
            }
        });

        upgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUpgradeDialogue();
            }
        });

        restoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bp.isPurchased(g.productID)) {
                    g.giveProduct();
                    upgradeButton.setVisibility(View.INVISIBLE);
                    restoreButton.setVisibility(View.INVISIBLE);
                    openRestoredDialogue();
                } else {
                    nothingToRestoreDialogue();
                }
            }
        });

        if (g.hasFullVersion) {
            upgradeButton.setVisibility(View.INVISIBLE);
            restoreButton.setVisibility(View.INVISIBLE);
        }
    }

    public void returnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void openUpgradeDialogue() {
        UpgradeDialogue dialogue = new UpgradeDialogue(bp, g, SettingsActivity.this, getSupportFragmentManager());
        dialogue.dialogueTitle = "Upgrade to Full Version?";
        dialogue.dialogueMessage = "Upgrade to the Full Version to remove ads and unlock all content.";
        dialogue.showUpgradeDialogue();
    }

    public void nothingToRestoreDialogue() {
        SimpleDialogue dialogue = new SimpleDialogue(SettingsActivity.this, getSupportFragmentManager());
        dialogue.dialogueTitle = "No purchases to restore";
        dialogue.dialogueMessage = "";
        dialogue.showSimpleDialogue();
    }
    public void openRestoredDialogue() {
        SimpleDialogue dialogue = new SimpleDialogue(SettingsActivity.this, getSupportFragmentManager());
        dialogue.dialogueTitle = "Restore Successful";
        dialogue.dialogueMessage = "Your purchase has been restored";
        dialogue.showSimpleDialogue();
    }

    public void rateAction() {
        try {
            String url = "market://details?id=" + getPackageName();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            String url = "http:play.google.com/store/apps/details?id=" + getPackageName();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

    // IAP
    @Override
    protected void onSaveInstanceState(Bundle outState) { // Need this otherwise always crashes after purchase (purchase goes through, everything works, but it crashes once)
        //No call for super(). Bug on API Level > 11.
        //On purchase, this is called to refresh activity, and I need to indicate that I can release the activity without saving state
    }
    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        g.giveProduct();
        openPurchasedDialogue();
    }
    @Override
    public void onPurchaseHistoryRestored() {}
    @Override
    public void onBillingError(int errorCode, Throwable error) {
        if (errorCode != Constants.BILLING_RESPONSE_RESULT_USER_CANCELED) { // Cancelled not because user hit cancel
            openErrorDialogue();
        }
    }
    @Override
    public void onBillingInitialized() {}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
    public void openPurchasedDialogue() {
        SimpleDialogue dialogue = new SimpleDialogue(SettingsActivity.this, getSupportFragmentManager());
        dialogue.dialogueTitle = "Purchase Successful";
        dialogue.dialogueMessage = "";
        dialogue.showSimpleDialogue();
    }
    public void openErrorDialogue() {
        SimpleDialogue dialogue = new SimpleDialogue(SettingsActivity.this, getSupportFragmentManager());
        dialogue.dialogueTitle = "Error Purchasing";
        dialogue.dialogueMessage = "You have not been charged. Please try again.";
        dialogue.showSimpleDialogue();
    }
    //IAP
}