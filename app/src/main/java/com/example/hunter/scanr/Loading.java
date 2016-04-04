package com.example.hunter.scanr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * LOADING CLASS
 * Allows the user to get content from the API and allows the user to check to see if
 * the application is connected to the internet.
 */
public class Loading extends AppCompatActivity {

    private static final String TAG = "LoadingActivity";
    TextView textView;
    String jsonString;
    Button redirect;
    Boolean success;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //grab the json file from scan-bag
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();

        // check to see if bundle is empty
        if (!extrasBundle.isEmpty()) {
            // check to see if bundle contains the shelfID key
            boolean hasString = extrasBundle.containsKey("jsonString");
            if (hasString) {
                jsonString = extrasBundle.getString("jsonString");
            } else {
                Log.e(TAG, "Error: Shelf ID was not passed to scan_bag activity");
            }
        } else {
            Log.e(TAG, "ERROR: Bundle was empty when passed to scan bag activity");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_page);
        textView = (TextView) findViewById(R.id.isConnected);
        redirect = (Button) findViewById(R.id.continueButton);
        redirect.setEnabled(false);
        success = false;
        if (isConnected()) {
            textView.setBackgroundColor(0xFF00CC00);
            textView.setText("Wifi connected!");
            sendPostRequest();
        } else {
            textView.setText("Wifi not connected!");
            displayResult();
        }
    }

    /**
     * DISPLAY RESULTS
     *
     */
    private void displayResult() {
        if (success) {
            startActivity(new Intent(Loading.this, Success.class));
            Loading.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    redirect.setEnabled(true);
                }
            });
        }
        else {
            startActivity(new Intent(Loading.this, Fail.class));
            Loading.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    redirect.setEnabled(true);
                }
            });
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
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // if connected return true, otherwise return false
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    /**
     * DIRECT TO SCAN SHELF
     * Directs the application to the scan_shelf activity.
     *
     * @param v - needed for button
     */
    public void directToScanShelf(View v) {
        Intent scan_act = new Intent(this, Scan_Shelf.class);
        startActivity(scan_act);
    }

    /**
     * SEND POST REQUEST
     * Sends a post request to the url for processing.
     */
    public void sendPostRequest() {
        new PostClass(this).execute();
    }

    /**
     * POST CLASS
     * AsyncTask that takes care of Post requests
     */
    private class PostClass extends AsyncTask<String, Void, Void> {
        private final Context context;
        public PostClass(Context c){
            this.context = c;
        }
        protected void onPreExecute(){
            progress = new ProgressDialog(this.context);
            progress.setTitle("Trying to connect to server");
            progress.setMessage("Please wait...");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection connection = null;

            try {
                //final TextView outputView = (TextView) findViewById(R.id.jsonContent);

                URL url = new URL("https://ustorewebsb.byui.edu/Ordering/Audit/RackData");

                connection = (HttpURLConnection)url.openConnection();

                //set a timeout to avoid infinite waiting times
                connection.setConnectTimeout(5000); //5 seconds until a connection times out
                connection.setReadTimeout(10000); //10 seconds until a reading timeout occurs, meaning with connections already established.

                //String urlParameters = jsonString;
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                try {
                    DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                    dStream.writeBytes(jsonString);
                    dStream.flush();
                    dStream.close();
                } catch (java.net.SocketTimeoutException e) {
                    connection.disconnect();
                    progress.dismiss();
                    success = false;
                    e.printStackTrace();
                }
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    success = true;
                }

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + jsonString);
                System.out.println("Response Code : " + responseCode);

                Loading.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        displayResult();
                        redirect.setEnabled(true);
                    }
                });
                connection.disconnect();
            }catch (IOException e) {
                connection.disconnect();
                progress.dismiss();
                success = false;
                e.printStackTrace();
            }

            if (!success) {
                displayResult();
            }

            return null;
        }

        protected void onPostExecute() {
            progress.dismiss();
        }

    }
}
