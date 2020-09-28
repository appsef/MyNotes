package com.alox.devapps.mynotes.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class NoteRepo {

    private String DB_NAME = "db_notes";

    private NoteDatabase mNoteDatabase;

    public NoteRepo(Context context) {
        mNoteDatabase = Room.databaseBuilder(context, NoteDatabase.class, DB_NAME).build();
    }

    public void insertNote(String NoteTitle, String NoteDesc, String NoteDate) {
        Notes notes = new Notes();
        notes.setTitleNote(NoteTitle);
        notes.setDateNote(NoteDate);
        notes.setDescNote(NoteDesc);
        insertNote(notes);
    }

    public void insertNote(final Notes notes) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mNoteDatabase.mDao().insertNote(notes);
                return null;
            }
        }.execute();
    }

    public void updateTask(final Notes notes) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mNoteDatabase.mDao().updateNote(notes);
                return null;
            }
        }.execute();
    }

    public void deleteNotes(final int id) {
        final LiveData<Notes> note = getNote(id);
        if (note != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    mNoteDatabase.mDao().deleteNote(note.getValue());
                    return null;
                }
            }.execute();
        }
    }

    public void deleteNotes(final Notes note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mNoteDatabase.mDao().deleteNote(note);
                return null;
            }
        }.execute();

    }

    private LiveData<Notes> getNote(int id) {
        return mNoteDatabase.mDao().getNoteById(id);
    }


    public LiveData<List<Notes>> getNotes() {
        return mNoteDatabase.mDao().getDescNotes();
    }
}
