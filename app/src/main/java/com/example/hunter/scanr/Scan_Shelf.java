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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//comment WILL ARE YOU GETTING THIS!?
public class Scan_Shelf extends AppCompatActivity {

    private static final String TAG = "ScanShelfActivity";
    // Pattern for the Shelf
    Pattern shelfPattern = Pattern.compile("([A-Z])-([A-Z])-(\\d+)");

    private EditText txtInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning_shelf);
        txtInput = (EditText) findViewById(R.id.input);
    }

    /**
     * submit function, checks to ensure that correct barcode was scanned
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
                Toast.makeText(Scan_Shelf.this, "ERROR: Incorrect barcode, Please Try Again", Toast.LENGTH_SHORT).show();
                txtInput.setText("");
            }
            else {
                Log.e(TAG, "ERROR: some strangness happened");
            }
        }
    }

    boolean check(String text) {
        Matcher shelfMatcher = shelfPattern.matcher(text);
        if (shelfMatcher.matches()) {
            return true;
        }
        else {
            return false;
        }
    }

    /*
     * http://www.101apps.co.za/index.php/articles/passing-data-between-activities.html
     */
    void redirect(String textToAdd) {
        Intent bag_act = new Intent(this, Scan_Bag.class);
        // Put string into a bundle and then pass the bundle to the new activity
        Bundle bundle = new Bundle();
        bundle.putString("shelfID", textToAdd);
        bag_act.putExtras(bundle);
        startActivity(bag_act);
    }

}
