package com.example.hunter.scanr;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Home extends AppCompatActivity {
//THIS IS A TEST TO SEE IF PUSHING IS WORKING!

    private static final String TAG = "EntityListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // gets the action bar for the application
        try {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.bstore_logo);
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to create University Store Logo", e);
        }

        // test our logs
        Shelf shelf = new Shelf("ad232");
        Bag bag = new Bag();
        bag.setStudentName("Hunter", "Marshall");
        shelf.addBag(bag);
        Book book1 = new Book(1);
        bag.addBook(book1);

    }

    public void directToScanning(View v) {
        Intent scan_act = new Intent(this, Scan_Shelf.class);
        startActivity(scan_act);
    }

    public void directToSearch(View v) {
        Intent search_act = new Intent(this, Search.class);
        startActivity(search_act);
    }

}
