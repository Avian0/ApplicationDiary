package com.example.thingie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, confirmPassword;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.register_username_editText);
        password = findViewById(R.id.register_password_editText);
        confirmPassword = findViewById(R.id.register_passwordConfirmation_editText);
    }

    public void Register(View view) {
        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_LONG).show();
        } else {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                sharedPreferences = this.getSharedPreferences("saved_info", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("username", username.getText().toString());
                editor.putString("password", password.getText().toString());

                editor.apply();

                Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Password and Confirm Password did not match", Toast.LENGTH_LONG).show();
            }
        }
    }
}