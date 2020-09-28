package com.alox.devapps.mynotes.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Notes {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title_note")
    public String titleNote;

    @ColumnInfo(name = "des_note")
    public String descNote;

    private String dateNote;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitleNote() {
        return titleNote;
    }

    public void setTitleNote(String titleNote) {
        this.titleNote = titleNote;
    }

    public String getDescNote() {
        return descNote;
    }

    public void setDescNote(String descNote) {
        this.descNote = descNote;
    }

    public String getDateNote() {
        return dateNote;
    }

    public void setDateNote(String dateNote) {
        this.dateNote = dateNote;
    }
}
