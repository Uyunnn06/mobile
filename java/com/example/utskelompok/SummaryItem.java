package com.example.utskelompok;

public class SummaryItem {
    private String description;
    private String amount;

    public SummaryItem(String description, String amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }
}