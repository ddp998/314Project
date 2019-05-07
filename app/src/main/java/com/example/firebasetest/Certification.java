package com.example.firebasetest;

public class Certification {
    String certName, issuedBy, dateIssued, issuedTo;

    public Certification(String certName, String issuedBy, String dateIssued, String issuedTo) {
        this.certName = certName;
        this.issuedBy = issuedBy;
        this.dateIssued = dateIssued;
        this.issuedTo = issuedTo;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(String issuedTo) {
        this.issuedTo = issuedTo;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }
}
