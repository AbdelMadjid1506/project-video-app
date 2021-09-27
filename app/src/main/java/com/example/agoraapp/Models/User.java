package com.example.agoraapp.Models;

public class User {

    private String Email;
    private String userName;
    private String uId;

    public User() {
    }

    public User(String email, String userName, String uId) {
        Email = email;
        this.userName = userName;
        this.uId = uId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
