package com.example.attendance_tracking_system.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.attendance_tracking_system.R;
import com.example.attendance_tracking_system.programs.ProgramsActivity;

public class TimeLineActivity extends AppCompatActivity {

    private TextView follwingTxt, myActivitiesTxt, featuredTxt;
    private Button joinButton, createLeagueButton;

    private LinearLayout programsBtn;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_time_line);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.timeline), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        follwingTxt = findViewById(R.id.timelineFollowingTextView);
        follwingTxt.setOnClickListener(v -> {
            follwingTxt.setBackgroundColor(getColor(R.color.white));
            follwingTxt.setTextColor(getColor(R.color.black));
            myActivitiesTxt.setBackgroundColor(getColor(R.color.primary_color));
            myActivitiesTxt.setTextColor(getColor(R.color.white));
            featuredTxt.setBackgroundColor(getColor(R.color.primary_color));
            featuredTxt.setTextColor(getColor(R.color.white));
        });

        myActivitiesTxt = findViewById(R.id.timelineMyActivitiesTextView);
        myActivitiesTxt.setOnClickListener(v -> {
            follwingTxt.setBackgroundColor(getColor(R.color.primary_color));
            follwingTxt.setTextColor(getColor(R.color.white));
            myActivitiesTxt.setBackgroundColor(getColor(R.color.white));
            myActivitiesTxt.setTextColor(getColor(R.color.black));
            featuredTxt.setBackgroundColor(getColor(R.color.primary_color));
            featuredTxt.setTextColor(getColor(R.color.white));
        });

        featuredTxt = findViewById(R.id.timelineFeaturedTextView);
        featuredTxt.setOnClickListener(v -> {
            follwingTxt.setBackgroundColor(getColor(R.color.primary_color));
            follwingTxt.setTextColor(getColor(R.color.white));
            myActivitiesTxt.setBackgroundColor(getColor(R.color.primary_color));
            myActivitiesTxt.setTextColor(getColor(R.color.white));
            featuredTxt.setBackgroundColor(getColor(R.color.white));
            featuredTxt.setTextColor(getColor(R.color.black));

        });

        joinButton = findViewById(R.id.joinWithInviteButton);
        joinButton.setOnClickListener(v -> {
            intent = new Intent(TimeLineActivity.this, ProgramsActivity.class);
            startActivity(intent);
            finish();
        });

        createLeagueButton = findViewById(R.id.createLeagueButton);
        createLeagueButton.setOnClickListener(v -> {
            intent = new Intent(TimeLineActivity.this, ProgramsActivity.class);
            startActivity(intent);
            finish();
        });

        programsBtn = findViewById(R.id.programsButtonLinearLayout);
        programsBtn.setOnClickListener(v -> {
            intent = new Intent(TimeLineActivity.this, ProgramsActivity.class);
            startActivity(intent);
            finish();
        });

    }
}