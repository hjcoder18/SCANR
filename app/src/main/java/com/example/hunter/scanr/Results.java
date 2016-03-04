package com.example.hunter.scanr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by kmmac on 3/2/2016.
 */
public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_return);
    }

    public void directToSearch(View v) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void directToBagContent (View v) {
        Intent intent = new Intent(this, Bag.class);
        startActivity(intent);
    }

}
