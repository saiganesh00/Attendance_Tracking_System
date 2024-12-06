package com.example.attendance_tracking_system.Auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.attendance_tracking_system.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstNameEditText, secondNameEditText, usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Initialize views
        firstNameEditText = findViewById(R.id.first_name);
        secondNameEditText = findViewById(R.id.second_name);
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        signUpButton = findViewById(R.id.signup_button);
        loginLink = findViewById(R.id.login_link);

        // Set up click listener for signup button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Set up click listener for login link
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to login activity
                finish();
            }
        });
    }

    private void registerUser() {
        if (validateInput()) {
            String firstName = firstNameEditText.getText().toString().trim();
            String secondName = secondNameEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            // TODO: Implement registration logic here
            // For now, just show a success message
            Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput() {
        String firstName = firstNameEditText.getText().toString().trim();
        String secondName = secondNameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        // First Name validation
        if (firstName.isEmpty()) {
            firstNameEditText.setError("First name is required");
            return false;
        }

        // Second Name validation
        if (secondName.isEmpty()) {
            secondNameEditText.setError("Second name is required");
            return false;
        }

        // Username validation
        if (username.isEmpty()) {
            usernameEditText.setError("Username is required");
            return false;
        }
        if (username.length() < 4) {
            usernameEditText.setError("Username must be at least 4 characters long");
            return false;
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            usernameEditText.setError("Username can only contain letters, numbers, and underscores");
            return false;
        }

        // Email validation
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email address");
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

        // Confirm Password validation
        if (confirmPassword.isEmpty()) {
            confirmPasswordEditText.setError("Please confirm your password");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }

        return true;
    }
}
