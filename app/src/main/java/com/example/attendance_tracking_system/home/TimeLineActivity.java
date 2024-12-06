package com.example.attendance_tracking_system.home;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.attendance_tracking_system.R;

public class TimeLineActivity extends AppCompatActivity {

    private TextView follwingTxt, myActivitiesTxt, featuredTxt;
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
    }
}