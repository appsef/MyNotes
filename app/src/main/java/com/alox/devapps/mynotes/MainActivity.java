package com.alox.devapps.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.alox.devapps.mynotes.Adapter.NoteAdapter;
import com.alox.devapps.mynotes.Database.NoteRepo;
import com.alox.devapps.mynotes.Database.Notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences mPreferences;

    public static RecyclerView mainView;
    private static NoteRepo noteRepo;

    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = this;

        noteRepo = new NoteRepo(MainActivity.this);

        mPreferences = getSharedPreferences("myNotes",MODE_PRIVATE);
        //views
        ImageButton addNote = findViewById(R.id.addNoteBtn);
        mainView = findViewById(R.id.mainRV);
        /*mainView.setLayoutManager(new GridLayoutManager(this,2));*/
        mainView.setLayoutManager(new LinearLayoutManager(this));
        /*StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mainView.setLayoutManager(layoutManager);*/
        mainView.setHasFixedSize(true);
        /*mainView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));*/

        /*Set<String> notes = mPreferences.getStringSet("notes",null);

        List<String> def = new ArrayList<>();
        def.add("Start creating notes`To create a note click the note button on the upper right corner.``Thank you");

        if (notes != null) {
            List<String> notesList = new ArrayList<>(notes);
            NoteAdapter noteAdapter = new NoteAdapter(MainActivity.this,notesList);
            mainView.setAdapter(noteAdapter);
        } else {
            NoteAdapter noteAdapter = new NoteAdapter(MainActivity.this,def);
            mainView.setAdapter(noteAdapter);
        }*/

        /*noteRepo.getNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                NoteAdapter noteAdapter = new NoteAdapter(MainActivity.this,notes);
                mainView.setAdapter(noteAdapter);
            }
        });*/

        refreshNotes();
        enableSwipeToDelete();


        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newNote = new Intent(MainActivity.this,AddActivity.class);
                startActivity(newNote);
            }
        });

        ImageView logo = findViewById(R.id.imageView);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InfoActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        boolean notNew = mPreferences.getBoolean("notNew",false);

        if (!notNew) {
            Intent welcome = new Intent(MainActivity.this,WelcomeActivity.class);
            welcome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            welcome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(welcome);
            finish();
        }
    }

    public static void refreshNotes() {

        noteRepo.getNotes().observe((LifecycleOwner) context, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                if (notes !=null) {
                    NoteAdapter noteAdapter = new NoteAdapter(context,notes);
                    mainView.setAdapter(noteAdapter);
                } else {
                    List<Notes> demo = new ArrayList<>();
                    Notes notes1 = new Notes();
                    notes1.setTitleNote("Hello! User");
                    notes1.setDescNote("Start creating notes");
                    notes1.setDateNote("Thanks");
                    demo.add(notes1);
                    NoteAdapter noteAdapter = new NoteAdapter(context,demo);
                    mainView.setAdapter(noteAdapter);
                }
            }
        });

        if (noteRepo.getNotes().getValue() !=null) {
            NoteAdapter noteAdapter = new NoteAdapter(context,noteRepo.getNotes().getValue());
            mainView.setAdapter(noteAdapter);
        } else {
            List<Notes> demo = new ArrayList<>();
            Notes notes1 = new Notes();
            notes1.setTitleNote("Hello! User");
            notes1.setDescNote("Start creating notes");
            notes1.setDateNote("Thanks");
            demo.add(notes1);
            NoteAdapter noteAdapter = new NoteAdapter(context,demo);
            mainView.setAdapter(noteAdapter);
        }

    }

    private void enableSwipeToDelete() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                NoteAdapter adapter = (NoteAdapter) mainView.getAdapter();
                if (adapter!=null) {
                    noteRepo.deleteNotes(adapter.getNote(viewHolder.getAdapterPosition()));
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(mainView);
    }
}