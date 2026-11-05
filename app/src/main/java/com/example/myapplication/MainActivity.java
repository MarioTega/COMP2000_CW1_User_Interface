
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                });

        EditText username = findViewById(R.id.usernameField);
        EditText password = findViewById(R.id.passwordField);

        TextView usernameError = findViewById(R.id.usernameError);
        TextView passwordError = findViewById(R.id.passwordError);

        // Hide username error as soon as the user starts typing
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    usernameError.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Hide password error as soon as the user starts typing
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty()) {
                    passwordError.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        Button signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(v -> {

            boolean usernameEmpty =
                    username.getText().toString().trim().isEmpty();

            boolean passwordEmpty =
                    password.getText().toString().isEmpty();

            // Validate username independently
            if (usernameEmpty) {
                usernameError.setText("Username is required");
                usernameError.setVisibility(View.VISIBLE);
            } else {
                usernameError.setVisibility(View.GONE);
            }

            // Validate password independently
            if (passwordEmpty) {
                passwordError.setText("Password is required");
                passwordError.setVisibility(View.VISIBLE);
            } else {
                passwordError.setVisibility(View.GONE);
            }

            // Only proceed if BOTH fields are filled
            if (!usernameEmpty && !passwordEmpty) {

                Intent intent = new Intent(
                        MainActivity.this,
                        BookingsActivity.class
                );

                startActivity(intent);
            }
        });
    }
}
