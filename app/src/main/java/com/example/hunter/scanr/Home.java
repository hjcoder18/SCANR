package com.example.hunter.scanr;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // gets the action bar for the application
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.bstore_logo);

    }

    public void directToScanning(View v) {
        Intent intent = new Intent(this, Scan.class);
        startActivity(intent);
    }

    public void directToSearch(View v) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

}
