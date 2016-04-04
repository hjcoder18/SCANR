package com.example.hunter.scanr;

import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private final long DELAY = 10; // 10 nano second delay
    Button sendButton;
    TextView errors;

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
        sendButton = (Button) findViewById(R.id.sendReportButton);
        sendButton.setEnabled(false);
        errors = (TextView) findViewById(R.id.ErrorMessages);
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
                                errors.setText("");
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
                                    //enable the send button
                                    errors.setText("");
                                    sendButton.setEnabled(true);
                                } else {
                                    //its not the shelf, clear and start over
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            errors.setText("ERROR: Unrecognized Barcode, scan rack " + shelfId + " or scan more bags.");
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
     * SEND REPORT
     * Sends the report to the University Store API
     *
     * @param v - needed for button
     */
    public void sendReport(View v) {
        if (isConnected()) {
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isConnected()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Scan_Bag.this);
                        builder.setMessage("Internet Connection Required.. Please try again").setCancelable(false).setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.setTitle("Network Error!");
                        alert.show();
                    } else {
                        redirect();
                    }
                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Internet Connection Required... Please try again.").setCancelable(false).setNegativeButton("CANCEL",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("Network Error!");
            alert.show();
        }
    }

    /**
     * IS CONNECTED
     * Tests to see if the scanner gun is connected to the wifi. If it is then it returns true,
     * otherwise it returns false.
     *
     * @return true or false
     */
    public boolean isConnected() {
        ConnectivityManager internet = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = internet.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

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
     * CHECK BAG
     * This method will check to see if the barcodes matches the patterns
     *
     * @param text - The string to associate the input string received
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

    /**
     * REDIRECT
     * Creates a json string of the rack content, and then
     * directs the application to the loading activity with the json string.
     */
    public void redirect() {
        //create the json string...
        Gson gson = new Gson();
        String jsonString = gson.toJson(rack);

        Intent Loading_Act = new Intent(this, Loading.class);
        // Put string into a bundle and then pass the bundle to the new activity
        Bundle bundle = new Bundle();
        bundle.putString("jsonString", jsonString);
        Loading_Act.putExtras(bundle);
        startActivity(Loading_Act);
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
                startActivity(new Intent(Scan_Bag.this, About.class));
                break;
            case R.id.report_bug:
                startActivity(new Intent(Scan_Bag.this, Report.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
