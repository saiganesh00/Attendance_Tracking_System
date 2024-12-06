package com.example.attendance_tracking_system.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.attendance_tracking_system.Auth.LoginActivity;
import com.example.attendance_tracking_system.R;
import com.example.attendance_tracking_system.network.RetrofitClient;
import com.example.attendance_tracking_system.network.models.LogoutRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";
    private SharedPreferences sharedPreferences;
    private LinearLayout logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("AuthPrefs", MODE_PRIVATE);

        // Initialize views
        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        try {
            logoutButton = findViewById(R.id.logoutButton);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing views", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setupClickListeners() {
        if (logoutButton != null) {
            logoutButton.setOnClickListener(v -> showLogoutConfirmation());
        } else {
            Toast.makeText(this, "Logout button not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes", (dialog, which) -> callLogoutApi())
            .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
            .show();
    }

    private void callLogoutApi() {
        String accessToken = sharedPreferences.getString("access_token", "");
        String refreshToken = sharedPreferences.getString("refresh_token", "");

        if (accessToken.isEmpty() || refreshToken.isEmpty()) {
            Log.e(TAG, "Missing tokens - Access Token: " + (accessToken.isEmpty() ? "missing" : "present") + 
                      ", Refresh Token: " + (refreshToken.isEmpty() ? "missing" : "present"));
            Toast.makeText(this, "Authentication tokens not found", Toast.LENGTH_SHORT).show();
            return;
        }

        LogoutRequest logoutRequest = new LogoutRequest(refreshToken);
        RetrofitClient.getInstance().getApiService().logout("Bearer " + accessToken, logoutRequest)
            .enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Logout successful");
                        clearLocalDataAndNavigateToLogin();
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e(TAG, "Logout failed. Error: " + errorBody);
                            Toast.makeText(MenuActivity.this, 
                                "Logout failed: " + errorBody, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing error response", e);
                            Toast.makeText(MenuActivity.this, 
                                "Logout failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "Network error during logout", t);
                    Toast.makeText(MenuActivity.this, 
                        "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void clearLocalDataAndNavigateToLogin() {
        try {
            // Clear all data from SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            // Navigate to LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Error during logout", e);
            Toast.makeText(this, "Error during logout", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
