package com.example.user.mdsapplication;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Reservations{
    private String userName;
    private int noOfPersons;
    private Date date;
    private int duration;
    private boolean chosenBoardGame;
    private String boardGame;
    private boolean specialMentions;
    private List<Product> products = new LinkedList<>();

    Reservations(String name, int noOfPersons,Date date, int duration){
        this.userName = name;
        this.noOfPersons = noOfPersons;
        this.duration = duration;
        this.chosenBoardGame = false;
        this.specialMentions = false;
    }

    Reservations(String name, int noOfPersons, Date date, int duration, String boardGame, LinkedList<Product> products){
        this.userName = name;
        this.noOfPersons = noOfPersons;
        this.duration = duration;
        this.date = date;
        this.chosenBoardGame = true;
        this.specialMentions = true;
        this.boardGame = boardGame;
        this.products = products;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNoOfPersons() {
        return noOfPersons;
    }

    public void setNoOfPersons(int noOfPersons) {
        this.noOfPersons = noOfPersons;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(String boardGame) {
        this.boardGame = boardGame;
    }
    public void addProduct(String name,int quantity,double price){
        products.add(new Product(name,quantity,price));
    }

    public boolean isChosenBoardGame() {
        return chosenBoardGame;
    }

    public void setChosenBoardGame(boolean chosenBoardGame) {
        this.chosenBoardGame = chosenBoardGame;
    }

    public boolean hasSpecialMentions() {
        return specialMentions;
    }

    public void setSpecialMentions(boolean specialMentions) {
        this.specialMentions = specialMentions;
    }

    public String getDate() {
        return date.getDate() + "/" +  date.getMonth() + "/" + date.getYear();
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public List<Product> getProducts(){
        return products;
    }
    public void setProducts(List<Product> products){
        this.products=products;
    }
}
