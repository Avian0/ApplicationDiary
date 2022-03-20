package com.example.thingie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.thingie.adapter.IActions;
import com.example.thingie.adapter.MyNotesAdapter;
import com.example.thingie.database.AppDatabase;
import com.example.thingie.model.MyNotes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements IActions {


    TextView name;
    RecyclerView notesRecyclerView;
    FloatingActionButton addFabButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Home");
        name = findViewById(R.id.username_textView);
        notesRecyclerView = findViewById(R.id.notes_recyclerView);
        addFabButton = findViewById(R.id.add_note_fabButton);
        sharedPreferences = getSharedPreferences("saved_info", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(getIntent().hasExtra("username")) {
            name.setText(getIntent().getStringExtra("username"));
        } else {
            name.setText("no username found");
        }

        //prepareDummyDate()

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        notesRecyclerView.setLayoutManager(manager);

        addFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent
                Intent intent = new Intent(HomeActivity.this, AddNotesActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        new FetchDataTask(this).execute();
    }

    @Override
    public void itemDeleted() {
        new FetchDataTask(this).execute();
    }

    @Override
    public void editNotes(MyNotes notes) {
        //Intent to another screen - Add Notes Activity
        Intent intent = new Intent(this, AddNotesActivity.class);
        intent.putExtra("title", notes.getTitle());
        intent.putExtra("details", notes.getDetails());
        intent.putExtra("date", notes.getDate());
        intent.putExtra("id", notes.getId());
        startActivity(intent);
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
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class FetchDataTask extends AsyncTask<Void, Void, List<MyNotes>> {
        AppDatabase db;
        List<MyNotes> notesList;
        HomeActivity mContext;

        public FetchDataTask(HomeActivity context) {
            this.mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mynotes").build();
        }

        @Override
        protected List<MyNotes> doInBackground(Void... voids) {
            notesList = db.myNotesDAO().getAll();
            return notesList;
        }

        @Override
        protected void onPostExecute(List<MyNotes> myNotes) {
            super.onPostExecute(myNotes);
            notesRecyclerView.setAdapter(new MyNotesAdapter(getApplicationContext(), myNotes, mContext));
        }
    }

    /*private void prepareDummyDate() {
        MyNotes notes = new MyNotes("10th Jan 2022", "Monday", "Good morning!!");

        MyNotes notes1 = new MyNotes();
        notes1.setDate("11th Jan, 2022");
        notes1.setTitle("Tuesday");
        notes1.setDetails("Good Evening!!!");

        MyNotes notes2 = new MyNotes("12th Jan, 2022");
        notes2.setTitle("Tuesday");
        notes2.setDetails("Good Evening!!!");

        myNotesList = new ArrayList<MyNotes>();
        myNotesList.add(notes);
        myNotesList.add(notes1);
        myNotesList.add(notes2);
    }*/
}