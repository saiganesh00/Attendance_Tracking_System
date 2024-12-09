package com.example.attendance_tracking_system.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.attendance_tracking_system.R;

public class TimeLineActivity extends AppCompatActivity {
    private TextView follwingTxt, myActivitiesTxt, featuredTxt;
    private LinearLayout menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        try {
            follwingTxt = findViewById(R.id.timelineFollowingTextView);
            myActivitiesTxt = findViewById(R.id.timelineMyActivitiesTextView);
            featuredTxt = findViewById(R.id.timelineFeaturedTextView);
            menuButton = findViewById(R.id.menuButtonLinearLayout);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing views", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setupClickListeners() {
        if (menuButton != null) {
            menuButton.setOnClickListener(v -> {
                Intent intent = new Intent(TimeLineActivity.this, MenuActivity.class);
                startActivity(intent);
            });
        }

        if (follwingTxt != null) {
            follwingTxt.setOnClickListener(v -> {
                follwingTxt.setBackgroundColor(getColor(R.color.white));
                follwingTxt.setTextColor(getColor(R.color.black));
                if (myActivitiesTxt != null) {
                    myActivitiesTxt.setBackgroundColor(getColor(R.color.primary_color));
                    myActivitiesTxt.setTextColor(getColor(R.color.white));
                }
                if (featuredTxt != null) {
                    featuredTxt.setBackgroundColor(getColor(R.color.primary_color));
                    featuredTxt.setTextColor(getColor(R.color.white));
                }
            });
        }

        if (myActivitiesTxt != null) {
            myActivitiesTxt.setOnClickListener(v -> {
                if (follwingTxt != null) {
                    follwingTxt.setBackgroundColor(getColor(R.color.primary_color));
                    follwingTxt.setTextColor(getColor(R.color.white));
                }
                myActivitiesTxt.setBackgroundColor(getColor(R.color.white));
                myActivitiesTxt.setTextColor(getColor(R.color.black));
                if (featuredTxt != null) {
                    featuredTxt.setBackgroundColor(getColor(R.color.primary_color));
                    featuredTxt.setTextColor(getColor(R.color.white));
                }
            });
        }

        if (featuredTxt != null) {
            featuredTxt.setOnClickListener(v -> {
                if (follwingTxt != null) {
                    follwingTxt.setBackgroundColor(getColor(R.color.primary_color));
                    follwingTxt.setTextColor(getColor(R.color.white));
                }
                if (myActivitiesTxt != null) {
                    myActivitiesTxt.setBackgroundColor(getColor(R.color.primary_color));
                    myActivitiesTxt.setTextColor(getColor(R.color.white));
                }
                featuredTxt.setBackgroundColor(getColor(R.color.white));
                featuredTxt.setTextColor(getColor(R.color.black));
            });
        }
    }
}