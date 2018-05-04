package com.example.user.mdsapplication;

class BoardGameReservation {
    String date;
    String time;
    int duration;

    public BoardGameReservation(){}

    public BoardGameReservation(String date, String time, int duration) {
        this.date = date;
        this.time = time;
        this.duration = duration;
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
}
