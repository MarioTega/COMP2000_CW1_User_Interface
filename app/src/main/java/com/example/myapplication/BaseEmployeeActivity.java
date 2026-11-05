package com.example.myapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.SystemBarStyle;
import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * Shared behaviour for every employee screen: top bar, sidebar menu,
 * back arrow, system-bar insets and the booking card helpers.
 */
public abstract class BaseEmployeeActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Light status-bar icons (header is coloured), dark nav-bar icons (light page)
        EdgeToEdge.enable(this,
                SystemBarStyle.dark(Color.TRANSPARENT),
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT));
    }

    /**
     * Call right after setContentView().
     *
     * @param title             text shown in the top bar
     * @param currentMenuItemId sidebar item to highlight (e.g. R.id.menuHistory)
     */
    protected void setupEmployeeScreen(String title, @IdRes int currentMenuItemId) {
        drawerLayout = findViewById(R.id.drawerLayout);
        View sidebar = findViewById(R.id.sidebar);
        View topBar = findViewById(R.id.topBar);

        ((TextView) findViewById(R.id.screenTitle)).setText(title);

        // The coloured header runs under the status bar, so it takes the top inset;
        // the rest of the screen only keeps clear of the sides and nav bar.
        final int topBarBasePaddingTop = topBar.getPaddingTop();
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            0,
                            systemBars.right,
                            systemBars.bottom
                    );

                    topBar.setPadding(
                            topBar.getPaddingLeft(),
                            topBarBasePaddingTop + systemBars.top,
                            topBar.getPaddingRight(),
                            topBar.getPaddingBottom()
                    );

                    return insets;
                });

        // Same for the sidebar (top/bottom only)
        final int sidebarBasePaddingTop = sidebar.getPaddingTop();
        ViewCompat.setOnApplyWindowInsetsListener(sidebar, (v, insets) -> {
            Insets systemBars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars());

            v.setPadding(
                    v.getPaddingLeft(),
                    sidebarBasePaddingTop + systemBars.top,
                    v.getPaddingRight(),
                    systemBars.bottom
            );

            return insets;
        });

        findViewById(R.id.menuButton).setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // System back closes the sidebar first
        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        } else {
                            setEnabled(false);
                            getOnBackPressedDispatcher().onBackPressed();
                        }
                    }
                });

        setupSidebar(currentMenuItemId);
    }

    // ------------------------------------------------------------------
    // Sidebar navigation
    // ------------------------------------------------------------------

    private void setupSidebar(@IdRes int currentId) {
        link(R.id.menuYourBookings, BookingsActivity.class, currentId);
        link(R.id.menuMakeBooking, MakeBookingActivity.class, currentId);
        link(R.id.menuBookingRequests, BookingRequestsActivity.class, currentId);
        link(R.id.menuCancellationRequests,
                CancellationRequestsActivity.class, currentId);
        link(R.id.menuHistory, HistoryActivity.class, currentId);
    }

    private void link(@IdRes int viewId, Class<?> target, @IdRes int currentId) {
        TextView item = findViewById(viewId);

        if (viewId == currentId) {
            // Current screen: highlighted pill, tapping just closes the sidebar
            item.setBackgroundResource(R.drawable.bg_nav_selected);
            item.setTypeface(item.getTypeface(), Typeface.BOLD);
            item.setOnClickListener(v ->
                    drawerLayout.closeDrawer(GravityCompat.START));
        } else {
            item.setOnClickListener(v -> navigateTo(target));
        }
    }

    private void navigateTo(Class<?> target) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Intent intent = new Intent(this, target);

        if (target == BookingsActivity.class) {
            // Bookings is the home screen: return to it instead of stacking
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else {
            startActivity(intent);
            // Swap screens so the back stack stays: Sign in > Bookings > Screen
            if (!(this instanceof BookingsActivity)) {
                finish();
            }
        }
    }

    // ------------------------------------------------------------------
    // Booking card helpers (item_booking.xml)
    // ------------------------------------------------------------------

    protected View addBookingCard(ViewGroup container, String roomName, int capacity) {
        View card = getLayoutInflater()
                .inflate(R.layout.item_booking, container, false);

        ((TextView) card.findViewById(R.id.roomName)).setText(roomName);
        ((TextView) card.findViewById(R.id.roomCapacity))
                .setText(capacity + " people capacity");

        container.addView(card);
        return card;
    }

    /** Soft chip: coloured text on a light tint of the same colour. */
    protected void showStatus(View card, String status, @ColorRes int colorRes) {
        int color = ContextCompat.getColor(this, colorRes);

        TextView badge = card.findViewById(R.id.statusBadge);
        badge.setText(status);
        badge.setTextColor(color);
        badge.setBackgroundTintList(
                ColorStateList.valueOf(ColorUtils.setAlphaComponent(color, 38)));
        badge.setVisibility(View.VISIBLE);
    }

    protected void showCancelButton(View card, View.OnClickListener listener) {
        View cancel = card.findViewById(R.id.cancelButton);
        cancel.setOnClickListener(listener);
        cancel.setVisibility(View.VISIBLE);
    }

    protected void showDateTime(View card, String date, String time) {
        ((TextView) card.findViewById(R.id.dateText)).setText(date);
        ((TextView) card.findViewById(R.id.timeText)).setText(time);
        card.findViewById(R.id.dateTimeRow).setVisibility(View.VISIBLE);
    }

    protected void showReason(View card, String reason) {
        TextView reasonText = card.findViewById(R.id.reasonText);
        reasonText.setText("Reason: " + reason);
        reasonText.setVisibility(View.VISIBLE);
    }
}
