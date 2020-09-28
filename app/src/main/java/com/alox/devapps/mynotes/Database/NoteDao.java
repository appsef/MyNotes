package com.alox.devapps.mynotes.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Notes")
    LiveData<List<Notes>> getAll();

    @Query("SELECT * FROM Notes ORDER BY uid desc")
    LiveData<List<Notes>> getDescNotes();

    @Query("SELECT * FROM Notes WHERE uid =:noteID")
    LiveData<Notes> getNoteById(int noteID);

    @Insert
    void insertNote(Notes notes);

    @Delete
    void deleteNote(Notes notes);

    @Update
    void updateNote(Notes notes);
}
