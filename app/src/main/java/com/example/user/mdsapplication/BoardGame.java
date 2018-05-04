package com.example.user.mdsapplication;

import java.util.ArrayList;
import java.util.List;

public class BoardGame {
    private int duration;
    private int maxPlayers;
    private int minAge;
    private int minPlayers;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }


    public BoardGame(){}

    public BoardGame(int duration, int maxPlayers, int minAge, int minPlayers, String name) {
        this.duration = duration;
        this.maxPlayers = maxPlayers;
        this.minAge = minAge;
        this.minPlayers = minPlayers;
        this.name = name;
    }
}

