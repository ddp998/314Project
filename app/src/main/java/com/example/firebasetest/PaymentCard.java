package com.example.firebasetest;

import java.util.Date;

public class PaymentCard {
    String cardNum; //can only contain 16 digits and not start in 0
    Date expiryDate; //store as mm/yy
    String securityCode, ownerFullName; //3-digit security code

    public PaymentCard(String cardNum, Date expiryDate, String securityCode, String ownerFullName) {
        this.cardNum = cardNum;
        this.expiryDate = expiryDate;
        this.securityCode = securityCode;
        this.ownerFullName = ownerFullName;
    }

    public boolean isAllDigits(String[] str) {
        boolean allDigits = true;

        for (int i = 0; i < str.length; i++) {
            try {
                int checkedDigit = Integer.parseInt(str[i]);
            } catch (NumberFormatException | NullPointerException e) {
                System.err.println(str[i] + " is not a digit.");
                allDigits = false;
            }
        }

        return allDigits;
    }

    public boolean isValidCardNum() {
        boolean validCard = true;

        String[] cardNumDigits = cardNum.split("");
        int cardNumDigitCount = cardNumDigits.length;

        if (isAllDigits(cardNumDigits)) {
            // checking 16 digits, leading 0
            if (cardNumDigitCount < 16 || cardNumDigitCount > 16 || cardNumDigits[0].equals("0")) {
                validCard = false;
            } else {
                validCard = true;
            }
        } else if (!isAllDigits(cardNumDigits)) {
            validCard = false;
        }

        return validCard;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        if (isValidCardNum()) {
            this.cardNum = cardNum;
        } else {
            System.err.println("Error: Invalid card number.");
        }
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }
}
