package com.example.thingie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    TextView forgotPassword, createAccount;
    Button confirm;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username_editText);
        password = findViewById(R.id.password_editText);
        forgotPassword = findViewById(R.id.forgotPassword_button);
        createAccount = findViewById(R.id.createAccount_button);
        confirm = findViewById(R.id.confirm_button);


        sharedPreferences = this.getSharedPreferences("saved_info", Context.MODE_PRIVATE);

        createAccount.setOnClickListener(view -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        if (sharedPreferences.contains("username")) {
            //User already exists
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("username", sharedPreferences.getString("username","")); //Passing values to another screen
            startActivity(intent);
            finish();
        }
    }


    public void Confirm(View view) {
        if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Login Error");
            alert.setMessage("Username and Password should not be empty");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.show();
        }
        else{
            String savedUsername = sharedPreferences.getString("username","");
            String savePassword = sharedPreferences.getString("password","");

            if (username.getText().toString().equals(savedUsername) && password.getText().toString().equals(savePassword)){
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("Username", username.getText().toString());
                intent.putExtra("Password", password.getText().toString());
                startActivity(intent);

                username.setText("");
                password.setText("");
            }

            else{
                Toast.makeText(this, "Login is Successful", Toast.LENGTH_LONG).show();
            }

        }
    }
}