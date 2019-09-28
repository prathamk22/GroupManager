package com.example.groupmanager;


import java.util.ArrayList;

public class LocationDetails {
    private String location,date,endDate,json;
    private int number,price;
    private ArrayList<String> people;
    private ArrayList<String> Expenses;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setExpenses(ArrayList<String> expenses) {
        Expenses = expenses;
    }

    public void setPeople(ArrayList<String> people) {
        this.people = people;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getNumber() {
        return number;
    }

    public int getPrice() {
        return price;
    }

    public ArrayList<String> getExpenses() {
        return Expenses;
    }

    public ArrayList<String> getPeople() {
        return people;
    }
}
