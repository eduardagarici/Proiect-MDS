package com.example.user.mdsapplication;


import java.io.Serializable;

public class MainReservation implements Serializable {

    private String name;
    private int noPers;
    private String date;

    private String time;

    private int  duration;
    private int table;

    MainReservation(String nume, int nrPers, String data,String timp, int durata, int masa)
    {
        this.name = nume;
        this.noPers = nrPers;
        this.date = data;
        this. duration = durata;
        this.time = timp;
        this.table = masa;
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


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public String markerDateDatabase(){
        return date.substring(0,2) + date.substring(3,5) + date.substring(6);
    }

    public String markerTimeDatabase(){
        return time.substring(0,2) + time.substring(3);
    }
}
