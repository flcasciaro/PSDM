package com.google.sample.cloudvision.activities;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.sample.cloudvision.AlarmReceiver;
import com.google.sample.cloudvision.R;

import java.sql.Time;
import java.util.Calendar;


/**
 * Created by Simone on 11/07/2017.
 */

public class SelectAlarmTimeActivity extends AppCompatActivity {

    private String storeId;
    private String networkAddress;
    private int percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_alarm_time);
    }

    @TargetApi(23)
    public void onSetAlarmButtonClicked(View view) {
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        Intent intent = getIntent();
        storeId = intent.getStringExtra("storeId");
        networkAddress = intent.getStringExtra("networkAddress");
        percentage = intent.getIntExtra("percentage", 80);

        Intent intentTarget = new Intent(this, AlarmReceiver.class);
        intentTarget.putExtra("storeId", storeId);
        intentTarget.putExtra("networkAddress", networkAddress);
        intentTarget.putExtra("percentage", percentage);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intentTarget, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();

        int h = timePicker.getHour();
        int m = timePicker.getMinute();

        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.SECOND, 0);

        Toast.makeText(SelectAlarmTimeActivity.this, "Alarm set at " + h + ":" + m, Toast.LENGTH_SHORT).show();

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

        Intent intentMain = new Intent(this, MainActivity.class);
        intent.putExtra("storeId", storeId);
        intent.putExtra("networkAddress", networkAddress);
        intent.putExtra("percentage", percentage);
        startActivity(intentMain);

    }

}
