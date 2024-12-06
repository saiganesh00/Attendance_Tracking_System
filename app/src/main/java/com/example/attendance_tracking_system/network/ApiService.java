package com.example.attendance_tracking_system.network;

import com.example.attendance_tracking_system.network.models.AuthResponse;
import com.example.attendance_tracking_system.network.models.LoginRequest;
import com.example.attendance_tracking_system.network.models.LogoutRequest;
import com.example.attendance_tracking_system.network.models.SignupRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({
        "Content-Type: application/json",
        "Accept: application/json"
    })
    @POST("login/")
    Call<AuthResponse> login(@Body LoginRequest loginRequest);

    @POST("logout/")
    Call<Void> logout(@Header("Authorization") String token, @Body LogoutRequest logoutRequest);

    @Headers({
        "Content-Type: application/json",
        "Accept: application/json"
    })
    @POST("register/")
    Call<AuthResponse> signup(@Body SignupRequest signupRequest);
}
