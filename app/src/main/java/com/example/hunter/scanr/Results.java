package com.example.hunter.scanr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
    }

    public void directToSearch(View v) {
        Intent search_act = new Intent(this, Search.class);
        startActivity(search_act);
    }

    public void directToBagContent (View v) {
        Intent bag_act = new Intent(this, Bag.class);
        startActivity(bag_act);
    }

}
