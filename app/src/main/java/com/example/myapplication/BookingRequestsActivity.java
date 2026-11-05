package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class BookingRequestsActivity extends BaseEmployeeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);
        setupEmployeeScreen("Your Booking Requests", R.id.menuBookingRequests);

        LinearLayout container = findViewById(R.id.listContainer);

        // Sample data
        View roomX = addBookingCard(container, "Room X", 6);
        showStatus(roomX, "rejected", R.color.emp_red);
        showDateTime(roomX, "12-11-26", "09:00 to 10:00");

        View roomY = addBookingCard(container, "Room Y", 12);
        showStatus(roomY, "approved", R.color.emp_green);
        showCancelButton(roomY, v -> DialogHelper.showCancelFlow(this));
        showDateTime(roomY, "13-11-26", "11:00 to 12:00");

        View roomZ = addBookingCard(container, "Room Z", 4);
        showStatus(roomZ, "pending", R.color.emp_orange);
        showCancelButton(roomZ, v -> DialogHelper.showCancelFlow(this));
        showDateTime(roomZ, "14-11-26", "14:00 to 15:00");
    }
}
