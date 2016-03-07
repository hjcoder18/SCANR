package com.example.hunter.scanr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scan extends AppCompatActivity {

    //String scanData;

    // Patterns for the Shelf
    //Pattern shelfPattern = Pattern.compile("([A-Z])-([A-Z])-(\\d+)");
    Pattern shelfPattern = Pattern.compile("(\\d+)");


    // Patterns for the Bag
    Pattern bagPattern = Pattern.compile("\\/C\\/C\\d+\\/C\\/C");
    //Matcher bagMatcher = bagPattern.matcher(scanData);


    private ArrayList<String> listy;
    private ArrayAdapter<String> adapt;
    private EditText txtInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning);
        ListView livi = (ListView) findViewById(R.id.listOfBags);
        String[] items = {};
        listy = new ArrayList<>(Arrays.asList(items));
        adapt = new ArrayAdapter<String>(this,R.layout.list_items,R.id.txtItem,listy);
        livi.setAdapter(adapt);
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
//        Intent myIntent = new Intent(getApplicationContext(), Scan.class);
//        startActivityForResult(myIntent, 0);
//        return true;
//    }

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

    void addToListy(String text) {
        listy.add(text);
        adapt.notifyDataSetChanged();
        txtInput.setText("");
    }

    public void redirectToMenu (View v) {
        Intent home_act = new Intent(this, Home.class);
        startActivity(home_act);
    }
}
