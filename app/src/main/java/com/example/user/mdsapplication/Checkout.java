package com.example.user.mdsapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mdsapplication.utils.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Checkout extends AppCompatActivity {

    private MainReservation details;
    private Reservations reserve;
    private DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference();
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        createObject();
        createDetails();
        if (reserve.getBoardGame()!=null)
            createBoardGame();
        if (reserve.getProducts()!=null && reserve.getProducts().size()!=0) {
            for(Product p : reserve.getProducts()){
                if(p.getQuantity()>0){
                    createSpecialMentions();
                    addSpecialMentions();
                    break;
                }
            }
        }
        updateBaseOnSubmit();
    }



    public void createObject() {
         i=getIntent();
         String source=i.getStringExtra("Source");
         Log.v("Source",source);
         if(source.equals("SpecialMentionsPage"))
            reserve=(Reservations) i.getSerializableExtra("reservation");
         else
         {
            details=(MainReservation) i.getSerializableExtra("mainReservation");
            reserve=new Reservations(details);
         }
    }
    public void createDetails() {
        TextView userName = (TextView) findViewById(R.id.putName);
        TextView people = (TextView) findViewById(R.id.putPeople);
        TextView dateText = (TextView) findViewById(R.id.putDate);
        TextView timeText = (TextView) findViewById(R.id.putTime);
        TextView duration = (TextView) findViewById(R.id.putDuration);

        userName.setText(reserve.getMainDetails().getName());
        people.setText(String.valueOf(reserve.getMainDetails().getNoPers()));
        dateText.setText(reserve.getMainDetails().getDate());
        timeText.setText(reserve.getMainDetails().getTime());
        duration.setText(reserve.getMainDetails().getDuration()+"h");
    }

    public void createBoardGame() {
        LinearLayout boardGameLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        relativeParams.addRule(RelativeLayout.BELOW, R.id.details);
        relativeParams.leftMargin = (int) getResources().getDimension(R.dimen.marginCheckoutBg);
        relativeParams.rightMargin = (int) getResources().getDimension(R.dimen.marginCheckoutBg);



        boardGameLayout.setOrientation(LinearLayout.HORIZONTAL);

        boardGameLayout.setId(R.id.boardGame);
        boardGameLayout.setBackgroundColor(getResources().getColor(R.color.checkoutBg));
        boardGameLayout.setLayoutParams(relativeParams);
        RelativeLayout rootLayout = findViewById(R.id.rootLayout);
        rootLayout.addView(boardGameLayout);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        linearParams.setMargins( (int)getResources().getDimension(R.dimen.margin_min), (int)getResources().getDimension(R.dimen.margin_min), (int)getResources().getDimension(R.dimen.margin_min), (int)getResources().getDimension(R.dimen.margin_min));
        TextView boardGame = new TextView(this);
        TextView boardGameName = new TextView(this);
        boardGame.setLayoutParams(linearParams);
        boardGame.setText(getResources().getText(R.string.bg));
        boardGame.setTextColor(getResources().getColor(R.color.brown_product));
        boardGame.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimension(R.dimen.text_checkout));
        // boardGame.setTextSize(getResources().getDimension(R.dimen.text_medium));
        boardGame.setGravity(Gravity.LEFT);
        boardGameName.setGravity(Gravity.CENTER);

        boardGameName.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimension(R.dimen.text_checkout));
        //boardGameName.setTextSize(getResources().getDimension(R.dimen.text_medium));
        boardGameName.setLayoutParams(linearParams);
        boardGameName.setText(reserve.getBoardGame());
        boardGameLayout.addView(boardGame);
        boardGameLayout.addView(boardGameName);
    }

    public void createSpecialMentions() {

        LinearLayout specialMentionsLayout = new LinearLayout(this);

        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        if (reserve.getBoardGame()!=null)
            relativeParams.addRule(RelativeLayout.BELOW, R.id.boardGame);
        else
            relativeParams.addRule(RelativeLayout.BELOW, R.id.details);
        relativeParams.topMargin = (int)getResources().getDimension(R.dimen.marginCheckoutBg);
        relativeParams.leftMargin = (int)getResources().getDimension(R.dimen.marginCheckoutBg);
        relativeParams.rightMargin = (int)getResources().getDimension(R.dimen.marginCheckoutBg);

        specialMentionsLayout.setId(R.id.specialMentions);
        specialMentionsLayout.setLayoutParams(relativeParams);

        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        linearParams.setMargins((int)getResources().getDimension(R.dimen.margin_small),0,(int)getResources().getDimension(R.dimen.margin_small),0);

        specialMentionsLayout.setOrientation(LinearLayout.VERTICAL);
        specialMentionsLayout.setBackgroundColor(getResources().getColor(R.color.grey_50));
        RelativeLayout rootLayout = findViewById(R.id.rootLayout);
        rootLayout.addView(specialMentionsLayout);

        LinearLayout textBar = new LinearLayout(this);
        textBar.setLayoutParams(linearParams);
        TextView name = new TextView(this);
        TextView quantity = new TextView(this);
        TextView price = new TextView(this);

        name.setLayoutParams(linearParams);
        quantity.setLayoutParams(linearParams);
        price.setLayoutParams(linearParams);

        name.setText(getResources().getText(R.string.nameText));
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimension(R.dimen.text_checkout));
        //name.setTextSize(getResources().getDimension(R.dimen.text_medium));
        name.setTextColor(getResources().getColor(R.color.brown_product));
        name.setLayoutParams(linearParams);
        quantity.setText(getResources().getText(R.string.quantity));
        quantity.setTextColor(getResources().getColor(R.color.brown_product));
        quantity.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimension(R.dimen.text_checkout));
        //quantity.setTextSize(getResources().getDimension(R.dimen.text_medium));
        quantity.setLayoutParams(linearParams);
        quantity.setGravity(Gravity.CENTER);
        price.setText(getResources().getText(R.string.price));
        price.setTextColor(getResources().getColor(R.color.brown_product));
        price.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimension(R.dimen.text_checkout));
        // price.setTextSize(getResources().getDimension(R.dimen.text_medium));
        price.setLayoutParams(linearParams);
        price.setGravity(Gravity.RIGHT);

        textBar.addView(name);
        textBar.addView(quantity);
        textBar.addView(price);
        specialMentionsLayout.addView(textBar);
    }

    public void addSpecialMentions() {
        @SuppressLint("WrongViewCast") LinearLayout specialMentionsLayout = findViewById(R.id.specialMentions);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        linearParams.setMargins((int)getResources().getDimension(R.dimen.margin_min),0,(int)getResources().getDimension(R.dimen.margin_min),0);
        double sum = 0;
        for (Product p : reserve.getProducts()) {
            if(p.getQuantity()!=0) {

                sum += p.getPricePerUnit()*p.getQuantity();
                LinearLayout item = new LinearLayout(this);
                item.setLayoutParams(linearParams);

                TextView name = new TextView(this);
                name.setText(p.getName());
                name.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimension(R.dimen.text_checkout));
                //name.setTextSize(getResources().getDimension(R.dimen.text_medium));
                name.setLayoutParams(linearParams);

                TextView quantity = new TextView(this);
                quantity.setText("" + p.getQuantity());
                quantity.setGravity(Gravity.CENTER);
                quantity.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimension(R.dimen.text_checkout));
                //quantity.setTextSize(getResources().getDimension(R.dimen.text_medium));
                quantity.setLayoutParams(linearParams);

                TextView price = new TextView(this);
                price.setText("" + p.getPricePerUnit()*p.getQuantity());
                price.setGravity(Gravity.RIGHT);
                price.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimension(R.dimen.text_checkout));
                //price.setTextSize(getResources().getDimension(R.dimen.text_medium));
                price.setLayoutParams(linearParams);

                item.addView(name);
                item.addView(quantity);
                item.addView(price);
                specialMentionsLayout.addView(item);
            }
        }
        View horizontalLine = new View(this);
        horizontalLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, (int)getResources().getDimension(R.dimen.horizontalLine)));
        horizontalLine.setBackgroundColor(getResources().getColor(R.color.checkoutBg));
        specialMentionsLayout.addView(horizontalLine);

        LinearLayout totalBar = new LinearLayout(this);
        totalBar.setLayoutParams(linearParams);

        TextView total = new TextView(this);
        total.setLayoutParams(linearParams);
        total.setText(getResources().getText(R.string.total));
        total.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimension(R.dimen.text_total));
        // total.setTextSize(getResources().getDimension(R.dimen.text_medium));
        total.setTextColor(getResources().getColor(R.color.colorProduct));

        TextView totalPrice = new TextView(this);
        totalPrice.setLayoutParams(linearParams);
        totalPrice.setText("" + sum);
        totalPrice.setGravity(Gravity.RIGHT);
        totalPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimension(R.dimen.text_total));
        totalPrice.setTextColor(getResources().getColor(R.color.colorProduct));

        totalBar.addView(total);
        totalBar.addView(totalPrice);
        specialMentionsLayout.addView(totalBar);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setText(getResources().getText(R.string.payment));
    }

   public void onBackPressed()
    {
        String _dateBD = reserve.getMainDetails().getDate().replaceAll("/", "");
        String _time = reserve.getMainDetails().getTime().replaceAll(":","");
        mDatabase.child("BlockedTables").child(String.valueOf(reserve.getMainDetails().getTable())).child(_dateBD).child(_time).setValue(null);
         super.onBackPressed();
    }

    public void updateBaseOnSubmit() {
        Button submitButton=findViewById(R.id.submitButton);
        List<Product> auxProducts=new LinkedList<>();
        auxProducts=getListFromProducts(reserve.getProducts());

        final Reservations auxReservation=new Reservations(reserve.getMainDetails(),auxProducts);
        if(reserve.getBoardGame()!=null)
            auxReservation.setBoardGame(reserve.getBoardGame());


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("activeReservations").child(reserve.getMainDetails().markerDateDatabase())
                        .child(String.valueOf(reserve.getMainDetails().getTable())).child(reserve.getMainDetails().markerTimeDatabase()).setValue(auxReservation);
                mDatabase.child("BlockedTables").child(String.valueOf(reserve.getMainDetails().getTable())).child(reserve.getMainDetails().markerDateDatabase())
                        .child(reserve.getMainDetails().markerTimeDatabase()).removeValue() ;
                mDatabase.child("blockedBoardGames").child(reserve.getBoardGame()).child(reserve.getMainDetails().markerDateDatabase()).child(reserve.getMainDetails().getTime()).removeValue();
                Toast t = Toast.makeText(Checkout.this, getResources().getText(R.string.orderSucces), Toast.LENGTH_LONG);
                t.show();
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Constants.THREAD_SLEEP_CHECKOUT);
                            Intent i=new Intent(Checkout.this,ReservationPage.class);
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
    /*@Override
    public void onBackPressed() {
        String source=i.getStringExtra("Source");
        if(source.equals("SpecialMentionsPage"))
            mDatabase.child("blockedBoardGames").child(reserve.getBoardGame()).child(reserve.getMainDetails().markerDateDatabase()).child(reserve.getMainDetails().getTime()).removeValue();
        else{
            mDatabase.child("BlockedTables").child(String.valueOf(reserve.getMainDetails().getTable())).child(reserve.getMainDetails().markerDateDatabase())
                    .child(reserve.getMainDetails().markerTimeDatabase()).removeValue() ;
        }
        super.onBackPressed();
    }*/

    List<Product> getListFromProducts(List<Product> aux){
        List<Product> products=new LinkedList<>();
        for(Product p : aux)
            if(p.getQuantity()>0)
                products.add(p);
        return products;
    }
}

