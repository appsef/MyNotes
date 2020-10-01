package com.alox.devapps.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alox.devapps.mynotes.Database.NoteRepo;
import com.alox.devapps.mynotes.Database.Notes;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NoteActivity extends AppCompatActivity {

    public static Notes notes;
    public static int position;
    TextView title;
    EditText desc;
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

        final ImageButton backBtn = findViewById(R.id.backBtn);
        ImageButton shareBtn = findViewById(R.id.shareBtn);
        final ImageButton delBtn = findViewById(R.id.deleteBtn);

        setNote(notes);
        Log.e("NoteID",notes.getUid()+"");
        final Snackbar snackbar = Snackbar.make(findViewById(R.id.noteActivity),"Changes made. Wanna Update?", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Save", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.setDescNote(desc.getText().toString());
                noteRepo.updateNote(notes);
            }
        });

        desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!desc.getText().toString().equals(notes.descNote)) {
                    snackbar.show();
                } else {
                    snackbar.dismiss();
                }
            }
        });

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