package com.example.hunter.scanr;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scan_Bag extends AppCompatActivity {

    private static final String TAG = "ScanBagActivity";

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaTutorial";

    //PATTERNS
    Pattern shelfPattern = Pattern.compile("([A-Z])-([A-Z])-(\\d+)");
    Pattern bagPattern = Pattern.compile("\\/C\\/C\\d+\\/C\\/C");

    //VARIABLES
    private List<String> listy = new ArrayList<String>();
    private ArrayAdapter<String> adapt;
    private EditText txtInput;
    private Button save, load;
    private TextView viewText;
    private String shelfId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        File dir = new File(path);
        dir.mkdirs();

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

    public void buttonSave (View v) {
        File newFile = new File(path + "savedFile.txt");
        String [] saveText = String.valueOf(txtInput.getText()).split(System.getProperty("line.separator"));

        txtInput.setText("");

        Toast.makeText(getApplicationContext(), "List was Saved!", Toast.LENGTH_LONG).show();

        Save (newFile, saveText);
    }

    public void buttonLoad (View v) {
        File newFile =  new File(path + "savedFile.txt");
        String [] loadText = Load(newFile);

        String finalString = "";

        for (int i = 0; i < loadText.length; i++) {
            finalString += loadText[i] + System.getProperty("line.separator");
        }

        viewText.setText(finalString);
    }

    public static void Save (File file, String [] data) {
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {e.printStackTrace();}
        try{
            try{
                for (int i = 0; i < data.length; i++) {
                    fos.write(data[i].getBytes());
                    if (i < data.length - 1) {
                        fos.write("\n".getBytes());
                    }
                }
            } catch (IOException e) {e.printStackTrace();}
        }
        finally {
                try{
                    fos.close();
                } catch (IOException e) {e.printStackTrace();}
        }
    }

    public static String [] Load(File file) {
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String test;
        int num = 0;

        try{
            while ((test = br.readLine()) != null) {
                num++;
            }
        } catch (IOException e) {e.printStackTrace();}

        try{
            fis.getChannel().position(0);
        } catch (IOException e) {e.printStackTrace();}

        String [] array = new String[num];

        String line;
        int count = 0;
        try{
            while ((line = br.readLine()) != null) {
                array[count] = line;
                count++;
            }
        } catch (IOException e) {e.printStackTrace();}

        return array;
    }
}
