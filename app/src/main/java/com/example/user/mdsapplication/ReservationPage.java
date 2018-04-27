package com.example.user.mdsapplication;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
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

import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import static java.text.DateFormat.*;

public class ReservationPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener{

     int day, month, year, hour, minute;
     int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

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
        onButtonsOnClick(checkoutBtn);
        onButtonsOnClick(mentionsBtn);

    }

    public void dateInputOnClick(TextView dateInput) {
        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ReservationPage.this, ReservationPage.this, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    public void timeInputOnClick(TextView timeInput) {
        timeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);


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

        String reservationDateString = dayFinal + "/" + monthFinal + "/" + yearFinal;

        TextView date = (TextView) findViewById(R.id.date);
        date.setText(reservationDateString);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

        String reservationTimeString = hourOfDay + ":" + minute;
        TextView time = (TextView) findViewById(R.id.time);
        time.setText(reservationTimeString);
    }

    public void onButtonsOnClick(Button btn)
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

                String _name = nameInput.getText().toString();
                String _noPers = noPersInput.getText().toString();
                String _date = dateInput.getText().toString();
                String _time = timeInput.getText().toString();
                String _duration = durationInput.getText().toString();

                String _datetime= _date + " "  + _time;
                Log.d("datatimp", _datetime);


                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                Date convertedDate =  new Date();
                try {
                    convertedDate =  format.parse(_datetime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.d("datatimp", convertedDate.toString());


                boolean ready = true;

                if(_name.length() == 0)
                {
                    nameInput.setBackground(redBorder);
                    if(ready) ready = false;
                }
                if(_noPers.length() == 0)
                {
                    noPersInput.setBackground(redBorder);
                    if(ready) ready = false;

                }
                if(_date.length() == 0) {

                    dateInput.setBackground(redBorder);
                    if (ready) ready = false;
                }
                if(_time.length() == 0)
                {
                    timeInput.setBackground(redBorder);
                    if(ready) ready = false;

                }
                if(_duration.length() == 0)
                {
                    durationInput.setBackground(redBorder);
                    if(ready) ready = false;
                }


                if(ready)
                {
                    MainReservation rezervare2 = new MainReservation(_name,Integer.parseInt(_noPers), convertedDate, Integer.parseInt(_duration));

                    Intent i = new Intent(ReservationPage.this, MainActivity.class);
                    i.putExtra("sampleObject", rezervare2);
                    startActivity(i);
                }
            }
        });

    }
}
