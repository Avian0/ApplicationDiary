package com.example.thingie.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyNotes implements Parcelable { //See implements Parcelable for parceling process
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

    // In order to make a model parcelable we need to implement below methods
    public MyNotes(Parcel in){
        String[] data = new String[3];
        in.readStringArray(data);
        this.date = data[0];
        this.title = data[1];
        this.details = data[2];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {this.date,
                this.title,
                this.details});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MyNotes createFromParcel(Parcel in) {
            return new MyNotes(in);
        }

        public MyNotes[] newArray(int size) {
            return new MyNotes[size];
        }
    };
}
