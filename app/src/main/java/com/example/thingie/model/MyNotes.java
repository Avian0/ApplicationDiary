package com.example.thingie.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyNotes {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "details")
    String details;

    public MyNotes(String date, String title, String details) { //constructor
        this.date = date;
        this.title = title;
        this.details = details;
    }

    public MyNotes(String date){
        this.date = date;
    }

    public MyNotes(){

    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) { this.details = details;
    }
}
