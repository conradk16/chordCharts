package com.realbook.jazz.chord.charts;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    HashMap<String, ArrayList<String>> titlesToChords = new HashMap<>();
    ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.main_banner);

        final Button hell_btn = (Button) findViewById(R.id.hell_btn);
        hell_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChordDisplayForTesting();
            }
        });


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


    }

    public void openChordDisplayForTesting() {
        String title = titles.get(0);
        ArrayList<String> listOfChords = titlesToChords.get(title);

        Intent intent = new Intent(this, ChordDisplay.class);
        intent.putExtra("title", title);
        intent.putStringArrayListExtra("list", listOfChords);
        startActivity(intent);
    }

    public void openChordDisplayAcivity(Integer listPosition) {
        String title = titles.get(listPosition);
        ArrayList<String> listOfChords = titlesToChords.get(title);

        Intent intent = new Intent(this, ChordDisplay.class);
        intent.putExtra("title", title);
        intent.putStringArrayListExtra("list", listOfChords);
        startActivity(intent);
    }

    public void reloadData(ArrayList<String> titles) {
        //TODO
    }
}
