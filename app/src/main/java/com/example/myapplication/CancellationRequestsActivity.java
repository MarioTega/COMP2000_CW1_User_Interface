package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class CancellationRequestsActivity extends BaseEmployeeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);
        setupEmployeeScreen("Your Cancellation Requests",
                R.id.menuCancellationRequests);

        LinearLayout container = findViewById(R.id.listContainer);

        // Sample data
        View roomY = addBookingCard(container, "Room Y", 12);
        showStatus(roomY, "approved", R.color.emp_green);
        showDateTime(roomY, "13-11-26", "11:00 to 12:00");
        showReason(roomY, "Absence of key attendees");

        View roomZ = addBookingCard(container, "Room Z", 4);
        showStatus(roomZ, "pending", R.color.emp_orange);
        showCancelButton(roomZ, v -> DialogHelper.showCancelFlow(this));
        showDateTime(roomZ, "14-11-26", "14:00 to 15:00");
        showReason(roomZ, "Redundancy");
    }
}
