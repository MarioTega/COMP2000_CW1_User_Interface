package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HistoryActivity extends BaseEmployeeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);
        setupEmployeeScreen("Your History", R.id.menuHistory);

        LinearLayout container = findViewById(R.id.listContainer);

        // Sample data
        View roomX = addBookingCard(container, "Room X", 6);
        showDateTime(roomX, "02-11-26", "10:00 to 11:00");

        View roomY = addBookingCard(container, "Room Y", 12);
        showDateTime(roomY, "03-11-26", "13:00 to 14:00");
    }
}
