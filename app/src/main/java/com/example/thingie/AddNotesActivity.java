package com.example.thingie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thingie.database.AppDatabase;
import com.example.thingie.model.MyNotes;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class AddNotesActivity extends AppCompatActivity {
    TextInputEditText title, notes;
    TextView addNotes;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        getSupportActionBar().setTitle("Add Notes");
        title = findViewById(R.id.title_editText);
        notes = findViewById(R.id.notes_editText);
        addNotes = findViewById(R.id.title_textView);
        sharedPreferences = getSharedPreferences("saved_info", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (getIntent().hasExtra("title") && getIntent().hasExtra("details")) {
            //Edit the content
            title.setText(getIntent().getStringExtra("title"));
            notes.setText(getIntent().getStringExtra("details"));
            isUpdate = true;
        }
        if (!TextUtils.isEmpty(notes.getText().toString())){
            addNotes.setText("Edit Notes");
        }
    }
    public void SaveNotes(View view) {

        if (title.getText().toString().isEmpty() || notes.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title and Notes are required", Toast.LENGTH_SHORT).show();
            isUpdate = true;
        } else {
            MyNotes myNotes = new MyNotes();
            myNotes.setTitle(title.getText().toString());
            myNotes.setDetails(notes.getText().toString());

            if (isUpdate){
                myNotes.setId(getIntent().getIntExtra("id", 0));
                myNotes.setDate(getIntent().getStringExtra("date"));
            }
            else
                myNotes.setDate(new Date().toString());

            MyTask task = new MyTask();
            task.execute(myNotes);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logoutmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout){
            editor.putBoolean("logout", true);
            editor.commit();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    class MyTask extends AsyncTask<MyNotes, Void, Void> {

        AppDatabase db;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mynotes").build();
        }

        @Override
        protected Void doInBackground(MyNotes... myNotes) {
            if (isUpdate) {
                db.myNotesDAO().updateNotes(myNotes[0]);
            } else {
                db.myNotesDAO().insert(myNotes[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast.makeText(AddNotesActivity.this, "Notes saved successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
