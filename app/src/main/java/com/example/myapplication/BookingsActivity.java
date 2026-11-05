package com.example.myapplication;

import android.os.Bundle;

public class BookingsActivity extends BaseEmployeeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);
        setupEmployeeScreen("Your Bookings", R.id.menuYourBookings);

        // Empty state is shown by default (activity_bookings.xml).
        // Once bookings exist, hide R.id.emptyState and list them instead.
    }
}
