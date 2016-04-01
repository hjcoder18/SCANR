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
        if (isConnected()) {
            textView.setBackgroundColor(0xFF00CC00);
            textView.setText("You are connected!");
            sendPostRequest();

        } else {
            textView.setText("You are NOT connected!");
        }
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void directToScanShelf(View v) {
        Intent scan_act = new Intent(this, Scan_Shelf.class);
        startActivity(scan_act);
    }

    /**
     * SEND POST REQUEST: sends a post request to the url for processing.
     */
    public void sendPostRequest() {
        new PostClass(this).execute();
    }

    /**
     * PostClass - AsyncTask that takes care of Post requests
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
            try {
                //final TextView outputView = (TextView) findViewById(R.id.jsonContent);

                URL url = new URL("https://ustorewebsb.byui.edu/Ordering/Audit/RackData");

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                //set a timeout to avoid infinite waiting times
                connection.setConnectTimeout(5000); //5 seconds until a connection times out
                connection.setReadTimeout(10000); //10 seconds until a reading timeout occurs, meaning with connections already established.

                //String urlParameters = jsonString;
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(jsonString);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + jsonString);
                System.out.println("Response Code : " + responseCode);

                Loading.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //outputView.setText(output);
                        progress.dismiss();
                        startActivity(new Intent(Loading.this, Fail.class));
                        redirect.setEnabled(true);
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

        protected void onPostExecute() {
            progress.dismiss();
        }

    }
}
