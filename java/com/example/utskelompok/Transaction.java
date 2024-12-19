package com.example.utskelompok;

public class Transaction {
    private String description;
    private int amount;
    private String time;
    private String type;  // "income" atau "expense"

    public Transaction(String description, int amount, String time, String type) {
        this.description = description;
        this.amount = amount;
        this.time = time;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}