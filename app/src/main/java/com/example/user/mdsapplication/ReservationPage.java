package com.example.user.mdsapplication;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static java.text.DateFormat.*;

public class ReservationPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener{

     int day, month, year, hour, minute;
     int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
     List<Integer> fitTable = new LinkedList<>();
     private int Table;
     private boolean validReservation = false;
     private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
     private String name;
     private int noPers;
     private String date;
     private String time;
     private int duration;
    @Override

     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_reservation_page);
        final TextView dateInput = (TextView) findViewById(R.id.date);
        final TextView timeInput = (TextView) findViewById(R.id.time);
        Button checkoutBtn = (Button) findViewById(R.id.checkoutButton);
        Button mentionsBtn = (Button) findViewById(R.id.specialMentions);

        dateInputOnClick(dateInput);
        timeInputOnClick(timeInput);
        onButtonsOnClick(checkoutBtn, true,"CheckoutPage");
        onButtonsOnClick(mentionsBtn, true,"SpecialMentionsPage");


    }

    public void dateInputOnClick(final TextView dateInput) {
        dateInput.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                dateInput.setBackground(null);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReservationPage.this, ReservationPage.this, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    public void timeInputOnClick(final TextView timeInput) {
        timeInput.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                timeInput.setBackground(null);

                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(ReservationPage.this, ReservationPage.this, hour, minute, android.text.format.DateFormat.is24HourFormat(ReservationPage.this));
                timePickerDialog.show();
            }
        });
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearFinal = year;
        monthFinal = month + 1;
        dayFinal = dayOfMonth;

        String reservationDateString;
        if(monthFinal < 10 && dayFinal < 10)
             reservationDateString = "0" + dayFinal + "/" + "0" + monthFinal + "/" + yearFinal;
        else if(monthFinal < 10)
            reservationDateString = dayFinal + "/" + "0" + monthFinal + "/" + yearFinal;
        else if(dayFinal < 10)
            reservationDateString = "0" + dayFinal + "/" + monthFinal + "/" + yearFinal;
        else
             reservationDateString = dayFinal + "/" + monthFinal + "/" + yearFinal;

        TextView date = (TextView) findViewById(R.id.date);
        date.setText(reservationDateString);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

        hourFinal = hourOfDay;
        minuteFinal = minute;

        String reservationTimeString;
        if(hourFinal < 10 && minuteFinal < 10)
              reservationTimeString = "0" + hourFinal + ":" + "0" + minuteFinal;
        else if(hourFinal < 10)
             reservationTimeString = "0" + hourFinal + ":" + minuteFinal;
        else if (minuteFinal <10 )
            reservationTimeString = hourFinal + ":" + "0" + minuteFinal;
        else
            reservationTimeString = hourFinal + ":"  + minuteFinal;
        TextView time = (TextView) findViewById(R.id.time);
        time.setText(reservationTimeString);
    }

    public boolean lessThanCurrentDate()
    {
       int  currentYear = Calendar.getInstance().get(Calendar.YEAR);
       int  currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
       int  currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if (yearFinal < currentYear)
            return true;
        else
            if(yearFinal == currentYear && monthFinal < currentMonth)
            {
                return true;
            }
             else
                 if(yearFinal == currentYear && monthFinal == currentMonth && dayFinal < currentDay)
                     return true;


        return false;
    }

    public boolean invalidTimeInput()
    {
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);

        if(hourFinal < 8)
            return true;
        if(yearFinal == Calendar.getInstance().get(Calendar.YEAR) &&
           monthFinal == Calendar.getInstance().get(Calendar.MONTH) + 1 &&
           dayFinal == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
            if (hourFinal < currentHour)
                return true;
            else if (hourFinal == currentHour && minuteFinal <= currentMinute)
                return true;
        }
        return false;

    }

    public void getTable(final boolean temp, final String activity)
    {

        mDatabase.child("tables").addValueEventListener(new ValueEventListener() {
             @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot c:children) {
                    int capacitate = c.child("capacity").getValue(Integer.class);
                    if(capacitate == noPers || capacitate == noPers + 1)
                    {
                        fitTable.add(Integer.parseInt(c.getKey()));

                    }
                }

                if(fitTable != null && !fitTable.isEmpty())
                    checkBlockListThenVerifyReservation(temp,activity);
                else
                    noReservationAvailableAlert("No table is available at the date and time chosen");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }

    public boolean overlapTimeReservation( String timeBD, int durationBD)
    {
        String[] hrMinString=timeBD.split(":");

        int hourBD = Integer.valueOf(hrMinString[0]);
        int minBD = Integer.valueOf(hrMinString[1]);

        int finishedTimeBD = hourBD + durationBD;
        int finishedTimeCurrent = hourFinal + duration;

        if(hourBD <= hourFinal && finishedTimeBD > hourFinal)
        return true;
        if(hourBD <= hourFinal && finishedTimeBD == hourFinal && minBD > minuteFinal)
        return true;
        if(hourBD > hourFinal && finishedTimeCurrent > finishedTimeBD)
            return true;
        if(hourBD > hourFinal && finishedTimeCurrent == finishedTimeBD && minBD < minuteFinal)
            return true;
        return false;
    }

    public void noReservationAvailableAlert (String msg)
    {
        AlertDialog.Builder noReservationDialog = new AlertDialog.Builder(ReservationPage.this);
        noReservationDialog.setMessage(msg).setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = noReservationDialog.create();
        alert.setTitle("Reservation error");
        alert.show();
    }

    public boolean tableWithoutReservation (DataSnapshot dataSnapshot, String date )
    {
        for(int i : fitTable) {
            if (dataSnapshot.child(date).child(String.valueOf(i)).getValue() == null) {
                Table = i;
                validReservation = true;
                return true;
            }
        }

        return false;
    }

    public void createIntent(String activity)
    {
        MainReservation reservationObj = new MainReservation(name, noPers, date, time, duration, Table);
        Intent c;
        if(activity.equals("SpecialMentionsPage"))
            c = new Intent(ReservationPage.this,SpecialMentions.class);
        else
            c= new Intent(ReservationPage.this,Checkout.class);
        c.putExtra("Source","ReservationPage");
        c.putExtra("mainReservation", reservationObj);
        startActivity(c);
        
    }
    public void checkBlockListThenVerifyReservation(final boolean temp, final String activity)
    {
        final String _dateBD = date.replaceAll("/", "");
        if(mDatabase.child("BlockedTables").getKey() !=null)
        {
            mDatabase.child("BlockedTables").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> blockedTables = dataSnapshot.getChildren();
                    for(DataSnapshot bT : blockedTables) {
                        if (fitTable.contains(Integer.valueOf(bT.getKey()))) {
                            if (bT.child(_dateBD).getValue() != null) {
                                Iterable<DataSnapshot> bTReservation = bT.child(_dateBD).getChildren();

                                for (DataSnapshot bTR : bTReservation) {
                                    if (overlapTimeReservation(bTR.child("ReservationTime").getValue().toString(), Integer.valueOf(bTR.child("duration").getValue().toString()))) {
                                        fitTable.remove(Integer.valueOf(bT.getKey()));
                                    }

                                }
                            }
                        }
                    }


                    verifyReservation(temp,activity);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else  verifyReservation(temp,activity);
    }

    public void send(boolean temp, String _dateBD,String activity) {
        if (temp)
            mDatabase.child("BlockedTables").child(String.valueOf(Table)).child(_dateBD).push().setValue(new TemporaryReservation(time, duration));
        createIntent(activity);
        Toast.makeText(getApplicationContext(), "Saving reservation...", Toast.LENGTH_SHORT).show();
    }


    public void verifyReservation(final boolean temp, final String activity)
    {
        final String _dateBD = date.replaceAll("/", "");
       if( mDatabase.child("activeReservations").getKey() != null)
       {
           mDatabase.child("activeReservations").addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   if (dataSnapshot.child(_dateBD).getValue() != null) {
                       if (!tableWithoutReservation(dataSnapshot, _dateBD))
                       {
                           for (int i : fitTable)
                           {
                               Iterable<DataSnapshot> reservations = dataSnapshot.child(_dateBD).child(String.valueOf(i)).getChildren();
                               boolean validTable = true;
                               for (DataSnapshot reservation : reservations) {

                                   if(overlapTimeReservation(reservation.child("mainDetails").child("time").getValue().toString(), Integer.valueOf(reservation.child("mainDetails").child("duration").getValue().toString())))
                                   {
                                       validTable = false;
                                       break;
                                   }

                               }
                              if(validTable) {
                                  Table = i;
                                  validReservation = true;
                              }

                           }
                       }
                       else{
                           send(temp,_dateBD);
                           fitTable.clear();
                       }

                   } else {
                       if(fitTable != null && !fitTable.isEmpty()) {
                           Table = fitTable.get(0);
                           validReservation = true;
                       }

                   }

                   if(validReservation == false)
                       noReservationAvailableAlert("No table is available at the date and time chosen");
                   else{
                       send(temp,_dateBD,activity);
                       fitTable.clear();
                   }
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }

           });
       }
       else {
           if(fitTable != null && !fitTable.isEmpty()) {
               Table = fitTable.get(0);
               send(temp,_dateBD,activity);
           }
           fitTable.clear();
       }
    }

    void eliminateRedBorder(final EditText input)
    {
        input.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                input.setBackground(null);
            }
        });

    }

    public void onButtonsOnClick(Button btn, final boolean temp, final String activity)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                ShapeDrawable redBorder = new ShapeDrawable();
                redBorder.setShape(new RectShape());
                redBorder.getPaint().setColor(Color.RED);
                redBorder.getPaint().setStrokeWidth(10f);
                redBorder.getPaint().setStyle(Paint.Style.STROKE);

                final TextView dateInput = (TextView) findViewById(R.id.date);
                final TextView timeInput = (TextView) findViewById(R.id.time);
                final EditText nameInput = (EditText) findViewById(R.id.name);
                final EditText noPersInput = (EditText) findViewById(R.id.noPers);
                final EditText durationInput = (EditText) findViewById(R.id.duration);


                boolean ready = true;

                if(nameInput.getText().toString().length() == 0)
                {
                    nameInput.setBackground(redBorder);
                    if(ready) ready = false;
                }
                if(noPersInput.getText().toString().length() == 0)
                {
                    noPersInput.setBackground(redBorder);
                    if(ready) ready = false;

                }
                else if(Integer.valueOf(noPersInput.getText().toString()) > 10)
                {
                    noReservationAvailableAlert("Please contact us by chat for reservations for a group with over 10 people");
                    if (ready) ready = false;
                    noPersInput.setText(null);
                }
                if(dateInput.getText().toString().length() == 0 || lessThanCurrentDate()) {

                    dateInput.setBackground(redBorder);
                    if (ready) ready = false;
                }
                if(timeInput.getText().toString().length() == 0 || invalidTimeInput())
                {
                    timeInput.setBackground(redBorder);
                    if(ready) ready = false;

                }
                if(durationInput.getText().toString().length() == 0)
                {
                    durationInput.setBackground(redBorder);
                    if(ready) ready = false;
                }
                else if (hourFinal != 0 && Integer.valueOf(durationInput.getText().toString()) + hourFinal > 24)
                {
                    durationInput.setBackground(redBorder);
                    if(ready) ready = false;
                }

                eliminateRedBorder(nameInput);
                eliminateRedBorder(noPersInput);
                eliminateRedBorder(durationInput);


                if(ready) {
                    name = nameInput.getText().toString();
                    noPers = Integer.valueOf(noPersInput.getText().toString());
                    date = dateInput.getText().toString();
                    time = timeInput.getText().toString();
                    duration = Integer.valueOf(durationInput.getText().toString());
                    getTable(temp,activity);
                }
            }
        });

    }
}
