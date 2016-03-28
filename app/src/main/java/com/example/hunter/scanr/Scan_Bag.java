package com.example.hunter.scanr;

//import android.content.Context; never used import
import android.content.Intent;
//import android.net.Uri; never used import
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
//import android.view.View; never used import
import android.view.WindowManager;
import android.widget.ArrayAdapter;
//import android.widget.Button; never used import
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

//import java.io.BufferedReader; ==================
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException; never used
//import java.io.FileOutputStream;      imports
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader; ===============
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Activity for loading the Scan_Bag layout
 * This activity is responsible for keeping track the number
 * of bags being scanned and saved into a list.
 *
 * @author William Montesdeoca, Korey McGill, Hunter Marshall
 * @version 3/16/2016
 * @since 1.0
 */
public class Scan_Bag extends AppCompatActivity {

    private static final String TAG = "ScanBagActivity";

    //PATTERNS for the Shelf and bag barcode
    Pattern shelfPattern = Pattern.compile("([A-Z])-([A-Z])-(\\d+)");
    Pattern bagPattern = Pattern.compile("\\/C\\/C\\d+\\/C\\/C");

    //VARIABLES
    private List<String> listOfBags = new ArrayList<String>();
    Shelf rack = new Shelf();
    private ArrayAdapter<String> adapt;
    private EditText txtInput;
    //private ListView viewText; COMMENTED OUT: never used.
    private String shelfId;
    private final long DELAY = 3000; // 10 nano second delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //stop keyboard popup
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Get the id from the scan-shelf activity
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        // check to see if bundle is empty
        if (!extrasBundle.isEmpty()) {
            // check to see if bundle contains the shelfID key
            boolean hasString = extrasBundle.containsKey("shelfID");
            if (hasString) {
                shelfId = extrasBundle.getString("shelfID");
                rack.setRoomCode(shelfId);
                rack.setContainers(listOfBags);
            } else {
                Log.e(TAG, "Error: Shelf ID was not passed to scan_bag activity");
            }
        } else {
            Log.e(TAG, "ERROR: Bundle was empty when passed to scan bag activity");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning_bag);

        ListView list = (ListView) findViewById(R.id.listOfBags);
        String[] items = {};
        listOfBags = new ArrayList<>(Arrays.asList(items));
        adapt = new ArrayAdapter<String>(this, R.layout.list_items, R.id.txtItem, listOfBags);
        list.setAdapter(adapt);
        txtInput = (EditText) findViewById(R.id.input);
        txtInput.addTextChangedListener(watchmen);
    }

    TextWatcher watchmen = new TextWatcher() {
        //Variable that helps keep track of when last input was received
        long lastInput = 0;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(final Editable s) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (System.currentTimeMillis() - lastInput >= DELAY) {
                        if (s.length() > 0) {
                            final String textToAdd = txtInput.getText().toString();

                            //if its a bag, add it to list
                            if (checkBag(textToAdd)) {
                                final String codeToAdd = textToAdd.replaceAll("/C", "");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        addToList(codeToAdd);

                                        //this set's text to null so further iterations won't occur.
                                        //technically this will be called 13(or however long the input)
                                        //times, need to find better method or prevent it (which this does)
                                        txtInput.setText("");
                                    }
                                });
                            }//end of ifbag

                            //if not bag, check if its a shelf
                            else {
                                if (checkShelf(textToAdd)) {
                                    //its the shelf, save and stop
                                    Gson gson = new Gson();
                                    String json = gson.toJson(rack);
                                    redirect(json);
                                    //clearList();
                                } else {
                                    //its not the shelf, clear and start over
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "ERROR: Rescan Initial Shelf When finished.", Toast.LENGTH_SHORT).show();
                                            txtInput.setText("");
                                        }
                                    });
                                    Log.e(TAG, "ERROR: onTextChanged error occured");
                                }
                            }
                        }//end of if length >=0 check
                    }//end of if lastinput check
                }//end of run
            }, DELAY);//end of runnable
            lastInput = System.currentTimeMillis();
        }
    };

    /**
     * This method will add the bag objects into the list
     *
     * @param text The string to associate the input string received
     *             from the barcode
     */
    void addToList(String text) {
        //first add bag to actual list of object
        rack.add(text);
        //this next list is just for display
        listOfBags.add(text);
        adapt.notifyDataSetChanged();
        Gson gson = new Gson();
        String json = gson.toJson(rack);
        showJson(json);
    }

    /**
     * Clear the list after saving...
     */
    void clearList() {
        listOfBags.clear();
        adapt.notifyDataSetChanged();
        txtInput.setText("");
    }

    /**
     * This method will check to see if the barcodes matches the patterns
     *
     * @param text The string to associate the input string received
     *             from the barcode
     */
    boolean checkBag(String text) {
        Matcher bagMatch = bagPattern.matcher(text);
        return bagMatch.matches();
    }

    /**
     * CHECK SHELF METHOD
     * Compares the pattern scanned to determine if it is a shelf.
     * Then it checks to see if it is the shelf that we are scanning bags on.
     * If it is then return true, otherwise return false.
     *
     * @param text - the pattern to checked
     * @return true if pattern is the shelf we are working on, otherwise returns false
     */
    boolean checkShelf(String text) {
        Matcher shelfMatch = shelfPattern.matcher(text);
        return text.equals(shelfId);
    }

    ///////////////////////////////////////////
    ///////////////////////////////////////////
    ///////////////////////////////////////////
    //SHOW JSON TEST METHOD FOR TESTING PURPOSES ONLY
    //delete when finished.
    void showJson(String jsonString) {
        Toast.makeText(getApplicationContext(), "The Rack Json Object: " + jsonString, Toast.LENGTH_LONG).show();
    }
    //////////////////////////////////////////
    //////////////////////////////////////////
    //////////////////////////////////////////

    /*
     * http://www.101apps.co.za/index.php/articles/passing-data-between-activities.html
     */
    void redirect(String jsonString) {
        Intent Loading_Act = new Intent(this, Loading.class);
        // Put string into a bundle and then pass the bundle to the new activity
        Bundle bundle = new Bundle();
        bundle.putString("jsonString", jsonString);
        Loading_Act.putExtras(bundle);
        startActivity(Loading_Act);
    }

    //code for william
//    public void sendButton(View v) {
//        Gson gson = new Gson();
//        String jSon = gson.toJson(rack);
//        send(jSon);
//    }
//
//    public void send(String json) {
//        Intent file = new Intent(Intent.ACTION_SEND);
//        file.setData((Uri.parse("mailto:")));
//        String [] to = {"kmmacgill@gmail.com"};
//        file.putExtra(Intent.EXTRA_EMAIL, to);
//        file.putExtra(Intent.EXTRA_SUBJECT, "This was sent from scanner gun");
//        file.putExtra(Intent.EXTRA_TEXT, json);
//        file.setType("message/rfc822");
//    }
}
