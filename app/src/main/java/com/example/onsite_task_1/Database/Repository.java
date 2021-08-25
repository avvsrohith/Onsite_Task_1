package com.example.onsite_task_1.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.onsite_task_1.RecyclerView.Event_item;

import java.util.List;

public class Repository {

    private DAO dao;
    private LiveData<List<Event_item>> allEvents;

    public Repository(Application application){
        DataBase database= DataBase.getInstance(application);
        dao=database.dao();
        allEvents= dao.getEvents();
    }

    public void addEvent(Event_item event_item){
        new InsertAsyncTask(dao).execute(event_item);
    }
    public void updateEvent(Event_item event_item){
        new UpdateAsyncTask(dao).execute(event_item);
    }
    public void deleteEvent(Event_item event_item){
        new DeleteAsyncTask(dao).execute(event_item);
    }
    public LiveData<List<Event_item>> getAllEvents(){
        return allEvents;
    }

    private static class InsertAsyncTask extends AsyncTask<Event_item,Void,Void>{

        private DAO dao;
        private InsertAsyncTask(DAO dao){
            this.dao=dao;
        }

        @Override
        protected Void doInBackground(Event_item... event_items) {
            dao.addEvent(event_items[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Event_item,Void,Void>{

        private DAO dao;
        private UpdateAsyncTask(DAO dao){
            this.dao=dao;
        }

        @Override
        protected Void doInBackground(Event_item... event_items) {
            dao.updateEvent(event_items[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Event_item,Void,Void>{

        private DAO dao;
        private DeleteAsyncTask(DAO dao){
            this.dao=dao;
        }

        @Override
        protected Void doInBackground(Event_item... event_items) {
            dao.deleteEvent(event_items[0]);
            return null;
        }
    }
}
