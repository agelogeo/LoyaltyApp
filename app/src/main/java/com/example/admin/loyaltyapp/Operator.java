package com.example.admin.loyaltyapp;

/**
 * Created by Admin on 8/1/2017.
 */

public class Operator {
    private int id;
    private String username;
    private String password;
    private int access_level;
    private String first_name;
    private String last_name;
    private String phone;

    public Operator() {
    }

    public Operator(int id, String username, String password, int access_level, String first_name, String last_name, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.access_level = access_level;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccess_level() {
        return access_level;
    }

    public void setAccess_level(int access_level) {
        this.access_level = access_level;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
