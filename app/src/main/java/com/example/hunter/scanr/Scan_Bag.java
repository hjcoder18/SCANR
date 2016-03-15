package com.example.hunter.scanr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scan_Bag extends AppCompatActivity {

    private static final String TAG = "ScanBagActivity";

    //PATTERNS
    Pattern shelfPattern = Pattern.compile("([A-Z])-([A-Z])-(\\d+)");
    Pattern bagPattern = Pattern.compile("\\/C\\/C\\d+\\/C\\/C");

    //VARIABLES
    private List<String> listy = new ArrayList<String>();
    private ArrayAdapter<String> adapt;
    private EditText txtInput;
    private String shelfId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Get the id from the scan-shelf activity
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        // check to see if bundle is empty
        if (!extrasBundle.isEmpty()) {
            // check to see if bundle contains the shelfID key
            boolean hasString = extrasBundle.containsKey("shelfID");
            if (hasString) {
                shelfId = extrasBundle.getString("shelfID");
            } else {
                Log.e(TAG, "Error: Shelf ID was not passed to scan_bag activity");
            }
        } else {
            Log.e(TAG, "ERROR: Bundle was empty when passed to scan bag activity");
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning_bag);
        ListView livi = (ListView) findViewById(R.id.listOfBags);
        String[] items = {};
        listy = new ArrayList<>(Arrays.asList(items));
        adapt = new ArrayAdapter<String>(this,R.layout.list_items,R.id.txtItem,listy);
        livi.setAdapter(adapt);
        txtInput = (EditText) findViewById(R.id.input);
        txtInput.addTextChangedListener(watchmen);
    }

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
                check(textToAdd);
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

    void addToListy(String text) {
        listy.add(text);
        adapt.notifyDataSetChanged();
        txtInput.setText("");
    }

    boolean check(String text) {
        /*int lengthOfText = text.length();
        if (lengthOfText >= 13) {
          addToListy(text);
        }*/

        Matcher shelfMatcher = shelfPattern.matcher(text);
        if (shelfMatcher.find()) {
            return true;
        } else {
            return false;
        }
    }
}
