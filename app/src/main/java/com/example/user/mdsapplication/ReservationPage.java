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
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class ReservationPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_page);

        final TextView dateInput = (TextView) findViewById(R.id.date);
        dateInput.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                dateInput.setBackground(null);

                android.support.v4.app.DialogFragment datePicker = new com.example.user.mdsapplication.DatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });

        final TextView timeInput = (TextView) findViewById(R.id.time);
        timeInput.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                timeInput.setBackground(null);

                android.support.v4.app.DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(),"time picker");

            }
        });
        dateInput.setText("12/12/2018");
        final EditText nameInput = (EditText) findViewById(R.id.name);
        final EditText noPersInput = (EditText) findViewById(R.id.noPers);
        final EditText durationInput = (EditText) findViewById(R.id.duration);
        Button checkoutBtn = (Button) findViewById(R.id.checkoutButton);

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                ShapeDrawable redBorder = new ShapeDrawable();
                redBorder.setShape(new RectShape());
                redBorder.getPaint().setColor(Color.RED);
                redBorder.getPaint().setStrokeWidth(10f);
                redBorder.getPaint().setStyle(Paint.Style.STROKE);

               String _name = nameInput.getText().toString();
               String _noPers = noPersInput.getText().toString();
               String _date = dateInput.getText().toString();
               String _time = timeInput.getText().toString();
               String _duration = durationInput.getText().toString();

               String _datetime= _date + " "  + _time;


                try {
                    Date dateTimeFormatted = (Date) new SimpleDateFormat("MMMM d, yyyy hh:mm", Locale.ENGLISH).parse(_datetime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boolean ready = true;
                Log.d("aici",_time);
                if(_name.length() == 0)
                {
                    //nameInput.setBackground(redBorder);
                    if(ready) ready = false;
                    Log.d("aici","aici1");
                    Log.d("aici",String.valueOf(ready));
                }
                else if(_noPers.length() == 0)
                {
                    //noPersInput.setBackground(redBorder);
                    if(ready) ready = false;
                    Log.d("aici","aici2");
                }
                else if(_date.length() == 0) {
                    //dateInput.setBackground(redBorder);
                    if (ready) ready = false;
                    Log.d("aici", "aici3");
                }
                else if(_time.length() == 0)
                {
                   // timeInput.setBackground(redBorder);
                    if(ready) ready = false;
                    Log.d("aici","aici4");
                }
                else if(_duration.length() == 0)
                {
                   // durationInput.setBackground(redBorder);
                    if(ready) ready = false;
                    Log.d("aici","aici5");
                }

                Log.d("aici",_date);
                Log.d("ready", String.valueOf(ready));
                Log.d("aici","aici");

                if(ready)
                {
                    ReservationsTest rezervare1 = new ReservationsTest("Beatrice", 5,new Date(2018,04,23), 3);

                  // ReservationsTest rezervare2 = new ReservationsTest(_name,Integer.parseInt(_noPers),     , Integer.parseInt(_duration));


                    Intent i = new Intent(ReservationPage.this, MainActivity.class);
                    i.putExtra("sampleObject", rezervare1);
                    startActivity(i);
                }
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String reservationDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());

        TextView date = (TextView) findViewById(R.id.date);
        date.setText(reservationDateString);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

        String reservationTimeString = hourOfDay + ":" + minute;
        TextView time = (TextView) findViewById(R.id.time);
        time.setText(reservationTimeString);
    }
}
