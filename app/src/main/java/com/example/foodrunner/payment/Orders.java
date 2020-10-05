package com.example.foodrunner.payment;

public class Orders {


        String ID;
        String name;
        String cardnumber;
        String expdate;
        String cvv;

        public Orders(){

        }

        public Orders(String ID, String name, String cardnumber, String expdate, String cvv) {
            this.ID = ID;
            this.name = name;
            this.cardnumber = cardnumber;
            this.expdate = expdate;
            this.cvv = cvv;
        }

        public String getID() {
            return ID;
        }

        public String getName() {
            return name;
        }

        public String getCardnumber() {
            return cardnumber;
        }

        public String getExpdate() {
            return expdate;
        }

        public String getCvv() {
            return cvv;
        }
    }


