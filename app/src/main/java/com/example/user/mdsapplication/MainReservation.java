package com.example.user.mdsapplication;

import java.io.Serializable;
import java.util.Date;

public class MainReservation implements Serializable {
    private String name;
    private int noPers;
    private Date date;
    private int  duration;


    MainReservation(String nume, int nrPers, Date data ,int durata )
    {
        this.name = nume;
        this.noPers = nrPers;
        this.date = data;
        this.duration = durata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoPers() {
        return noPers;
    }

    public void setNoPers(int noPers) {
        this.noPers = noPers;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public Date getDate() { return date; }

    public void setDate(Date date) {
        this.date = date;
    }
}
