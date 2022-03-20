package com.example.thingie.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.thingie.model.MyNotes;

@Database(entities = {MyNotes.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MyNotesDAO myNotesDAO();
}
