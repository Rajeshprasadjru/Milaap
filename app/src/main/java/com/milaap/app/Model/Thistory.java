package com.milaap.app.Model;

public class Thistory {
    String type, amount,purpose,date;

    public Thistory(String type, String amount, String purpose, String date) {
        this.type = type;
        this.amount = amount;
        this.purpose = purpose;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Thistory{" +
                "type='" + type + '\'' +
                ", amount='" + amount + '\'' +
                ", purpose='" + purpose + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
