package com.example.admin.loyaltyapp;

/**
 * Created by Admin on 8/1/2017.
 */

public class Customer {
    private int id;
    private String name;
    private String surname;
    private String phone;
    private String barcode;
    private int stamps;
    private int coupons_used;
    private int visits;
    private String last_visit;


    public Customer(){
        super();
    }

    public Customer(int id, String name, String surname, String phone, String barcode, int stamps, int coupons_used, int visits, String last_visit) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.barcode = barcode;
        this.stamps = stamps;
        this.coupons_used = coupons_used;
        this.visits = visits;
        this.last_visit = last_visit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getStamps() {
        return stamps;
    }

    public void setStamps(int stamps) {
        this.stamps = stamps;
    }

    public int getCoupons_used() {
        return coupons_used;
    }

    public void setCoupons_used(int coupons_used) {
        this.coupons_used = coupons_used;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public String getLast_visit() {
        return last_visit;
    }

    public void setLast_visit(String last_visit) {
        this.last_visit = last_visit;
    }
}
