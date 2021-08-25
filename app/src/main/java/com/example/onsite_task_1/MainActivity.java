package com.example.onsite_task_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.onsite_task_1.Database.ViewModel;
import com.example.onsite_task_1.RecyclerView.Adapter;
import com.example.onsite_task_1.RecyclerView.Event_item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {
    RecyclerView recyclerView;
    List<Event_item> EventList;
    FloatingActionButton addButton;
    TextView events;
    public static Event_item clickedEventItem;
    public static boolean newItem=true;
    ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventList=new ArrayList<>();
        addButton=findViewById(R.id.add);
        events=findViewById(R.id.events);
        viewModel= ViewModelProviders.of(MainActivity.this).get(ViewModel.class);
        viewModel.getEvents().observe(this, new Observer<List<Event_item>>() {
            @Override
            public void onChanged(List<Event_item> event_items) {
                EventList=event_items;
                events.setVisibility(event_items.isEmpty() ? View.VISIBLE:View.GONE);
                PutInRecyclerView(event_items);
            }
        });

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Event_item event_item=new Event_item("Title","desc","time");
        EventList.add(event_item);




        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newItem=true;
                Intent intent=new Intent(MainActivity.this,Event_details.class);
                startActivity(intent);
            }
        });


    }

    public void PutInRecyclerView(List<Event_item> eventList){
        Adapter adapter=new Adapter(this,eventList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);
    }


    @Override
    public void onItemCLick(int position) {
        newItem=false;
        Intent intent=new Intent(this,Event_details.class);
        clickedEventItem=EventList.get(position);
        startActivity(intent);
    }
}