package com.example.attendance_tracking_system.network.models;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("tokens")
    private Tokens tokens;

    @SerializedName("user")
    private User user;

    public static class Tokens {
        @SerializedName("access")
        private String accessToken;

        @SerializedName("refresh")
        private String refreshToken;

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }

    public static class User {
        @SerializedName("id")
        private int id;

        @SerializedName("employee_id")
        private String employeeId;

        @SerializedName("email")
        private String email;

        @SerializedName("username")
        private String username;

        @SerializedName("first_name")
        private String firstName;

        @SerializedName("last_name")
        private String lastName;

        @SerializedName("is_active")
        private boolean isActive;

        public int getId() {
            return id;
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public boolean isActive() {
            return isActive;
        }
    }

    public String getMessage() {
        return message;
    }

    public Tokens getTokens() {
        return tokens;
    }

    public User getUser() {
        return user;
    }
}
