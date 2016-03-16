package com.example.hunter.scanr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

/**
 * Scan Shelf Class
 * This class has everything to do with the ability to scan a shelf using the scanner device.
 *
 * @author Korey MacGill, Hunter Marshall, William Montesdeoca
 * @version 2016.0215
 * @since 1.0
 */
public class Scan_Shelf extends AppCompatActivity {
    // Patterns for the Shelf
    Pattern rackPattern = Pattern.compile("([A-Z])-([A-Z])-(\\d+)");

    private EditText txtInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning_shelf);
        //ListView livi = (ListView) findViewById(R.id.listOfBags);
        //String[] items = {};
        //listy = new ArrayList<>(Arrays.asList(items));
        //adapt = new ArrayAdapter<String>(this,R.layout.list_items,R.id.txtItem,listy);
        //livi.setAdapter(adapt);
        txtInput = (EditText) findViewById(R.id.input);
        txtInput.addTextChangedListener(watchmen);
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

    /**
     * Create a new Text Watcher
     */
    TextWatcher watchmen = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0 ) {
                //TextView output = (TextView) findViewById(R.id.result);
                //output.setText(s + "\n");
                String textToAdd = txtInput.getText().toString();
                //buffTheMagicDragon.append(textToAdd);
                boolean ready = check(textToAdd);
                if(ready) {
                    redirect(textToAdd);
                }
                else if (!ready){
                    Toast.makeText(Scan_Shelf.this, "This isn't a shelf barcode...", Toast.LENGTH_LONG).show();
                }
                else {
                    System.out.println("ERROR: WE HIT THIS THING");
                }
                //if succeeded in finding a shelf, pass id to scan_bag and begin.
                //else do nothing.
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
            String scanData = s.toString();
            if (check(scanData)) {
                EditText Texting = (EditText)findViewById(R.id.input);
                Texting.setText("");
            }
            /*if (s.length() >= 13) {
                EditText Texting = (EditText)findViewById(R.id.input);
                Texting.setText("");
            }*/
        }
    };

    /**
     * Checks to see if the barcode scanned matches the pattern for rack.
     *
     * @param text - is the pattern for the barcode that was scanned
     * @return - returns true or false depending of if the barcode matches the pattern for shelf.
     */
    boolean check(String text) {
        Matcher rackMatcher = rackPattern.matcher(text);
        if (rackMatcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Directs the app to the scan_bag activity to allow the user to scan bags.
     *
     * @param textToAdd - the rack id that will be moved to the new activity
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
