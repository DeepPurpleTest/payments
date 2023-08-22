package com.example.payments.entity;

public enum CardType {
    VISA("4"), MASTERCARD("51");
    private final String prefix;

    CardType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }
}
