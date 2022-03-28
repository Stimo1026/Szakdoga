package com.application.appnimal.classes;

import java.util.ArrayList;

public class User {

    private String userName;
    private String fullName;
    private String pw;
    private String email;
    private ArrayList<Pet> pets;
    private ArrayList<Walk> walks;

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<Pet> getPets() {
        return pets;
    }

    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Walk> getWalks() {
        return walks;
    }

    public void setWalks(ArrayList<Walk> walks) {
        this.walks = walks;
    }

    public User(String userName, String fullName, String pw, String email) {
        this.userName = userName;
        this.fullName = fullName;
        this.pw = pw;
        this.email = email;
        this.pets = new ArrayList<Pet>();
        this.walks = new ArrayList<Walk>();
    }


}
