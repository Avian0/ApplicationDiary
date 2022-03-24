package com.example.thingie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.thingie.model.MyNotes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PreviewNotesActivity extends AppCompatActivity {
    TextView titleTextView, notesTextView;
    MyNotes myNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_notes);

        titleTextView = findViewById(R.id.notes_title_textview);
        notesTextView = findViewById(R.id.notes_textview);

        /*
         * Here checking if getIntent() has notes object
         */
        if(getIntent().hasExtra("notes")) {
            myNotes = getIntent().getParcelableExtra("notes");
        }

        if (myNotes != null) {
            getSupportActionBar().setTitle(convertDate(myNotes.getDate()));
            titleTextView.setText(myNotes.getTitle());
            notesTextView.setText(myNotes.getDetails());
        }
    }

    public String convertDate(String notesDate) {
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        try {
            //Current format being saved in DB is -> Thu Mar 24 10:16:31 GMT+05:30 2022
            Date mDate = df.parse(notesDate);
            //Changing to format -> Mar 24,2022 10:16 AM
            df = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
            String date = df.format(mDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return "NO DATE";
        }
    }
}