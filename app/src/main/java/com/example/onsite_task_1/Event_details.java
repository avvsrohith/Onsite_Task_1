package com.example.onsite_task_1;

import static com.example.onsite_task_1.MainActivity.clickedEventItem;
import static com.example.onsite_task_1.MainActivity.newItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onsite_task_1.Database.ViewModel;
import com.example.onsite_task_1.RecyclerView.Event_item;

import java.util.Calendar;

public class Event_details extends AppCompatActivity {

    EditText title,desc,time;
    Button save,delete;
    ViewModel viewModel;
    TimePickerDialog timePickerDialog;
    Calendar calendar,pCalender;
    int minutes,hour;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        title=findViewById(R.id.et_title);
        desc=findViewById(R.id.et_description);
        time=findViewById(R.id.et_time);
        save=findViewById(R.id.btn_save);
        delete=findViewById(R.id.btn_delete);
        viewModel= ViewModelProviders.of(this).get(ViewModel.class);
        createNotificationChannel();

        if(clickedEventItem!=null && !newItem) {
            Event_item clickedItem = clickedEventItem;
            title.setText(clickedItem.getTitle());
            desc.setText(clickedItem.getDescription());
        }

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog=new TimePickerDialog(Event_details.this,(view, hourOfDay, minute) ->{
                    time.setText(hourOfDay+":"+minute);
                    calendar=Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                    calendar.set(Calendar.MINUTE,minute);
                    calendar.set(Calendar.SECOND,0);
                    calendar.set(Calendar.MILLISECOND,0);

                    if(minute-15<0){
                        minutes=60+minute-15;
                        hour=hourOfDay-1;
                    }
                    else{
                        minutes=minute;
                        hour=hourOfDay;
                    }

                    pCalender=Calendar.getInstance();
                    pCalender.set(Calendar.HOUR_OF_DAY,hour);
                    pCalender.set(Calendar.MINUTE,minutes);
                    pCalender.set(Calendar.SECOND,0);
                    pCalender.set(Calendar.MILLISECOND,0);

                    setNotificaton();

                },0,0,false );
                timePickerDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event_item event=new Event_item(title.getText().toString(),desc.getText().toString(),time.getText().toString());
                viewModel.addEvent(event);
                Toast.makeText(Event_details.this,"Event saved", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Event_details.this, MainActivity.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteEvent(clickedEventItem);
                cancelNotificaton();
                Toast.makeText(Event_details.this,"Event deleted", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Event_details.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(){

        CharSequence name="ReminderChannel";
        String Description="Notifies about events";
        int importance= NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel=new NotificationChannel("channel",name,importance);
        channel.setDescription(Description);
        NotificationManager notificationManager=getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void setNotificaton(){
        alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent=new Intent(this,AlarmReciever.class);
        pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    private void cancelNotificaton(){

        Intent intent=new Intent(this,AlarmReciever.class);
        pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);

        if(alarmManager==null){
            alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
    }
}