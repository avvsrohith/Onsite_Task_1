package com.example.onsite_task_1.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onsite_task_1.Event_details;
import com.example.onsite_task_1.RecyclerView.Event_item;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Event_item>> allEvents;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
        allEvents=repository.getAllEvents();
    }

    public void addEvent(Event_item event_item){
        repository.addEvent(event_item);
    }
    public void updateEvent(Event_item event_item){
        repository.updateEvent(event_item);
    }
    public void deleteEvent(Event_item event_item){
        repository.deleteEvent(event_item);
    }
    public LiveData<List<Event_item>> getEvents(){
        return allEvents;
    }





}
