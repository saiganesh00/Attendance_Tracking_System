package com.example.attendance_tracking_system.network.models;

import com.google.gson.annotations.SerializedName;

public class SignupRequest {
    @SerializedName("username")
    private String username;
    
    @SerializedName("password")
    private String password;
    
    @SerializedName("email")
    private String email;
    
    @SerializedName("first_name")
    private String firstName;
    
    @SerializedName("last_name")
    private String lastName;

    public SignupRequest(String username, String password, String email, 
                        String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
