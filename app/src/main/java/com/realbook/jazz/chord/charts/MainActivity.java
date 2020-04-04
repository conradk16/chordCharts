package com.realbook.jazz.chord.charts;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.Constants;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.ads.consent.ConsentInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler { // IAP

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> authors = new ArrayList<>();
    HashMap<String, ArrayList<String>> titlesToChords = new HashMap<>();
    HashMap<String, Boolean> isTitleLocked = new HashMap<>();
    List<ListEntry> rowEntries = new ArrayList<>();

    Global global;
    ListView resultsListView;
    Button upgradeButton;

    BillingProcessor bp; // IAP

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //IAP
        bp = BillingProcessor.newBillingProcessor(this, getString(R.string.license_key), this);
        bp.initialize();
        //IAP

        global = (Global) getApplication();
        global.loadPurchasedStatus();
        global.loadReviewPoints();
        global.loadReviewPointsThreshold();

        resultsListView = findViewById(R.id.resultsListView);
        ImageView settingsImgView = findViewById(R.id.settingsImgView);
        upgradeButton = findViewById(R.id.upgradeButton);

        upgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUpgradeDialogue();
            }
        });

        settingsImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingsActivity();
            }
        });

        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listEntryClicked(position);
            }
        });

        if (!global.hasFullVersion) {
            global.loadAdsPreference();
            boolean isInEEAOrUnknown = ConsentInformation.getInstance(getApplicationContext()).isRequestLocationInEeaOrUnknown();
            if (isInEEAOrUnknown && global.personalizedAdsStatus.equals("not chosen")) {
                requestPersonalizedAdConsent();
            }
            global.loadFirstInterstitialAd();
        }
        global.timeLastAd = System.currentTimeMillis()/1000;

        InputStream input = null;
        AssetManager manager = getAssets();
        try {
            input = manager.open("database.txt");
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String mline;

            while ((mline = br.readLine()) != null) {
                String title = mline;
                ArrayList<String> list = new ArrayList<>();
                br.readLine();
                while ((mline = br.readLine()) != null && mline.length() != 0) {
                    list.add(mline);
                }
                titlesToChords.put(title, list);
                titles.add(title);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        Collections.sort(titles, new AlphabeticalComparator());

        for (int i = 0; i < titles.size(); i++) {
            authors.add(titlesToChords.get(titles.get(i)).get(0));
            if (global.hasFullVersion) {
                isTitleLocked.put(titles.get(i), false);
            } else {
                if(titles.get(i).equals("I Got Rhythm") || titles.get(i).equals("Autumn Leaves")
                        || titles.get(i).equals("Jazz Blues") || titles.get(i).equals("All The Things You Are") ||
                        titles.get(i).equals("All Of Me") || titles.get(i).equals("Fly Me To The Moon") ||
                        titles.get(i).equals("Take The A Train") || titles.get(i).equals("Summertime") ||
                        titles.get(i).equals("The Girl From Ipanema"))
                    isTitleLocked.put(titles.get(i), false);
                else
                    isTitleLocked.put(titles.get(i), (i % global.LOCK_FREQUENCY == 0));
            }
        }


        final EditText searchBar = (EditText) findViewById(R.id.search_bar);
        searchBar.setSingleLine();


        searchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                populateList();
            }
        });

        searchBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                searchBar.setCursorVisible(true);
                return false;
            }
        });

        searchBar.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    hideKeyboard(MainActivity.this);
                    searchBar.setCursorVisible(false);
                    return true;
                }
                return false;
            }
        });

        Button clearBtn = (Button) findViewById(R.id.clearButton);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.setText("");
                populateList();
                searchBar.setCursorVisible(true);
                showKeyboard(MainActivity.this);
            }
        });

        searchBar.setCursorVisible(false);
        populateList();


    }

    public void openChordDisplayAcivity(String title, String author, ArrayList<String> listOfChords) {
        Intent intent = new Intent(this, ChordDisplay.class);
        intent.putExtra("title", title);
        intent.putExtra("author", author);
        intent.putStringArrayListExtra("list", listOfChords);
        startActivity(intent);
    }

    public void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    public void populateList() {
        rowEntries = new ArrayList<>();
        EditText searchBar = findViewById(R.id.search_bar);
        String currentText = searchBar.getText().toString();

        for (int i = 0; i < titles.size(); i++) {
            Integer img;
            if (isTitleLocked.get(titles.get(i))) {
                img = R.drawable.lock_icon;
            } else {
                img = null;
            }

            if (titles.get(i).toLowerCase().contains(currentText.toLowerCase()) || authors.get(i).toLowerCase().contains(currentText.toLowerCase())) {
                rowEntries.add(new ListEntry(titles.get(i), authors.get(i), img));
            }
        }

        if (rowEntries.size() == 0) {
            rowEntries.add(new ListEntry("No results", "", null));
        }

        CustomListAdapter customAdapter = new CustomListAdapter(this, R.layout.custom_list_entry, rowEntries);
        resultsListView.setAdapter(customAdapter);
    }

    public void listEntryClicked(int position) {
        if (!rowEntries.get(position).t1.equals("No results")) {
            ListEntry e = rowEntries.get(position);
            if (e.image == null) {
                openChordDisplayAcivity(e.t1, e.t2, titlesToChords.get(e.t1));
            } else {
                openUpgradeDialogue();
            }
        }
    }

    public void openUpgradeDialogue() {
        UpgradeDialogue dialogue = new UpgradeDialogue(bp, global, MainActivity.this, getSupportFragmentManager());
        dialogue.dialogueTitle = "Upgrade to Full Version?";
        dialogue.dialogueMessage = "Upgrade to the Full Version to remove ads and unlock all content.";
        dialogue.showUpgradeDialogue();
    }

    public void requestPersonalizedAdConsent() {
        TextView title = new TextView(MainActivity.this);
        title.setPadding(10, 20, 10, 20);
        title.setTextColor(Color.BLACK);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setTextSize(20);
        title.setText("This app shows ads. Can we use your data to show ads that are more relevant?");
        title.setGravity(Gravity.CENTER);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        builder.setCustomTitle(title);
        builder.setPositiveButton("Yes, see relevant ads", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                global.saveAdsPreference(true);

            }
        });
        builder.setNegativeButton("No, see ads that are less relevant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                global.saveAdsPreference(false);
            }
        });
        builder.setNeutralButton("Upgrade to the ad-free version", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openUpgradeDialogue();
            }
        });
        builder.setMessage("");
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


        TextView negativeButton = (TextView)dialog.findViewById(android.R.id.button2);
        negativeButton.setTextColor(Color.parseColor("#324CCF"));
        negativeButton.setAllCaps(false);
        negativeButton.setTextSize(18);

        TextView neutralButton = (TextView)dialog.findViewById(android.R.id.button3);
        neutralButton.setTextColor(Color.parseColor("#324CCF"));
        neutralButton.setAllCaps(false);
        neutralButton.setTextSize(18);
    }

    // Screen tapped anywhere
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            EditText searchBar = findViewById(R.id.search_bar);
            searchBar.setCursorVisible(false);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.showSoftInput(view, 0);
    }

    // IAP
    @Override
    protected void onSaveInstanceState(Bundle outState) { // Need this otherwise always crashes after purchase (purchase goes through, everything works, but it crashes once)
        //No call for super(). Bug on API Level > 11.
        //On purchase, this is called to refresh activity, and I need to indicate that I can release the activity without saving state
    }
    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        global.giveProduct();
        populateList();
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
        SimpleDialogue dialogue = new SimpleDialogue(MainActivity.this, getSupportFragmentManager());
        dialogue.dialogueTitle = "Purchase Successful";
        dialogue.dialogueMessage = "";
        dialogue.showSimpleDialogue();
    }
    public void openErrorDialogue() {
        SimpleDialogue dialogue = new SimpleDialogue(MainActivity.this, getSupportFragmentManager());
        dialogue.dialogueTitle = "Error Purchasing";
        dialogue.dialogueMessage = "You have not been charged. Please try again.";
        dialogue.showSimpleDialogue();
    }
    //IAP

}
