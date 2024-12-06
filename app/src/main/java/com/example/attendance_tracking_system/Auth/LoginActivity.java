package com.example.attendance_tracking_system.Auth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
    private EditText emailUsernameEditText, passwordEditText;
    private Button loginButton;
    private TextView signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Initialize views
        emailUsernameEditText = findViewById(R.id.email_username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        signupLink = findViewById(R.id.signup_link);

        // Set click listeners
        loginButton.setOnClickListener(v -> loginUser());
        signupLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        if (validateInput()) {
            String emailOrUsername = emailUsernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // TODO: Implement login logic here
            // For now, just show a success message
            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput() {
        String emailOrUsername = emailUsernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        // Email/Username validation
        if (emailOrUsername.isEmpty()) {
            emailUsernameEditText.setError("Email or username is required");
            return false;
        }

        // Password validation
        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            return false;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters long");
            return false;
        }

        return true;
    }
}
