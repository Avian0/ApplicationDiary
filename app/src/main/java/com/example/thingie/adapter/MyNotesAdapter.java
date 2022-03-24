package com.example.thingie.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.thingie.R;
import com.example.thingie.database.AppDatabase;
import com.example.thingie.model.MyNotes;


import java.util.ArrayList;
import java.util.List;


public class MyNotesAdapter extends RecyclerView.Adapter<MyNotesAdapter.MyNotesDataHolder> {

    Context context;
    List<MyNotes> myNotes;
    IActions mIActions;

    public MyNotesAdapter(Context context, List<MyNotes> myNotes, IActions iActions) {
        this.context = context;
        this.myNotes = myNotes;
        this.mIActions = iActions;
    }

    @NonNull
    @Override
    public MyNotesAdapter.MyNotesDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.my_notes_row, parent, false);
        return new MyNotesDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNotesAdapter.MyNotesDataHolder holder, int position) {
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Using newly added interface method for handling click action
                mIActions.handleClick(myNotes.get(position));
//                Toast.makeText(context, "You have clicked item: "+(position+1), Toast.LENGTH_SHORT).show();
            }
        });
        holder.date.setText(myNotes.get(position).getDate());
        holder.details.setText(myNotes.get(position).getDetails());
        holder.title.setText(myNotes.get(position).getTitle());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIActions.itemDeleted(myNotes.get(position));
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIActions.editNotes(myNotes.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {

        return myNotes.size();
    }

    class MyNotesDataHolder extends RecyclerView.ViewHolder {
        TextView date, title, details;
        ConstraintLayout rowLayout;
        Button delete, edit;
        public MyNotesDataHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_textView);
            title = itemView.findViewById(R.id.title_textView);
            details = itemView.findViewById(R.id.details_textView);
            rowLayout = itemView.findViewById(R.id.row_layout);
            delete = itemView.findViewById(R.id.delete_button);
            edit = itemView.findViewById(R.id.edit_button);
        }
    }
}
