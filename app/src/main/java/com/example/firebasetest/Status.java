package com.example.firebasetest;

public enum Status {
    AVAILABLE("Available"),
    UNAVAILABLE("Unavailable");

    private String status;

    Status(String status){
        this.status = status;
    }

    @Override public String toString(){
        return status;
    }
}
