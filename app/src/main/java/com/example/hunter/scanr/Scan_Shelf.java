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
        //txtInput.addTextChangedListener(watchmen);
    }

//    public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//
//        if (id == R.id.) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//        Intent myIntent = new Intent(getApplicationContext(), Scan_Shelf.class);
//        startActivityForResult(myIntent, 0);
//        return true;
//    }

//    TextWatcher watchmen = new TextWatcher() {
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            if (s.length() > 0 ) {
//                //TextView output = (TextView) findViewById(R.id.result);
//                //output.setText(s + "\n");
//                String textToAdd = txtInput.getText().toString();
//                //buffTheMagicDragon.append(textToAdd);
//                boolean ready = (textToAdd);
//                if(ready) {
//                    redirect(textToAdd);
//                }
//                else if (!ready){
//                    Toast.makeText(Scan_Shelf.this, "This isn't a shelf barcode...", Toast.LENGTH_SHORT).show();
//                }
//                else {check
//                    System.out.println("ERROR: WE HIT THIS THING");
//                }
//                //if succeeded in finding a shelf, pass id to scan_bag and begin.
//                //else do nothing.
//            }
//        }
//        @Override
//        public void afterTextChanged(Editable s) {
//            String scanData = s.toString();
//            if (check(scanData)) {
//                EditText Texting = (EditText)findViewById(R.id.input);
//                Texting.setText("");
//            }
//        }
//    };

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
