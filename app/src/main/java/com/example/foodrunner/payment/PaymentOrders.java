package com.example.foodrunner.payment;

public class PaymentOrders {


    String UserID;
    String UserName;
    String Postalcode;
    String adress;
    String nicnumber;
    String pnumber;


    public PaymentOrders(){

    }

    public PaymentOrders(String userID, String userName, String postalcode, String adress, String nicnumber, String pnumber) {
        UserID = userID;
        UserName = userName;
        Postalcode = postalcode;
        this.adress = adress;
        this.nicnumber = nicnumber;
        this.pnumber = pnumber;
    }

    public String getUserID() {
        return UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPostalcode() {
        return Postalcode;
    }

    public String getAdress() {
        return adress;
    }

    public String getNicnumber() {
        return nicnumber;
    }

    public String getPnumber() {
        return pnumber;
    }


}
