package com.cs360.simplestocks.model;

import java.util.ArrayList;

/**
 * User class to hold user data when created
 */
public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Purchase> portfolio;

    public User(int id, String name, String email, String password, ArrayList<Purchase> portfolio) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.portfolio = portfolio;
    }

    public int getId(){

        return id;
    }

    public void setId(int id){

        this.id = id;
    }

    public String getName(){

        return name;
    }

    public void setName(String name){

        this.name = name;
    }

    public String getEmail(){

        return email;
    }

    public void setEmail(String email){

        this.email = email;
    }

    public String getPassword(){

        return password;
    }

    public void setPassword(String password){

        this.password = password;
    }

    public ArrayList<Purchase> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(ArrayList<Purchase> portfolio) {
        this.portfolio = portfolio;
    }
}
