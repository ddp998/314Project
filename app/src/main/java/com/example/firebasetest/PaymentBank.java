package com.example.firebasetest;

public class PaymentBank {
    String acctName, bankName, bsb, acctNum, ownerEmail; //bsb 6, acctNum 8

    public PaymentBank(String acctName, String bankName, String bsb, String acctNum, String ownerEmail) {
        this.acctName = acctName;
        this.bankName = bankName;
        this.bsb = bsb;
        this.acctNum = acctNum;
        this.ownerEmail = ownerEmail;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBsb() {
        return bsb;
    }

    public void setBsb(String bsb) {
        this.bsb = bsb;
    }

    public String getAcctNum() {
        return acctNum;
    }

    public void setAcctNum(String acctNum) {
        this.acctNum = acctNum;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}
