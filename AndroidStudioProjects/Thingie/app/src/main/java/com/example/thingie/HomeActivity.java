package com.example.thingie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView name = findViewById(R.id.username_textView);
        TextView password = findViewById(R.id.password_textView);

        if(getIntent().hasExtra("Username")) {
            name.setText(getIntent().getStringExtra("Username"));
        } else {
            name.setText("No Username found");
        }
        if(getIntent().hasExtra("Password")) {
            password.setText(getIntent().getStringExtra("Password"));
        } else {
            password.setText("no password found");
        }
    }
}