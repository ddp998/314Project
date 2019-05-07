package com.example.firebasetest;

public enum CarTransmission {
    AUTOMATIC("Automatic"),
    MANUAL("Manual");

    private String transmission;

    private CarTransmission(String transmission){
        this.transmission = transmission;
    }

    @Override public String toString(){
        return transmission;
    }
}
