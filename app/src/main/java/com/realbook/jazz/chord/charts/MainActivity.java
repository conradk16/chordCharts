package com.realbook.jazz.chord.charts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.Constants;
import com.anjlab.android.iab.v3.TransactionDetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    HashMap<String, ArrayList<String>> titlesToChords = new HashMap<>();
    HashMap<String, Boolean> titleIsAllowedOnFreeVersion = new HashMap<>();
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> authors = new ArrayList<>();
    Global global;
    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.main_banner);

        global = (Global) getApplication();
        global.loadPurchasedStatus();
        global.loadReviewPoints();
        global.loadReviewPointsThreshold();

        bp = BillingProcessor.newBillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkDxNOmqxEFQPV3R0iid/ky/Y6hM7teKX7RtfERgLN6KEFGby88Nx3NQSgXXZfbHIaF0yPH2bngHKoOM24C4T1vISq0R7RTa0oAZn6z3//+SqevlsTgSQqrGfiOJiJspNPTQ5FvIXjieHgPw3VlNMqraGbpHtTcvX2c14YgKO4IdsAuvmpRnxQYDEypN9Rko0Eluh7Z92oRNZblRsGBp2Jc4GW9d8BiMV+3MBzgXlXIK6OJwd1G72nLt9l07V97ZhdbVNoiQ/6laulGbhRj9HHpA+NYDp4NiYV6qT0/L2rKZs4apiwQqT8G/x3jlWQ+7PRWthdj+vWt5Yn/hxssLGFQIDAQAB", null);
        bp.initialize();

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

        Boolean isAllowed = true;
        for (String title : titles) {
            System.out.println(title);
            titleIsAllowedOnFreeVersion.put(title, isAllowed);
            isAllowed = !isAllowed;
        }

        for (String t : titles) {
            authors.add(titlesToChords.get(t).get(0));
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
                reloadData();
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
                reloadData();
                searchBar.setCursorVisible(true);
                showKeyboard(MainActivity.this);
            }
        });

        searchBar.setCursorVisible(false);
        reloadData();


    }

    public void openChordDisplayAcivity(String title, String author, ArrayList<String> listOfChords) {
        Intent intent = new Intent(this, ChordDisplay.class);
        intent.putExtra("title", title);
        intent.putExtra("author", author);
        intent.putStringArrayListExtra("list", listOfChords);
        startActivity(intent);
    }

    public void reloadData() {

        ListView listView = (ListView) findViewById(R.id.resultsListView);
        EditText searchBar = (EditText) findViewById(R.id.search_bar);
        final ArrayList<String> titlesToShow = new ArrayList<>();
        final ArrayList<String> authorsToShow = new ArrayList<>();

        String currentText = searchBar.getText().toString();

        for (int i = 0; i < titles.size(); i++) {
            if (titles.get(i).toLowerCase().contains(currentText.toLowerCase()) || authors.get(i).toLowerCase().contains(currentText.toLowerCase())) {
                titlesToShow.add(titles.get(i));
                authorsToShow.add(authors.get(i));
            }
        }

        if (titlesToShow.size() == 0) {
            titlesToShow.add("No results");
            authorsToShow.add("");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, titlesToShow) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(titlesToShow.get(position));
                text1.setTextColor(Color.BLACK);
                text2.setText(authorsToShow.get(position));
                text2.setTextColor(Color.BLACK);
                return view;
            }
        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!global.hasFullVersion && !titleIsAllowedOnFreeVersion.get(titlesToShow.get(position))) { // free version only allows access to every other song
                    openUpgradeDialogue();
                }
                else {
                    openChordDisplayAcivity(titlesToShow.get(position), authorsToShow.get(position), titlesToChords.get(titlesToShow.get(position)));
                }
            }
        });
    }

    public void openUpgradeDialogue() {
        Dialogues dialogue = new Dialogues();
        String title = getResources().getString(R.string.upgrade_title);
        String message = getResources().getString(R.string.upgrade_message);
        dialogue.showUpgradeDialogue(title, message, MainActivity.this, bp, global);
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

    // IAP START

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Need this method otherwise always crashes after purchase (purchase goes through, everything works, but it crashes once)
        //No call for super(). Bug on API Level > 11.
        //On purchase, this is called to refresh activity, and I need to indicate that I can release the activity without saving state
    }
    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        global.giveProduct();
        Dialogues dialogue = new Dialogues();
        String title = getResources().getString(R.string.already_purchased_title);
        String message = getResources().getString(R.string.already_purchased_message);
        dialogue.showNotificationDialogue(title, message, MainActivity.this);
    }
    @Override
    public void onPurchaseHistoryRestored() {

    }
    @Override
    public void onBillingError(int errorCode, Throwable error) {
        if (errorCode != Constants.BILLING_RESPONSE_RESULT_USER_CANCELED) { // Cancelled not because user hit cancel
            Dialogues dialogue = new Dialogues();
            String title = getResources().getString(R.string.error_purchasing_title);
            String message = getResources().getString(R.string.error_purchasing_message);
            dialogue.showNotificationDialogue(title, message, MainActivity.this);
        }
    }
    @Override
    public void onBillingInitialized() {
    }
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

    // IAP END

}
