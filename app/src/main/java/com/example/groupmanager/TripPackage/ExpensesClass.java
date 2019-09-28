package com.example.groupmanager.TripPackage;

import java.util.ArrayList;

public class ExpensesClass {
    private String name,date,time,category;
    private int bill;
    private ArrayList<String> contribution;
    private String collection;

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public void setContribution(ArrayList<String> contribution) {
        this.contribution = contribution;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getBill() {
        return bill;
    }

    public ArrayList<String> getContribution() {
        return contribution;
    }
}
