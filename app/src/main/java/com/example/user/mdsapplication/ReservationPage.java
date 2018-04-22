package com.example.user.mdsapplication;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

public class ReservationPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_page);

        EditText date = (EditText) findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment datePicker = new com.example.user.mdsapplication.DatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });

        EditText time = (EditText) findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(),"time picker");

            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String reservationDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());

        EditText date = (EditText) findViewById(R.id.date);
        date.setText(reservationDateString);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

        String reservationTimeString = hourOfDay + ":" + minute;
        EditText time = (EditText) findViewById(R.id.time);
        time.setText(reservationTimeString);
    }
}
