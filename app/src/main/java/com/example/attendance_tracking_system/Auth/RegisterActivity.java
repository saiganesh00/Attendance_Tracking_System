package com.example.attendance_tracking_system.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.text.method.PasswordTransformationMethod;
import android.text.method.HideReturnsTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.attendance_tracking_system.R;
import com.example.attendance_tracking_system.network.RetrofitClient;
import com.example.attendance_tracking_system.network.models.AuthResponse;
import com.example.attendance_tracking_system.network.models.SignupRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private EditText firstNameEditText, secondNameEditText, usernameEditText, emailEditText;
    private EditText passwordEditText, confirmPasswordEditText;
    private Button signUpButton;
    private TextView loginLink;
    private boolean passwordVisible = false;
    private boolean confirmPasswordVisible = false;

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

        // Setup password visibility toggles
        setupPasswordToggle(passwordEditText, () -> passwordVisible, visible -> passwordVisible = visible);
        setupPasswordToggle(confirmPasswordEditText, () -> confirmPasswordVisible, visible -> confirmPasswordVisible = visible);

        signUpButton.setOnClickListener(v -> registerUser());
        loginLink.setOnClickListener(v -> finish());
    }

    private void setupPasswordToggle(EditText editText, BooleanSupplier isVisible, Consumer<Boolean> setVisible) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0);
        editText.setOnTouchListener((view, event) -> {
            if (event.getAction() != MotionEvent.ACTION_UP) {
                return false;
            }
            
            if (event.getRawX() < (editText.getRight() - editText.getCompoundDrawables()[2].getBounds().width())) {
                return false;
            }
            
            boolean visible = !isVisible.getAsBoolean();
            setVisible.accept(visible);
            
            if (visible) {
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
            } else {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0);
            }
            
            editText.setSelection(editText.length());
            return true;
        });
    }

    @FunctionalInterface
    interface BooleanSupplier {
        boolean getAsBoolean();
    }

    @FunctionalInterface
    interface Consumer<T> {
        void accept(T t);
    }

    private void registerUser() {
        String firstName = firstNameEditText.getText().toString().trim();
        String secondName = secondNameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Reset errors
        firstNameEditText.setError(null);
        secondNameEditText.setError(null);
        usernameEditText.setError(null);
        emailEditText.setError(null);

        // Validate input
        if (firstName.isEmpty()) {
            firstNameEditText.setError("First name is required");
            return;
        }
        if (secondName.isEmpty()) {
            secondNameEditText.setError("Second name is required");
            return;
        }
        if (username.isEmpty()) {
            usernameEditText.setError("Username is required");
            return;
        }
        if (username.length() < 4) {
            usernameEditText.setError("Username must be at least 4 characters long");
            return;
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            usernameEditText.setError("Username can only contain letters, numbers, and underscores");
            return;
        }
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email address");
            return;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Please enter password");
            return;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters long");
            return;
        }
        if (confirmPassword.isEmpty()) {
            confirmPasswordEditText.setError("Please confirm password");
            return;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return;
        }

        // Create signup request
        SignupRequest signupRequest = new SignupRequest(
            username, password, email, firstName, secondName
        );

        RetrofitClient.getInstance().getApiService().signup(signupRequest)
            .enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("RegisterActivity", "Registration successful");
                        Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        finish(); // Go back to login screen
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("RegisterActivity", "Registration failed. Error: " + errorBody);
                            Log.e("RegisterActivity", "Response code: " + response.code());
                            Log.e("RegisterActivity", "Request URL: " + call.request().url());
                            Toast.makeText(RegisterActivity.this, 
                                "Registration failed: " + errorBody, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e("RegisterActivity", "Error parsing error response", e);
                            Toast.makeText(RegisterActivity.this, 
                                "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    Log.e("RegisterActivity", "Network error during registration", t);
                    Log.e("RegisterActivity", "Request URL: " + call.request().url());
                    Toast.makeText(RegisterActivity.this, 
                        "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
