package com.example.NotesApp.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)

    private  int id;

    private String title;

    private String description;

    private String timeStamp;

    private int priority;

    public Note(String title, String description, String timeStamp, int priority) {
        this.title = title;
        this.description = description;
        this.timeStamp = timeStamp;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public int getPriority() {
        return priority;
    }
}
