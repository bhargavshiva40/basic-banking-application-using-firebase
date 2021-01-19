package com.shiva1234r.ebank;

public class HistoryModel {
    String senderName, receiverName, amount;

    public HistoryModel() {
    }

    public HistoryModel(String amount, String receiverName, String senderName) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.amount = amount;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getAmount() {
        return amount;
    }
}
