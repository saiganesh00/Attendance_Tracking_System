package com.example.attendance_tracking_system.programs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.attendance_tracking_system.R;
import com.example.attendance_tracking_system.attendance.MainAttendanceActivity;
import com.example.attendance_tracking_system.home.TimeLineActivity;

public class ProgramsActivity extends AppCompatActivity {

    private LinearLayout homeBtn;
    private CardView attendanceCard;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.programs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        homeBtn = findViewById(R.id.homeButtonLinearLayout);
        homeBtn.setOnClickListener(v -> {
            intent = new Intent(ProgramsActivity.this, TimeLineActivity.class);
            startActivity(intent);
            finish();
        });

        attendanceCard = findViewById(R.id.attendanceCard);
        attendanceCard.setOnClickListener(v -> {
            intent = new Intent(ProgramsActivity.this, MainAttendanceActivity.class);
            startActivity(intent);
        });
    }
}