package com.alox.devapps.mynotes.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Notes.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao mDao();
}
