package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class RoomsFoundActivity extends BaseEmployeeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);
        setupEmployeeScreen("Rooms Found", R.id.menuMakeBooking);

        LinearLayout container = findViewById(R.id.listContainer);

        // Sample data. The chosen date/time arrive as MakeBookingActivity.EXTRA_DATE / EXTRA_TIME
        addRoom(container, "Room Y", 12);
        addRoom(container, "Room Z", 4);
    }

    private void addRoom(LinearLayout container, String name, int capacity) {
        View card = addBookingCard(container, name, capacity);
        showStatus(card, "vacant", R.color.emp_green);

        card.setOnClickListener(v ->
                DialogHelper.showBookingFlow(this, this::goHome));
    }

    private void goHome() {
        Intent intent = new Intent(this, BookingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
