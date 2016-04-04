package com.example.hunter.scanr;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * HOME ACTIVIITY
 * Allows the user to decide if they want to scan a rack for audit purposes,
 * or if they want to scan a bag to display it's content, who it belongs to,
 * and the location is should be at.
 */
public class Home extends AppCompatActivity {

    private static final String TAG = "HomeActivity";  // used for log

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.about_scanr:
                startActivity(new Intent(Home.this, About.class));
                break;
            case R.id.report_bug:
                startActivity(new Intent(Home.this, Report.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * DIRECT TO SCANNING
     * Directs the user to new activity so they can start scanning a rack and it's bags.
     *
     * @param v - parameter used for button
     */
    public void directToScanning(View v) {
        Intent scan_act = new Intent(this, Scan_Shelf.class);
        startActivity(scan_act);
    }

    /**
     * DIRECT TO SEARCH BAG
     * Directs the user to new activity so they can scan a bag and view it's content,
     * who the bag belongs to, and it's proper location.
     *
     * @param v - parameter used for button
     */
    public void directToSearchBag(View v) {
        Intent search_act = new Intent(this, Search_Bag.class);
        startActivity(search_act);
    }

}
