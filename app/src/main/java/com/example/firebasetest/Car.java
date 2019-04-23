package com.example.firebasetest;

public class Car {
    String regNum;
    CarTransmission transmission;
    CarMake make;
    CarModel model;
    int year;
    String series, engine, ownerEmail;

    public Car (String regNum, CarTransmission transmission, CarMake make, CarModel model, int year, String series, String engine, String ownerEmail) {
        this.regNum = regNum;
        this.transmission = transmission;
        this.make = make;
        this.model = model;
        this.year = year;
        this.series = series;
        this.engine = engine;
        this.ownerEmail = ownerEmail;
    }
}
