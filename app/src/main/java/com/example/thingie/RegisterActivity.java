package com.example.thingie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText username, password, confirmPassword, securityAns;
    Spinner securityQues;
    SharedPreferences sharedPreferences;
    String selectedQues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register Account");
        username = findViewById(R.id.register_username_editText);
        password = findViewById(R.id.register_password_editText);
        securityAns = findViewById(R.id.register_securityquestion_editText);
        confirmPassword = findViewById(R.id.register_passwordConfirmation_editText);
        securityQues = findViewById(R.id.register_securityquestion_Spinner);

        securityQues.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.my_spinner_item, getResources().getStringArray(R.array.security_questions));
        securityQues.setAdapter(adapter);
    }

    public void Register(View view) {
        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty() || securityAns.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_LONG).show();
        } else {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                sharedPreferences = this.getSharedPreferences("saved_info", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("username", username.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.putString("question",selectedQues);
                editor.putString("answer", securityAns.getText().toString());

                editor.commit();

                Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Password and Confirm Password did not match", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedQues = getResources().getStringArray(R.array.security_questions)[i];
        Toast.makeText(this, getResources().getStringArray(R.array.security_questions)[i], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}