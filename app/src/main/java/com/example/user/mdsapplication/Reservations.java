package com.example.user.mdsapplication;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Reservations implements Serializable{
    MainReservation mainDetails;
    private String boardGame;
    private List<Product> products = new LinkedList<>();

    public Reservations(MainReservation mainDetails) {
        this.mainDetails = mainDetails;
    }

    public Reservations(MainReservation mainDetails, String boardGame) {
        this.mainDetails = mainDetails;
        this.boardGame = boardGame;
    }

    public Reservations(MainReservation mainDetails, List<Product> products) {
        this.mainDetails = mainDetails;
        this.products = products;
    }

    public Reservations(MainReservation mainDetails, String boardGame, List<Product> products) {
        this.mainDetails = mainDetails;
        this.boardGame = boardGame;
        this.products = products;
    }

    public MainReservation getMainDetails() {
        return mainDetails;
    }

    public void setMainDetails(MainReservation mainDetails) {
        this.mainDetails = mainDetails;
    }

    public String getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(String boardGame) {
        this.boardGame = boardGame;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
