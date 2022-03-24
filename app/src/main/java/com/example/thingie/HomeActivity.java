package com.example.thingie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thingie.adapter.IActions;
import com.example.thingie.adapter.MyNotesAdapter;
import com.example.thingie.database.AppDatabase;
import com.example.thingie.model.MyNotes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements IActions {


    RecyclerView notesRecyclerView;
    FloatingActionButton addFabButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        notesRecyclerView = findViewById(R.id.notes_recyclerView);
        addFabButton = findViewById(R.id.add_note_fabButton);
        sharedPreferences = getSharedPreferences("saved_info", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(getIntent().hasExtra("username")) {
            getSupportActionBar().setTitle(String.format("%s's notes", getIntent().getStringExtra("username")));
        } else {
            getSupportActionBar().setTitle("Home");
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
    public void itemDeleted(MyNotes notes) {
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirm Deletion");
        alertDialog.setMessage("Are you sure you want to delete this notes?");
        alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do db operation to delete
                new DeleteNotesTask(HomeActivity.this).execute(notes);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //No implementation needed
                //TODO
            }
        });
        alertDialog.show();

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

    /***
     * This is the implementation of newly added method in IActions Interface
     * @param notes
     */
    @Override
    public void handleClick(MyNotes notes) {
        Intent intent = new Intent(this, PreviewNotesActivity.class);
        /*
         * Here sending entire notes object at once
         * instead of sending Title, date and details individually
        */
        intent.putExtra("notes", notes);
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

    class DeleteNotesTask extends AsyncTask<MyNotes, Void, Void> {
        AppDatabase db;
        HomeActivity context;

        public DeleteNotesTask(HomeActivity homeContext) {
            this.context = homeContext;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db = Room.databaseBuilder(context, AppDatabase.class, "mynotes").build();
        }

        @Override
        protected Void doInBackground(MyNotes... myNotes) {
            db.myNotesDAO().deleteNotes(myNotes[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            //Update ui - Interface
            Toast.makeText(context, "Deletion Successful", Toast.LENGTH_SHORT).show();
            new FetchDataTask(context).execute();
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