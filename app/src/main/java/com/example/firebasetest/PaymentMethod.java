package com.example.firebasetest;

public enum PaymentMethod {
    CARD("Card"),
    BANK("Bank"),
    UNSAVED("Unsaved");

    private String method;

    PaymentMethod(String method){
        this.method = method;
    }

    @Override public String toString(){
        return method;
    }
}
