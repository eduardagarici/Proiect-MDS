package com.example.user.mdsapplication;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.LinkedList;

public class Checkout extends AppCompatActivity {

    //Reservations rezervation = getIntent().getSerializableExtras( "Reservation" );
    private Reservations reserve;
    private DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        createObject();
        createDetails();
        if (reserve.isChosenBoardGame())
            createBoardGame();
        if (reserve.hasSpecialMentions()) {
            createSpecialMentions();
            addSpecialMentions();
        }
        updateBaseOnSubmit();
    }

    public void createObject() {
        Date date = new Date(2019, 10, 20);
        LinkedList<Product> products = new LinkedList<>();
        products.add(new Product("Cola", 2, 250.30));
        products.add(new Product("fanta", 3, 190));
        reserve = new Reservations("Gigel", 5, date, 3, "Catan", products);
    }

    public void createDetails() {
        TextView userName = (TextView) findViewById(R.id.putName);
        TextView people = (TextView) findViewById(R.id.putPeople);
        TextView dateText = (TextView) findViewById(R.id.putDate);
        TextView duration = (TextView) findViewById(R.id.putDuration);

        userName.setText(reserve.getUserName());
        people.setText(""+reserve.getNoOfPersons());
        dateText.setText(reserve.getDate());
        duration.setText("" + reserve.getDuration());
    }

    public void createBoardGame() {
        LinearLayout boardGameLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        relativeParams.addRule(RelativeLayout.BELOW, R.id.details);
        relativeParams.topMargin = 100;
        relativeParams.leftMargin = 125;
        relativeParams.rightMargin = 125;

        boardGameLayout.setOrientation(LinearLayout.HORIZONTAL);

        boardGameLayout.setId(R.id.boardGame);
        boardGameLayout.setLayoutParams(relativeParams);
        RelativeLayout rootLayout = findViewById(R.id.rootLayout);
        rootLayout.addView(boardGameLayout);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        linearParams.setMargins(5,5,5,5);
        TextView boardGame = new TextView(this);
        TextView boardGameName = new TextView(this);
        boardGame.setLayoutParams(linearParams);
        boardGame.setText("BoardGame");
        boardGame.setGravity(Gravity.CENTER);
        boardGameName.setGravity(Gravity.CENTER);

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

        if (reserve.isChosenBoardGame())
            relativeParams.addRule(RelativeLayout.BELOW, R.id.boardGame);
        else
            relativeParams.addRule(RelativeLayout.BELOW, R.id.details);
        relativeParams.topMargin = 100;
        relativeParams.leftMargin = 100;
        relativeParams.rightMargin = 100;

        specialMentionsLayout.setId(R.id.specialMentions);
        specialMentionsLayout.setLayoutParams(relativeParams);

        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        linearParams.setMargins(10,0,10,0);

        specialMentionsLayout.setOrientation(LinearLayout.VERTICAL);
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

        name.setText("Name");
        name.setLayoutParams(linearParams);
        quantity.setText("Quantity");
        quantity.setLayoutParams(linearParams);
        quantity.setGravity(Gravity.CENTER);
        price.setText("Price");
        price.setLayoutParams(linearParams);
        price.setGravity(Gravity.RIGHT);

        textBar.addView(name);
        textBar.addView(quantity);
        textBar.addView(price);
        specialMentionsLayout.addView(textBar);
    }

    public void addSpecialMentions() {
        LinearLayout specialMentionsLayout = findViewById(R.id.specialMentions);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );

        double sum = 0;
        for (Product p : reserve.getProducts()) {
            sum += p.getPrice();
            LinearLayout item = new LinearLayout(this);
            item.setLayoutParams(linearParams);

            TextView name = new TextView(this);
            name.setText(p.getName());
            name.setLayoutParams(linearParams);

            TextView quantity = new TextView(this);
            quantity.setText("" + p.getQuantity());
            quantity.setGravity(Gravity.CENTER);
            quantity.setLayoutParams(linearParams);

            TextView price = new TextView(this);
            price.setText("" + p.getPrice());
            price.setGravity(Gravity.RIGHT);
            price.setLayoutParams(linearParams);

            item.addView(name);
            item.addView(quantity);
            item.addView(price);
            specialMentionsLayout.addView(item);
        }
        View horizontalLine = new View(this);
        horizontalLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 5));
        horizontalLine.setBackgroundColor(0xFF00FF00);
        specialMentionsLayout.addView(horizontalLine);

        LinearLayout totalBar = new LinearLayout(this);
        totalBar.setLayoutParams(linearParams);

        TextView total = new TextView(this);
        total.setLayoutParams(linearParams);
        total.setText("Total:");

        TextView totalPrice = new TextView(this);
        totalPrice.setLayoutParams(linearParams);
        totalPrice.setText("" + sum);
        totalPrice.setGravity(Gravity.RIGHT);

        totalBar.addView(total);
        totalBar.addView(totalPrice);
        specialMentionsLayout.addView(totalBar);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setText("Proceed to payment");
    }

    public void updateBaseOnSubmit() {
        Button submitButton=findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("activeReservations").push().setValue(reserve);
            }
        });
    }
}