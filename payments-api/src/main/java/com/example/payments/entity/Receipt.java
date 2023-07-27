package com.example.payments.entity;

public enum Receipt {
    TEMPLATE_1("receipt1");
    public final String templateName;
    Receipt(String html) {
        this.templateName = html;
    }
}
