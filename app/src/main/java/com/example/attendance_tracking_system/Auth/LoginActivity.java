package com.example.attendance_tracking_system.Auth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.text.method.PasswordTransformationMethod;
import android.text.method.HideReturnsTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.attendance_tracking_system.R;
import com.example.attendance_tracking_system.home.;
import com.example.attendance_tracking_system.network.RetrofitClient;
import com.example.attendance_tracking_system.network.models.AuthResponse;
import com.example.attendance_tracking_system.network.models.LoginRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView signupLink;
    private SharedPreferences sharedPreferences;
    private boolean passwordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Initialize views
        usernameEditText = findViewById(R.id.email_username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        signupLink = findViewById(R.id.signup_link);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("AuthPrefs", MODE_PRIVATE);

        // Check if user is already logged in
        if (isLoggedIn()) {
            navigateToTimeline();
            return;
        }

        // Setup password visibility toggle
        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0);
        passwordEditText.setOnTouchListener((view, event) -> {
            if (event.getAction() != MotionEvent.ACTION_UP) {
                return false;
            }
            
            if (event.getRawX() < (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[2].getBounds().width())) {
                return false;
            }
            
            passwordVisible = !passwordVisible;
            if (passwordVisible) {
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
            } else {
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0);
            }
            
            // Maintain cursor position
            passwordEditText.setSelection(passwordEditText.length());
            return true;
        });

        loginButton.setOnClickListener(v -> loginUser());
        signupLink.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void loginUser() {
        String login = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (login.isEmpty() || password.isEmpty()) {
            if (login.isEmpty()) {
                usernameEditText.setError("Please enter username");
            }
            if (password.isEmpty()) {
                // Removed passwordLayout.setError("Please enter password");
            }
            return;
        }

        // Clear any previous errors
        usernameEditText.setError(null);
        // Removed passwordLayout.setError(null);

        Log.d(TAG, "Attempting login with username: " + login);
        LoginRequest loginRequest = new LoginRequest(login, password);

        RetrofitClient.getInstance().getApiService().login(loginRequest)
            .enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        AuthResponse authResponse = response.body();
                        Log.d(TAG, "Login successful");
                        
                        // Save tokens and user info
                        saveAuthData(authResponse);
                        
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        navigateToTimeline();
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e(TAG, "Login failed. Error: " + errorBody);
                            Log.e(TAG, "Response code: " + response.code());
                            Toast.makeText(LoginActivity.this, 
                                "Login failed: " + errorBody, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing error response", e);
                            Toast.makeText(LoginActivity.this, 
                                "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    Log.e(TAG, "Network error during login", t);
                    Toast.makeText(LoginActivity.this, 
                        "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void saveAuthData(AuthResponse authResponse) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_token", authResponse.getTokens().getAccessToken());
        editor.putString("refresh_token", authResponse.getTokens().getRefreshToken());
        editor.putString("employee_id", authResponse.getUser().getEmployeeId());
        editor.putString("username", authResponse.getUser().getUsername());
        editor.putString("email", authResponse.getUser().getEmail());
        editor.putBoolean("is_logged_in", true);
        editor.apply();
    }

    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean("is_logged_in", false);
    }

    private void navigateToTimeline() {
        Intent intent = new Intent(this, TimeLineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
