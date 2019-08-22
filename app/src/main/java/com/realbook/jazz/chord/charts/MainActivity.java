package com.realbook.jazz.chord.charts;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    HashMap<String, ArrayList<String>> titlesToChords = new HashMap<>();
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> authors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.main_banner);


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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, titlesToShow) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(titlesToShow.get(position));
                text2.setText(authorsToShow.get(position));
                return view;
            }
        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openChordDisplayAcivity(titlesToShow.get(position), authorsToShow.get(position), titlesToChords.get(titlesToShow.get(position)));
            }
        });
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
}
