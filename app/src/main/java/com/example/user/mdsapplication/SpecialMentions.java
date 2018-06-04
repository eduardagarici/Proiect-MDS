package com.example.user.mdsapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.user.mdsapplication.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpecialMentions extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Animation fade_in,fade_out;
    private ViewFlipper viewFlipper;
    private ListView lvProduct;
    private ProductListAdapter adapter;
    private List<Product> mProductList;
    private List<Product> drinks=new LinkedList<>();
    private List<Product> snacks=new LinkedList<>();
    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;
    private List<BoardGame> boardGames=new ArrayList<>();
    private List<String> boardGamesNames=new ArrayList<>();
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=database.getReference();
    private MainReservation details;
    private boolean buttonPressed=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_mentions);

        Intent i=getIntent();
        Log.v("SourceSpec",i.getStringExtra("Source"));
        details=(MainReservation) i.getSerializableExtra("mainReservation");

        setSlideShow();
        getDataBaseData();
        sendIntent();

    }

    public void sendIntent(){
        Button submitButton=findViewById(R.id.submitButtonSpecial);
        final Spinner spinner=findViewById(R.id.spinner);
        Long tsLong = System.currentTimeMillis()/1000;
        final String ts = tsLong.toString();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPressed=true;
                BlockedBoardGame blockedBoardGame=new BlockedBoardGame(details.getDate(),details.getTime(),details.getDuration(),ts);
                databaseReference.child("blockedBoardGames").child(spinner.getSelectedItem().toString())
                        .child(details.markerDateDatabase()).child(blockedBoardGame.getTime()).setValue(blockedBoardGame);
                Reservations reserve=new Reservations(details,spinner.getSelectedItem().toString(),mProductList);
                Intent i=new Intent(SpecialMentions.this,Checkout.class);
                i.putExtra("Source","SpecialMentionsPage");
                i.putExtra("reservation",reserve);
                startActivity(i);
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Constants.THREAD_SLEEP_SPECIAL);
                            buttonPressed=false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });

    }

    public void setSlideShow(){
       viewFlipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper);
        fade_in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);
        viewFlipper.setInAnimation(fade_in);
        viewFlipper.setOutAnimation(fade_out);
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(Constants.SLIDESHOW_FLIP);
        viewFlipper.startFlipping();
    }
    public void setListView(){
        lvProduct=findViewById(R.id.ListView);
        mProductList=new LinkedList<>();
        for(Product d : drinks){
            if(!mProductList.contains(d))
                mProductList.add(d);
        }
        for(Product s : snacks){
            if(!mProductList.contains(s))
                mProductList.add(s);
        }

        adapter=new ProductListAdapter(getApplicationContext(),this,mProductList);
        lvProduct.setAdapter(adapter);

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"it works",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setBoardGame(){
        boardGamesNames.add("none");
        for (BoardGame b : boardGames) {
            if(!boardGamesNames.contains(b.getName()))
                boardGamesNames.add(b.getName());
        }

        spinner=findViewById(R.id.spinner);
        spinnerAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,boardGamesNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        spinner.setBackgroundResource(0);

        String item = parent.getItemAtPosition(position).toString();
        for(final BoardGame boardGame : boardGames){
            if(boardGame.getName().equals(item)){

                databaseReference.child("blockedBoardGames").child(boardGame.getName()).child(details.markerDateDatabase()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        for (DataSnapshot child : children) {
                            BlockedBoardGame value = child.getValue(BlockedBoardGame.class);
                            if (timeBetween(value.getTime(), details.getTime(), value.getDuration(), details.getDuration())) {
                                checkBoardGame(boardGame);
                            }
                            else {
                                if(!buttonPressed)
                                    unaivalableToast();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                checkBoardGameReservations(boardGame);
            }
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        TextView choseBoardGame=(TextView)findViewById(R.id.boardGame);

    }

    public void checkBoardGameReservations(final BoardGame boardGame){
        databaseReference.child("boardGames").child(boardGame.getName()).child("reservations").child(details.markerDateDatabase()).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child : children)
                {
                    BoardGameReservation value = child.getValue(BoardGameReservation.class);
                    if(timeBetween(value.getTime(),details.getTime(),value.getDuration(),details.getDuration())){
                        checkBoardGame(boardGame);
                    }
                    else{
                       unaivalableToast();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void unaivalableToast(){
        Toast t;
        LinearLayout spinLayout=findViewById(R.id.chooseBoardGame);
        t = Toast.makeText(SpecialMentions.this, R.string.unavailableGame, Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER, spinLayout.getScrollX(), spinLayout.getScrollY());
        t.show();
        spinner.setSelection(0);
    }

    public void getDataBaseData(){
        databaseReference.child("boardGames").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child :children){
                    BoardGame boardGame=child.getValue(BoardGame.class);
                    boardGames.add(boardGame);
                }
                setBoardGame();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
       databaseReference.child("drinks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child : children){
                    Product drink=child.getValue(Product.class);
                    drinks.add(drink);
                }
                databaseReference.child("food").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children=dataSnapshot.getChildren();
                        for(DataSnapshot child : children){
                            Product snack=child.getValue(Product.class);
                            snacks.add(snack);
                            setListView();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void checkBoardGame(BoardGame boardGame){
        ShapeDrawable redBorder = new ShapeDrawable();
        redBorder.setShape(new RectShape());
        redBorder.getPaint().setColor(Color.RED);
        redBorder.getPaint().setStrokeWidth(10f);
        redBorder.getPaint().setStyle(Paint.Style.STROKE);

        Toast t;
        LinearLayout spinLayout=findViewById(R.id.chooseBoardGame);
        if(boardGame.getMaxPlayers()<details.getNoPers())
        {
            t=Toast.makeText(this,R.string.maxPlayersExceeded,Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER,spinLayout.getScrollX(),spinLayout.getScrollY());
            t.show();
            spinner.setBackground(redBorder);
        }
        if(boardGame.getMinPlayers()>details.getNoPers()) {
            t = Toast.makeText(this, R.string.minPlayers, Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER, spinLayout.getScrollX(), spinLayout.getScrollY());
            t.show();
            spinner.setBackground(redBorder);
        }
        if(boardGame.getDuration()<details.getDuration()){
            t = Toast.makeText(this, R.string.noTime, Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER, spinLayout.getScrollX(), spinLayout.getScrollY());
            t.show();
            spinner.setBackground(redBorder);
        }
    }
    public boolean timeBetween(String time1,String time2,int duration1,int duration2) {
        int t1 = Integer.parseInt(time1.substring(0, 2)) * 100 + Integer.parseInt(time1.substring(3));
        int t2 = Integer.parseInt(time2.substring(0, 2)) * 100 + Integer.parseInt(time2.substring(3));
        if (t1 <= t2 && t2 < t1 + duration1*100)
            return false;
        if (t1 > t2 && t2 + duration2*100 > t1)
            return false;
        return true;
    }

    @Override
    public void onBackPressed(){
        databaseReference.child("BlockedTables").child(String.valueOf(details.getTable())).child(details.markerDateDatabase())
                .child(details.markerTimeDatabase()).removeValue() ;
        super.onBackPressed();
    }
}
