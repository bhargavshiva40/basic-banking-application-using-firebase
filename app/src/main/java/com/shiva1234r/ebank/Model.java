package com.shiva1234r.ebank;

public class Model {

    long balance;
    String bank;
    String email;
    String  name;
    String phone;
    String userId;

    public Model() {
    }

    public Model(long balance, String bank, String email,  String name, String phone,String userId) {
        this.balance = balance;
        this.bank = bank;
        this.email = email;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
