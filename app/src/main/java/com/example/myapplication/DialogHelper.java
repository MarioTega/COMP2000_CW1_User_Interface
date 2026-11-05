package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

/** Pop-ups from the prototypes: tick/cross confirm and exit message. */
public final class DialogHelper {

    private DialogHelper() {}

    // ---- Full flows ---------------------------------------------------

    /** "Do you want to proceed?" > "Booking Request successful". */
    public static void showBookingFlow(Context context, Runnable onExit) {
        showConfirm(context, "Do you want to proceed with the booking", () ->
                showMessage(context,
                        "Booking Request successful\nNow pending approval",
                        onExit));
    }

    /** "Are you sure?" > "Request cancelled successfully". */
    public static void showCancelFlow(Context context) {
        showConfirm(context, "Are you sure you want to cancel this request?", () ->
                showMessage(context, "Request cancelled successfully", null));
    }

    // ---- Building blocks ----------------------------------------------

    /** Message with a green tick (onYes) and a red cross (just closes). */
    public static void showConfirm(Context context, String message, Runnable onYes) {
        View view = inflate(context);
        ((TextView) view.findViewById(R.id.dialogMessage)).setText(message);
        view.findViewById(R.id.exitButton).setVisibility(View.GONE);

        AlertDialog dialog = create(context, view, true);

        view.findViewById(R.id.yesButton).setOnClickListener(v -> {
            dialog.dismiss();
            if (onYes != null) onYes.run();
        });
        view.findViewById(R.id.noButton).setOnClickListener(v -> dialog.dismiss());

        show(context, dialog);
    }

    /** Message with a single red exit button. */
    public static void showMessage(Context context, String message, Runnable onExit) {
        View view = inflate(context);
        ((TextView) view.findViewById(R.id.dialogMessage)).setText(message);
        view.findViewById(R.id.confirmRow).setVisibility(View.GONE);
        view.findViewById(R.id.dialogIcon).setVisibility(View.VISIBLE);
        view.findViewById(R.id.exitButton).setVisibility(View.VISIBLE);

        AlertDialog dialog = create(context, view, false);

        view.findViewById(R.id.exitButton).setOnClickListener(v -> {
            dialog.dismiss();
            if (onExit != null) onExit.run();
        });

        show(context, dialog);
    }

    // ---- Internals ----------------------------------------------------

    private static View inflate(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.dialog_prompt, null);
    }

    private static AlertDialog create(Context context, View view, boolean cancelable) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(cancelable)
                .create();

        if (dialog.getWindow() != null) {
            // Let our rounded grey card be the only background
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
        }
        return dialog;
    }

    private static void show(Context context, AlertDialog dialog) {
        dialog.show();

        if (dialog.getWindow() != null) {
            int width = (int) (context.getResources()
                    .getDisplayMetrics().widthPixels * 0.85f);
            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
