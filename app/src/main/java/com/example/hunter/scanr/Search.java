package com.example.hunter.scanr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Search extends AppCompatActivity {
    // testing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        //setContentView(R.layout.search);
    }

    public void directToResults (View v) {
        Intent intent = new Intent(this, Results.class);
        startActivity(intent);
    }

    public void redirectToMenu (View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
