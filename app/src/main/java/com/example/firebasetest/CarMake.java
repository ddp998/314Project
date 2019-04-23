package com.example.firebasetest;

public enum CarMake {
    MAKE0("Make"),
    MAKE1("Make 1"),
    MAKE2("Make 2"),
    MAKE3("Make 3");

    private String makeName;

    private CarMake(String makeName){
        this.makeName = makeName;
    }

    @Override public String toString(){
        return makeName;
    }
}
