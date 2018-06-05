package com.example.user.mdsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;
import com.example.user.mdsapplication.utils.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Payment extends AppCompatActivity {
    private Reservations reserve;
    private double price;
    private Intent i;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        i=getIntent();
        reserve= (Reservations) i.getSerializableExtra("reserve");
        price=i.getDoubleExtra("price",0);
        Pay();
    }

    public void onBackPressed() {
        String source=i.getStringExtra("Source");
        if(source.equals("SpecialMentionsPage"))
            mDatabase.child("blockedBoardGames").child(reserve.getBoardGame()).child(reserve.getMainDetails().markerDateDatabase()).child(reserve.getMainDetails().getTime()).removeValue();
        else{
            mDatabase.child("BlockedTables").child(String.valueOf(reserve.getMainDetails().getTable())).child(reserve.getMainDetails().markerDateDatabase())
                    .child(reserve.getMainDetails().markerTimeDatabase()).removeValue() ;
        }
        super.onBackPressed();
    }

    public void Pay() {
        CardForm cardForm = (CardForm) findViewById(R.id.card_form);
        TextView textDesc = (TextView) findViewById(R.id.payment_amount);
        Button btnPay = (Button) findViewById(R.id.btn_pay);
        textDesc.setText("$"+price);
         btnPay.setText(String.format("Payer %s",textDesc.getText()));
        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                mDatabase.child("activeReservations").child(reserve.getMainDetails().markerDateDatabase())
                        .child(String.valueOf(reserve.getMainDetails().getTable())).child(reserve.getMainDetails().markerTimeDatabase()).setValue(reserve);
                mDatabase.child("BlockedTables").child(String.valueOf(reserve.getMainDetails().getTable())).child(reserve.getMainDetails().markerDateDatabase())
                        .child(reserve.getMainDetails().markerTimeDatabase()).removeValue();
                mDatabase.child("blockedBoardGames").child(reserve.getBoardGame()).child(reserve.getMainDetails().markerDateDatabase()).child(reserve.getMainDetails().getTime()).removeValue();
                Toast t = Toast.makeText(Payment.this, getResources().getText(R.string.orderSucces), Toast.LENGTH_LONG);
                t.show();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Constants.THREAD_SLEEP_CHECKOUT);
                            Intent i = new Intent(Payment.this, MainActivity.class);
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });
    }

}
