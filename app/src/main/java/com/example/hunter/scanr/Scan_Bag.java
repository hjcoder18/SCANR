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
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Activity for loading the Scan_Bag layout
 * <p/>
 * This activity is responsible for keeping track the number
 * of bags being scanned and saved into a list.
 *
 * @author William Montesdeoca, Kory McGill, Hunter Marshall
 * @version 3/16/2016
 * @since 1.0
 */

public class Scan_Bag extends AppCompatActivity {

    private static final String TAG = "ScanBagActivity";

    //public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaTutorial";

    //PATTERNS
    Pattern shelfPattern = Pattern.compile("([A-Z])-([A-Z])-(\\d+)");
    Pattern bagPattern = Pattern.compile("\\/C\\/C\\d+\\/C\\/C");

    //VARIABLES
    private List<String> listOfBags = new ArrayList<String>();
    private ArrayAdapter<String> adapt;
    private EditText txtInput;
    private ListView viewText;
    private Button save, load;
    private String shelfId;

    //timer and delay for textwatcher purposes
    private Timer timer = new Timer();
    private final long DELAY = 10; // 10 nano second delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //create the save file
        File dir = new File(getFilesDir(), "txtFile.txt");
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
        Toast.makeText(Scan_Bag.this, "the shelf id is - " + shelfId, Toast.LENGTH_SHORT).show();

        save = (Button) findViewById(R.id.saveFile);
        load = (Button) findViewById(R.id.loadFile);

        ListView list = (ListView) findViewById(R.id.listOfBags);
        String[] items = {};
        listOfBags = new ArrayList<>(Arrays.asList(items));
        adapt = new ArrayAdapter<String>(this, R.layout.list_items, R.id.txtItem, listOfBags);
        list.setAdapter(adapt);
        txtInput = (EditText) findViewById(R.id.input);
        txtInput.addTextChangedListener(watchmen);
    }

    TextWatcher watchmen = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (timer != null) {
                timer.cancel();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            //while loop, while not equal to shelfpattern
            if (s.length() > 0) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        final String textToAdd = txtInput.getText().toString();
                        boolean ready = check(textToAdd);
                        if (ready) {
                            final String codeToAdd = textToAdd.replaceAll("/C", "");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    addToListy(codeToAdd);
                                }
                            });
                        } else if (!ready) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtInput.setText("");
                                }
                            });
                            Log.e(TAG, "ERROR: onTextChanged error occured");
                        } else {
                            Log.e(TAG, "ERROR: shouldn't happen ever...");
                        }
                    }
                }, DELAY);
            }
        }
        //after while loop save
    };

    /**
     * This method will add the bag objects into the list
     *
     * @param text The string to associate the input string received
     *             from the barcode
     */
    void addToListy(String text) {
        listOfBags.add(text);
        adapt.notifyDataSetChanged();
        txtInput.setText("");
    }

    /**
     * Clear the list after saving...
     */
    void clearListy() {
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
    boolean check(String text) {
        Matcher bagmatch = bagPattern.matcher(text);
        if (bagmatch.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method will add the Load functionality to the Save button
     *
     * @param v The View object that is associate it with the save
     *          button on the screen
     */
    public void buttonSave (View v) {
        File newFile = new File(getFilesDir() + "savedFile.txt");
        String [] saveText = listOfBags.toArray(new String[listOfBags.size()]);

        txtInput.setText("");

        Save(newFile, saveText);

        Toast.makeText(getApplicationContext(), "List was Saved! clearing list", Toast.LENGTH_SHORT).show();

        clearListy();
    }

    /**
     * This method will add the Load functionality to the Load button
     *
     * @param v The View object that is associate it with the load
     *          button on the screen
     */
    public void buttonLoad (View v) {
        File newFile =  new File(getFilesDir() + "savedFile.txt");
        String [] loadText = Load(newFile);
        String finalString = "";

        for (int i = 0; i < loadText.length; i++) {
            addToListy(loadText[i]);
        }
    }

    /**
     * This method will save the input of the user
     *
     * @param file The File object that input data is saved in
     */
    public static void Save(File file, String[] data) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                for (int i = 0; i < data.length; i++) {
                    fos.write(data[i].getBytes());
                    if (i < data.length - 1) {
                        fos.write("\n".getBytes());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method will display the input of the user
     *
     * @param file The File object that input data is displayed
     */
    public static String[] Load(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String test;
        int num = 0;

        try {
            while ((test = br.readLine()) != null) {
                num++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fis.getChannel().position(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] array = new String[num];

        String line;
        int count = 0;
        try {
            while ((line = br.readLine()) != null) {
                array[count] = line;
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return array;
    }
}
