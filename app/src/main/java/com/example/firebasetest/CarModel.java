package com.example.firebasetest;

public enum CarModel {
    MODEL0("Model"),
    MODEL1("Model 1"),
    MODEL2("Model 2"),
    MODEL3("Model 3");

    private String modelName;

    private CarModel(String modelName){
        this.modelName = modelName;
    }

    @Override public String toString(){
        return modelName;
    }
}
