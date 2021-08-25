package com.example.onsite_task_1.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onsite_task_1.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private List<Event_item> EventList;
    LayoutInflater inflater;
    Event_item currentEvent;
    private OnItemClickListener Listener;

    public interface OnItemClickListener {
        void onItemCLick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        Listener=listener;
    }


    public Adapter(Context context, List<Event_item> eventList){
        this.inflater=LayoutInflater.from(context);
        this.EventList=eventList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= inflater.inflate(R.layout.eventcard,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        currentEvent=EventList.get(position);

        holder.Title.setText(currentEvent.getTitle());
        holder.Description.setText(currentEvent.getDescription());
        holder.Time.setText(currentEvent.getTime());

    }

    @Override
    public int getItemCount() {
        return EventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Title,Description,Time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.tv_title);
            Description=itemView.findViewById(R.id.tv_description);
            Time=itemView.findViewById(R.id.tv_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            Listener.onItemCLick(position);
                        }
                    }
                }
            });

        }


    }

}
