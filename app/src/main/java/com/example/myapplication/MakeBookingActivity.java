package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MakeBookingActivity extends BaseEmployeeActivity {

    public static final String EXTRA_DATE = "extra_date";
    public static final String EXTRA_TIME = "extra_time";

    private static final String PLACEHOLDER = "Select";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_booking);
        setupEmployeeScreen("Room Finder", R.id.menuMakeBooking);

        Spinner dateSpinner = findViewById(R.id.dateSpinner);
        Spinner timeSpinner = findViewById(R.id.timeSpinner);

        dateSpinner.setAdapter(createAdapter(buildDateOptions()));
        timeSpinner.setAdapter(createAdapter(buildTimeOptions()));

        findViewById(R.id.checkRoomsButton).setOnClickListener(v -> {
            boolean dateMissing = dateSpinner.getSelectedItemPosition() == 0;
            boolean timeMissing = timeSpinner.getSelectedItemPosition() == 0;

            if (dateMissing || timeMissing) {
                Toast.makeText(this, "Please select a date and a time",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, RoomsFoundActivity.class);
            intent.putExtra(EXTRA_DATE, dateSpinner.getSelectedItem().toString());
            intent.putExtra(EXTRA_TIME, timeSpinner.getSelectedItem().toString());
            startActivity(intent);
        });
    }

    private ArrayAdapter<String> createAdapter(List<String> options) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.item_spinner, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    /** "Select" followed by the next 14 days. */
    private List<String> buildDateOptions() {
        List<String> dates = new ArrayList<>();
        dates.add(PLACEHOLDER);

        SimpleDateFormat format = new SimpleDateFormat("EEE d MMM", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 14; i++) {
            dates.add(format.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    /** "Select" followed by hourly slots from 09:00 to 17:00. */
    private List<String> buildTimeOptions() {
        List<String> times = new ArrayList<>();
        times.add(PLACEHOLDER);

        for (int hour = 9; hour < 17; hour++) {
            times.add(String.format(Locale.getDefault(),
                    "%02d:00 to %02d:00", hour, hour + 1));
        }
        return times;
    }
}
