package com.example.novelsproject.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String UserID;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private String isAdmin;

    public User(String userID, String username, String password, String email, String avatar, String isAdmin) {
        this.UserID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.isAdmin = isAdmin;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }
}
