package com.example.hunter.scanr;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search_Bag extends AppCompatActivity {

    //non-UI stuff
    private static final String TAG = "SearchBagActivity";
    Pattern bagPattern = Pattern.compile("\\/C\\/C\\d+\\/C\\/C");
    private final long DELAY = 10; // 10 nano second delay
    String bagCode;
    String incomplete_url = "https://ustorewebsb.byui.edu/Ordering/Audit/GetItem?itemId=";
    private ProgressDialog progress;
    Bag studentBag;

    //UI stuff
    private EditText input;
    TextView name;
    TextView Inum;
    TextView bagId;
    TextView roomCode;
    TextView errors;
    TextView output;
    Button clearButton;

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
        //studentBag = new Bag();
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

    boolean checkBag(String text) {
        Matcher bagMatch = bagPattern.matcher(text);
        return bagMatch.matches();
    }

    /**
     * SEND GET REQUEST sends a get request to the url for processing.
     */
    public void sendGetRequest() {
        new GetClass(this).execute();
    }

    /**
     * Get CLASS - takes care of get requests
     */
    private class GetClass extends AsyncTask<String, Void, Void> {

        private final Context context;

        public GetClass(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
            progress.setMessage("Loading");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(incomplete_url + bagCode);

                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                //String urlParameters = "fizz=buzz";
                c.setRequestMethod("GET");
                c.setRequestProperty("Content-Length", "0");
                c.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                c.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(5000);
                c.setReadTimeout(5000);
                c.connect();
                int status = c.getResponseCode();

//                switch(status) {
//                    case 200:
//                    case 201:
//                        BufferedReader buffRead = new BufferedReader(new InputStreamReader(c.getInputStream()));
//                        StringBuilder bobTheBuilder = new StringBuilder();
//                        String line;
//                        while ((line = buffRead.readLine()) != null) {
//                            bobTheBuilder.append(line+"\n");
//                        }
//                        buffRead.close();
//                        String results = bobTheBuilder.toString();

                        //System.out.println("\nSending 'POST' request to URL : " + url);
                        //System.out.println("Post parameters : " + urlParameters);
                        //System.out.println("Response Code : " + status);

                        final StringBuilder output = new StringBuilder("Request URL " + url);
                        //output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                        //output.append(System.getProperty("line.separator") + "Response Code " + status);
                        //output.append(System.getProperty("line.separator") + "Type " + "GET");
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        String line = "";
                        StringBuilder responseOutput = new StringBuilder();
                        System.out.println("output===============" + br);
                        while ((line = br.readLine()) != null) {
                            responseOutput.append(line);
                        }
                        br.close();
                //output.append(responseOutput.toString());
                String toConvert = output.toString();
                Gson gson = new Gson();
                studentBag = gson.fromJson(toConvert, Bag.class);
                System.out.println(studentBag);
                Search_Bag.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        name.setText(studentBag.getStudentName());

                        progress.dismiss();
                }
                });


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }

}
