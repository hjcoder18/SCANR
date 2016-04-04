package com.example.hunter.scanr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SEARCH BAG CLASS
 * All the variables and methods needed to allow the user to scan a bag and then view the content
 * for that bag. It does this by having the user scan a bag, we connect with the University Store's
 * API and it sends us a json object of the bag information. The bag content consist of the
 * following:
 *      Student's Name
 *      Student's ID
 *      Bag ID - the id of that bag
 *      Room Code - the room the bag should be in
 *      Rack ID - the rack the bag should be on
 */
public class Search_Bag extends AppCompatActivity {

    //non-UI stuff
    private static final String TAG = "SearchBagActivity";
    Pattern bagPattern = Pattern.compile("\\/C\\/C\\d+\\/C\\/C");
    private final long DELAY = 10; // 10 nano second delay
    String bagCode;
    String incomplete_url = "https://ustorewebsb.byui.edu/Ordering/Audit/GetItem?itemId=";
    private ProgressDialog progress;

    //UI stuff
    private EditText input;
    TextView name;
    TextView Inum;
    TextView bagId;
    TextView roomCode;
    TextView errors;
    Button clearButton;
    Fail failMessage;
    boolean timeout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //stop keyboard popup
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search__bag);

        //Main initialization of all fields...
        input = (EditText) findViewById(R.id.input);
        input.addTextChangedListener(watcher);
        name = (TextView) findViewById(R.id.result_name);
        Inum = (TextView) findViewById(R.id.result_inum);
        bagId = (TextView) findViewById(R.id.result_bag_id);
        roomCode = (TextView) findViewById(R.id.result_room_code);
        errors = (TextView) findViewById(R.id.ErrorMessages);
        clearButton = (Button) findViewById(R.id.clearButton);
    }

    TextWatcher watcher = new TextWatcher() {
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
                            final String rawBagCode = input.getText().toString();

                            //if its a bag, request info from the API
                            if (checkBag(rawBagCode)) {
                                errors.setText("");
                                bagCode = rawBagCode.replaceAll("/C", "");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        sendGetRequest();
                                        input.setText("");
                                    }
                                });
                            }//end of ifbag

                            //if not bag, put out error message
                            else {
                                //its not the shelf, clear and start over
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        errors.setText("ERROR: Unrecognized Barcode");
                                        ClearData();
                                        input.setText("");
                                    }
                                });
                                Log.e(TAG, "ERROR: onTextChanged error occured");
                            }
                        }//end of if length >=0 check
                    }//end of if lastinput check
                }//end of run
            }, DELAY);//end of runnable
            lastInput = System.currentTimeMillis();
        }
    };

    /**
     * CLEAR FIELDS
     * Clears the fields of the bag content including the error messsages that may have poped up
     * @param v - needed for button
     */
    public void ClearFields(View v) {
        name.setText("");
        Inum.setText("");
        bagId.setText("");
        roomCode.setText("");
        errors.setText("");
    }

    /**
     * CLEAR DATA
     * Clears the data of the bag content
     */
    public void ClearData() {
        name.setText("");
        Inum.setText("");
        bagId.setText("");
        roomCode.setText("");
    }

    private void displaySearchFailure() {
        if (timeout) {
            startActivity(new Intent(Search_Bag.this, Search_Fail.class));
        }
        else {
            startActivity(new Intent(Search_Bag.this, Search_Fail.class));
        }
    }

    /**
     * CHECK BAG
     * Checks the bar code of the object being scanned and determines if it is a bag. Returns true
     * if it is a bag, otherwise returns false
     * @param text - the barcode that was scanned
     * @return true or false
     */
    boolean checkBag(String text) {
        Matcher bagMatch = bagPattern.matcher(text);
        return bagMatch.matches();
    }

    /**
     * SEND GET REQUEST
     * Sends a get request to the url for processing.
     */
    public void sendGetRequest() {
        new GetClass(this).execute();
    }

    /**
     * Get CLASS
     * Takes care of get requests
     */
    private class GetClass extends AsyncTask<String, Void, Void> {

        private final Context context;

        public GetClass(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
            progress.setTitle("Looking for that bag now");
            progress.setMessage("Please Wait...");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection c = null;
            try {
                URL url = new URL(incomplete_url + bagCode);

                c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setRequestProperty("Content-Length", "0");
                c.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                c.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(5000);
                c.setReadTimeout(10000);
                c.connect();
                int status = c.getResponseCode();
                if (status != 200) {
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                    displaySearchFailure();
                }
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();

                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                String theJsonString = "";
                String line = "";

                while ((line = br.readLine()) != null) {
                    theJsonString = theJsonString + line;
                }

                JSONObject jo = new JSONObject(theJsonString);
                final String fname = jo.get("studentFirstName").toString();
                final String lname = jo.get("studentLastName").toString();
                final String bid = jo.get("bagId").toString();
                final String sid = jo.get("studentID").toString();
                final String shid = jo.get("shelfID").toString();

                br.close();

                Search_Bag.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        name.setText(fname + " " + lname);
                        Inum.setText(sid);
                        bagId.setText(bid);
                        roomCode.setText(shid);
                        progress.dismiss();
                    }
                });
            } catch (java.net.SocketTimeoutException e) {
                progress.dismiss();
                timeout = true;
                displaySearchFailure();
                e.printStackTrace();
            } catch (IOException e) {
                progress.dismiss();
                displaySearchFailure();
                e.printStackTrace();
            } catch (JSONException e) {
                progress.dismiss();
                displaySearchFailure();
                e.printStackTrace();
            }
            return null;
        }
    }
}
