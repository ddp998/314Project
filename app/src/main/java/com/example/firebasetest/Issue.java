package com.example.firebasetest;

public enum Issue {
    ISSUE1("Issue 1"),
    ISSUE2("Issue 2"),
    ISSUE3("Issue 3");

    private String issue;

    private Issue(String issue){
        this.issue = issue;
    }

    @Override public String toString(){
        return issue;
    }
}
