package com.example.admin.loyaltyapp;

/**
 * Created by Admin on 10/1/2017.
 */

public class Coupon {
    private int id;
    private String name;
    private int required_stamps;
    private boolean isOkay;

    public boolean isManage() {
        return isManage;
    }

    public void setManage(boolean manage) {
        isManage = manage;
    }

    private boolean isManage;

    public Coupon(int id, String name, int required_stamps) {
        this.id = id;
        this.name = name;
        this.required_stamps = required_stamps;
    }

    public Coupon() {
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

    public int getRequired_stamps() {
        return required_stamps;
    }

    public void setRequired_stamps(int required_stamps) {
        this.required_stamps = required_stamps;
    }


    public boolean isOkay() {
        return isOkay;
    }

    public void setOkay(boolean okay) {
        isOkay = okay;
    }
}
