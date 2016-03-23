package com.example.hunter.scanr;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
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
    /*
      Korey's Json file
      php-kormac.rhcloud.com/file.json
   */
    EditText urlText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_page);
        urlText = (EditText) findViewById(R.id.inputText);
        textView = (TextView) findViewById(R.id.isConnected);

        if (isConnected()) {
            textView.setBackgroundColor(0xFF00CC00);
            textView.setText("You are connected!");
        } else {
            textView.setText("You are NOT connected!");
        }
    }

    /**
     * GET Method
     * Allows the application to get content from the API
     *
     * @param line - the contents of the json file
     * @return line - the contents of the json file
     */
    public static String GET(String line) {
        HttpURLConnection connection = null;
        URL url = null;
        BufferedReader br = null;
        line = "";

        try {
            url = new URL("https://ustorewebsb.byui.edu/Ordering/Audit/");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();

            while((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            try {
                br.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    /**
     * IS CONNECTED METHOD
     * Determines if we are connected to the internet.
     *
     * @return true or false - if the network is connected return true, otherwise return false
     */
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    /**
     * HTTYPASYNCTASK
     */
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String...params) {
            String url = params[0];
            String data = GET(url);
            return data;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            urlText.setText(result);
        }
    }
}
