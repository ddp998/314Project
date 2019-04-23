package com.example.firebasetest;

public enum PaymentType {
    SUB("Subscription"),
    PERUSE("Pay as you go");

    private String type;

    PaymentType(String type){
        this.type = type;
    }

    @Override public String toString(){
        return type;
    }
}
