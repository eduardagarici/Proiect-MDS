package com.example.user.mdsapplication.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.user.mdsapplication.R;
import com.example.user.mdsapplication.ui.fragments.LoginFragment;

public class LoginActivity  extends AppCompatActivity {
    private Toolbar mToolbar;
    private String from = "loginBtn";

    public static void startIntent(Context context){
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    public static void startIntent(Context context,int flags){
        Intent intent=new Intent(context,LoginActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    public static void startIntent(Context context, String from){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        //get the 'from'
        try{
            Intent intent = getIntent();
            from = intent.getStringExtra("from");
            if(from == null || from.length() == 0)
                from = "loginBtn";

        }catch(Exception ex){
            Log.e("LoginActivity ", ex.getMessage(), ex);
        }

        init(from);
    }

    private void bindViews(){
        mToolbar=(Toolbar) findViewById(R.id.toolbar);
    }

    private void init(String from){
        setSupportActionBar(mToolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_login,
                LoginFragment.newInstance(from),
                LoginFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }
}
