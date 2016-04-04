package com.example.hunter.scanr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Report extends AppCompatActivity {
    EditText subject, message;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        subject = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String newSubject = subject.getText().toString();
                    String newMessage = message.getText().toString();

                    final Intent file = new Intent(Intent.ACTION_SEND);
                    file.setType("plain/text");
                    String[] to = {"kmmacgill@gmail.com"};
                    file.putExtra(Intent.EXTRA_EMAIL, to);
                    file.putExtra(Intent.EXTRA_SUBJECT, newSubject);
                    file.putExtra(Intent.EXTRA_TEXT, newMessage);
                    file.setType("message/rfc822");
                    startActivity(Intent.createChooser(file, "Choose Option to Send..."));
                } catch (Throwable t) {
                    Toast.makeText(Report.this, "Report failed please try again: " + t.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}



