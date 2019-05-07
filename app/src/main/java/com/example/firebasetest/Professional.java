package com.example.firebasetest;

import java.util.ArrayList;

public class Professional extends User {
    int avgRating = 0;
    String companyName = null;
    ArrayList<Certification> certs = null;
    Status status;
    int numRequestsComplete = 0;

    public Professional(String firstName, String lastName, String email, String phone, int avgRating, String companyName, ArrayList<Certification> certs, Status status, int numRequestsComplete) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.avgRating = avgRating;
        this.companyName = companyName;
        this.certs = certs;
        this.status = Status.UNAVAILABLE;
        this.numRequestsComplete = 0;
    }

    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ArrayList<Certification> getCerts() {
        return certs;
    }

    public void setCerts(ArrayList<Certification> certs) {
        this.certs = certs;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
