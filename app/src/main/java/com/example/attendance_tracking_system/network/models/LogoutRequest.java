package com.example.attendance_tracking_system.network.models;

import com.google.gson.annotations.SerializedName;

public class LogoutRequest {
    @SerializedName("refresh_token")
    private String refreshToken;

    public LogoutRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
