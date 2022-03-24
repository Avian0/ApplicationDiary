package com.example.thingie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText answer, password, confpassword;
    TextView question;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    String securityquestionans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setTitle("Reset Password");
        question = findViewById(R.id.register_securityquestion_textView);
        answer = findViewById(R.id.answer_editText);
        password = findViewById(R.id.forgot_password_editText);
        confpassword = findViewById(R.id.forgot_confpassword_editText);
        sharedPreferences = getSharedPreferences("saved_info", Context.MODE_PRIVATE);
        question.setText(sharedPreferences.getString("question", ""));
        securityquestionans = sharedPreferences.getString("answer", "");
    }

    public void Change_Password(View view) {
        if (answer.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confpassword.getText().toString().isEmpty()){
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
        }
        else{
            if (answer.getText().toString().equals(securityquestionans)){
                if (password.getText().toString().equals(confpassword.getText().toString())){
                    editor = sharedPreferences.edit();
                    editor.putString("password", password.getText().toString());
                    editor.commit();
                    finish();
                }
                else{
                    Toast.makeText(this, "Password don't match", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Security answer incorrect", Toast.LENGTH_SHORT).show();
            }
        }
    }
}