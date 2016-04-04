package com.example.hunter.scanr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * FAIL CLASS
 * This will allow a user friend pop window to appear when the contents failed to connect and be sent
 * to the bookstore's API.
 */
public class Fail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fail_page);

        // create a metric system
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // initialize width and height
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        // shrink activity to the size of a pop up window
        getWindow().setLayout((int) (width * .8), (int) (height * .3));
    }
}
