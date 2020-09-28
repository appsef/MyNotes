package com.alox.devapps.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alox.devapps.mynotes.Database.NoteRepo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AddActivity extends AppCompatActivity {

    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;

    private NoteRepo noteRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //init
        mPreferences = getSharedPreferences("myNotes",MODE_PRIVATE);
        mEditor = mPreferences.edit();
        noteRepo =new NoteRepo(AddActivity.this);

        //Views
        ImageButton addNoteBtn = findViewById(R.id.addNoteBtn);
        ImageButton cancelBtn = findViewById(R.id.cancelBtn);
        final EditText title = findViewById(R.id.titleField);
        final EditText desc = findViewById(R.id.descField);
        TextView dateT = findViewById(R.id.dateTextAA);

        SimpleDateFormat format1 = new SimpleDateFormat("d MMMM ''yy");
        dateT.setText(format1.format(new Date()));

        SimpleDateFormat format = new SimpleDateFormat("d MMM ''yy  h:mm a");
        final String date = format.format(new Date());

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!title.getText().toString().isEmpty() && !desc.getText().toString().isEmpty()) {
                    noteRepo.insertNote(title.getText().toString(),desc.getText().toString(),date);
                    MainActivity.refreshNotes();

                    onBackPressed();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}