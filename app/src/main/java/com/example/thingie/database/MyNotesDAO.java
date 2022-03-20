package com.example.thingie.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.thingie.model.MyNotes;
import java.util.List;

@Dao
public interface MyNotesDAO {
    @Insert
    void insert(MyNotes myNotes);

    @Query("SELECT * from MyNotes")
    List<MyNotes> getAll();

    @Delete
    void deleteNotes(MyNotes myNotes);

    @Update
    void updateNotes(MyNotes myNotes);
}
