package com.example.hunter.scanr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Scan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning);
    }

    public void redirectToMenu (View v) {
        Intent home_act = new Intent(this, Home.class);
        startActivity(home_act);
    }
}
