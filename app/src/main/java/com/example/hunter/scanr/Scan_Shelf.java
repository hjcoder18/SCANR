package com.example.hunter.scanr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SCAN_SHELF CLASS EXTENDS APPCOMPATEACTIVITY
 *
 * This class will allow us to scan the racks, while checking the patterns of the bar code to
 * determine if the item being scanned is a rack. If the item is a rack it will then move to the
 * next activity, otherwise it will clear the input and allow the user to try again.
 */
public class Scan_Shelf extends AppCompatActivity {

    private static final String TAG = "ScanShelfActivity";

    // Pattern for the Shelf
    Pattern shelfPattern = Pattern.compile("([A-Z])-([A-Z])-(\\d+)");
    TextView errors;
    private EditText txtInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning_shelf);
        txtInput = (EditText) findViewById(R.id.input);
        errors = (TextView) findViewById(R.id.ErrorMessages);
        errors.setText("");
    }

    /**
     * SUBMIT METHOD
     *
     * Checks to ensure that correct barcode was scanned
     * and passes user to next activity if they have. otherwise clears the input
     * and allows user to try again.
     *
     * @param v - view to attach to activity
     */
    public void Submit(View v) {
        if (txtInput.length() > 0 ) {
            String textToAdd = txtInput.getText().toString();
            if(check(textToAdd)) {
                redirect(textToAdd);
            }
            else if (!check(textToAdd)){
                errors.setText("ERROR: Incorrect barcode, Please Try Again");
                txtInput.setText("");
            }
            else {
                Log.e(TAG, "ERROR: Correct Barcode scanned but still misfire, troubleshoot needed.");
            }
        }
    }

    /**
     * CHECK METHOD
     * Will check to see if the item scanned is a rack. Will return true if it is a rack, and false
     * if it is not a rack.
     * @param text - item to be checked
     * @return true or false - true if the item is a rack, false otherwise
     */
    boolean check(String text) {
        // check the pattern to make sure it matches
        Matcher shelfMatcher = shelfPattern.matcher(text);

        // if it matches return true, otherwise return false
        if (shelfMatcher.matches()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Will move to the next activity that will allow the user to scan the bags on the rack.
     * It will pass the rack ID to the next activity.
     *
     * @param textToAdd - the rack id that will be sent to the next activity
     */
    void redirect(String textToAdd) {
        Intent bag_act = new Intent(this, Scan_Bag.class);

        // Put string into a bundle and then pass the bundle to the new activity
        Bundle bundle = new Bundle();

        // Add the shelfID to the bundles so we can use it in the new activity
        bundle.putString("shelfID", textToAdd);
        bag_act.putExtras(bundle);

        // go to new activity
        startActivity(bag_act);
    }

}
