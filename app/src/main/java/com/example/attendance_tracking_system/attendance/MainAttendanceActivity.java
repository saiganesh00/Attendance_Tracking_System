package com.example.attendance_tracking_system.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.attendance_tracking_system.R;
import com.example.attendance_tracking_system.programs.ProgramsActivity;
import com.journeyapps.barcodescanner.CaptureActivity;

public class MainAttendanceActivity extends AppCompatActivity {

    private ImageView backArrow;
    private CardView checkInCard, checkOutCard;

    // Register the ActivityResultLauncher to handle the QR scan result
    private final ActivityResultLauncher<Intent> qrScanLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String scannedResult = result.getData().getStringExtra("SCAN_RESULT");
                    Toast.makeText(this, "QR Code scanned: " + scannedResult, Toast.LENGTH_SHORT).show();
                    // You can add logic here to handle the scanned data (e.g., check-in or check-out)
                } else {
                    Toast.makeText(this, "QR Code scan canceled", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_attendance);

        // Set up edge-to-edge mode for the activity
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up the back button
        backArrow = findViewById(R.id.backIcon);
        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(MainAttendanceActivity.this, ProgramsActivity.class);
            startActivity(intent);
            finish();
        });

        // Set up the check-in and check-out cards
        checkInCard = findViewById(R.id.checkInCard);
        checkOutCard = findViewById(R.id.checkOutCard);

        checkInCard.setOnClickListener(v -> startQRCodeScanner("Check In"));
        checkOutCard.setOnClickListener(v -> startQRCodeScanner("Check Out"));
    }

    private void startQRCodeScanner(String actionType) {
        // Set up the intent to start the QR scanner
        Intent intent = new Intent(this, CaptureActivity.class);
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        qrScanLauncher.launch(intent);
    }
}
