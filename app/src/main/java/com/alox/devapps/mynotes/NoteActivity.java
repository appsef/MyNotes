package com.alox.devapps.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alox.devapps.mynotes.Database.NoteRepo;
import com.alox.devapps.mynotes.Database.Notes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NoteActivity extends AppCompatActivity {

    public static Notes notes;
    public static int position;
    TextView title;
    TextView desc;
    TextView dat;

    NoteRepo noteRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //init
        noteRepo = new NoteRepo(NoteActivity.this);

        //Views
        title = findViewById(R.id.titleText);
        desc = findViewById(R.id.descText);
        dat = findViewById(R.id.dateText);

        ImageButton backBtn = findViewById(R.id.backBtn);
        ImageButton shareBtn = findViewById(R.id.shareBtn);
        ImageButton delBtn = findViewById(R.id.deleteBtn);

        setNote(notes);
        Log.e("NoteID",notes.getUid()+"");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(notes);
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NoteActivity.this, "Under Construction.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setNote(Notes NoteRaw) {
        if (NoteRaw != null) {
            final String titleT = NoteRaw.getTitleNote();
            final String descT = NoteRaw.getDescNote();
            final String dateT = NoteRaw.getDateNote();
            title.setText(titleT);
            desc.setText(descT);
            dat.setText(dateT);


        } else {
            title.setText("No Notes");
        }
    }

    private void deleteNote(Notes NoteId) {
        if (notes != null) {
            noteRepo.deleteNotes(NoteId);
            MainActivity.refreshNotes();
            onBackPressed();
        }
    }
}