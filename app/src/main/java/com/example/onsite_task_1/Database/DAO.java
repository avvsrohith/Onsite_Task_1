package com.example.onsite_task_1.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.onsite_task_1.RecyclerView.Event_item;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    void addEvent(Event_item event_item);

    @Update
    void updateEvent(Event_item event_item);

    @Delete
    void deleteEvent(Event_item event_item);

    @Query("SELECT * FROM events")
    LiveData<List<Event_item>> getEvents();

}
