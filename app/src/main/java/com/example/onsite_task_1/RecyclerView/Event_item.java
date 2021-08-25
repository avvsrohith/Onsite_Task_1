package com.example.onsite_task_1.RecyclerView;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class Event_item {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String time;

    public Event_item(String title, String description, String time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }
}
