package com.example.attendance_tracking_system.network.models;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("login")
    private String login;
    
    @SerializedName("password")
    private String password;

    public LoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
