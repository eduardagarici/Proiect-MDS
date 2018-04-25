package com.example.user.mdsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        ReservationsTest rezervare1 =  (ReservationsTest) i.getSerializableExtra("sampleObject");
        Log.d("nume",rezervare1.getName());


    }

}


