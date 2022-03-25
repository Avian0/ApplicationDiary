package com.example.thingie.adapter;

import com.example.thingie.model.MyNotes;

public interface IActions {
    void itemDeleted(MyNotes notes);
    void editNotes(MyNotes notes);
    void handleClick(MyNotes notes); //Adding another method for handling item click from adapter
}
