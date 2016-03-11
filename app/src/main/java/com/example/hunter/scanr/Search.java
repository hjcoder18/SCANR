package com.example.hunter.scanr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Search extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
    }

    public void directToBag_Content (View v) {
        Intent bag_act = new Intent(this, Bag.class);
        startActivity(bag_act);
    }

    public void redirectToMenu (View v) {
        Intent home_act = new Intent(this, Home.class);
        startActivity(home_act);
    }
}
