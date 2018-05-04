package com.example.user.mdsapplication;

import java.io.StringBufferInputStream;

public class BlockedBoardGame extends BoardGameReservation {
    private String realDate;
    public BlockedBoardGame(){}
    public BlockedBoardGame(String date, String time,int duration,String realDate){
        super(date,time,duration);
        this.realDate=realDate;
    }

    public String getRealDate() {
        return realDate;
    }

    public void setRealDate(String realDate) {
        this.realDate = realDate;
    }
}
