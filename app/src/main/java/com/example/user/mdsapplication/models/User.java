package com.example.user.mdsapplication.models;

public class User {
    public String uid;
    public String email;
    public String firebaseToken;
    public String privilege;

    public User(){ this.privilege="user";}

    public User(String uid, String email, String firebaseToken) {
        this.uid = uid;
        this.email = email;
        this.firebaseToken = firebaseToken;
        this.privilege="user";
    }

    public String getPrivilege(){
        return privilege;
    }
}
