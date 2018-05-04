package com.example.user.mdsapplication;

import java.io.Serializable;
import java.util.Calendar;

public class TemporaryReservation implements Serializable {

    String RealTime;
    String ReservationTime;
    int duration;

    TemporaryReservation(String resT, int d)
    {
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);

        if(currentHour < 10 && currentMinute < 10)
            this.RealTime= "0" + currentHour + ":" + "0" + currentMinute;
        else if(currentHour < 10)
            this.RealTime = "0" +currentHour + ":" + currentMinute;
        else if (currentMinute < 10)
            this.RealTime = currentHour + ":" + "0" + currentMinute;
        else
            this.RealTime = currentHour + ":" + currentMinute;

        this.ReservationTime = resT;
        this.duration = d;
    }
}
