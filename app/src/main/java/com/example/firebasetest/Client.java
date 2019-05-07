package com.example.firebasetest;

public class Client extends User {
    String licenseNum;
    PaymentType paymentType;
    PaymentMethod paymentMethod = null;
    PaymentCard cardInfo = null;
    PaymentBank bankInfo = null;

    /* initial demo constructor
    public Client(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    } */

    public Client(String firstName, String lastName, String email, String phone, String licenseNum, PaymentType paymentType, PaymentMethod paymentMethod, PaymentCard cardInfo, PaymentBank bankInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.licenseNum = licenseNum;
        this.paymentType = paymentType;
        this.paymentMethod = paymentMethod;

        if (paymentMethod.equals(PaymentMethod.CARD)) {
            this.cardInfo = cardInfo;
        } else if (paymentMethod.equals(PaymentMethod.BANK)) {
            this.bankInfo = bankInfo;
        } else if (paymentMethod.equals(PaymentMethod.UNSAVED)) {
            cardInfo = null;
            bankInfo = null;
        }
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentCard getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(PaymentCard cardInfo) {
        this.cardInfo = cardInfo;
    }

    public PaymentBank getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(PaymentBank bankInfo) {
        this.bankInfo = bankInfo;
    }


}
