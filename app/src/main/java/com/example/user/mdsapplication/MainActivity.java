package com.example.user.mdsapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.mdsapplication.ui.activities.LoginActivity;
import com.example.user.mdsapplication.ui.activities.SplashActivity;
import com.example.user.mdsapplication.ui.activities.UserListingActivity;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {
    private Button loginBtn, chatBtn, callBtn, reserveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.ma_login_btn);
        reserveBtn = findViewById(R.id.ma_reserve_btn);
        chatBtn = findViewById(R.id.ma_chat_btn);
        callBtn = findViewById(R.id.ma_call_btn);

        checkForPermissions();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // if logged in redirect the user to user listing activity
            loginBtn.setVisibility(View.GONE);
            reserveBtn.setVisibility(View.VISIBLE);
            chatBtn.setOnClickListener(c ->
                UserListingActivity.startActivity(getApplicationContext()));
        }
        else {
            // otherwise redirect the user to login activity
            loginBtn.setVisibility(View.VISIBLE);
            reserveBtn.setVisibility(View.GONE);
            chatBtn.setOnClickListener(c -> LoginActivity.startIntent(MainActivity.this, "chatBtn"));
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO login
                LoginActivity.startIntent(MainActivity.this, "loginBtn");
            }
        });

        reserveBtn.setOnClickListener(c -> {
            Intent reserveIntent = new Intent(getApplicationContext(), ReservationPage.class);
            reserveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(reserveIntent);
        });
    }

    private void checkForPermissions() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);

        }
        else{
            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callSupport();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (permissions[0].equalsIgnoreCase
                        (Manifest.permission.CALL_PHONE)
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted.
                    callBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callSupport();
                        }
                    });
                } else {
                    // Permission denied. Stop the app.

                }
            }
        }
    }


    private void callSupport(){
        try {
            Uri callUri = Uri.parse("tel:0756455497");
            Intent callIntent = new Intent(Intent.ACTION_CALL, callUri);
            Toast.makeText(this, "Calling the support team!", Toast.LENGTH_SHORT).show();
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(callIntent);
        }catch(Exception ex)
        {
            Toast.makeText(this, "Couldn't make the call!", Toast.LENGTH_SHORT).show();
        }
    }
}


