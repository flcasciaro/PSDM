package com.google.sample.cloudvision.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.google.sample.cloudvision.PollingService;
import com.google.sample.cloudvision.R;

public class MainActivity extends AppCompatActivity {

    private String storeId;
    private String networkAddress;
    private int percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storeId = "Negozio_1";
        networkAddress = "192.168.0.1:8080";
        percentage = 80;
    }

    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        storeId = intent.getStringExtra("storeId");
        networkAddress = intent.getStringExtra("networkAddress");
        percentage = intent.getIntExtra("percentage", 80);
    }

    public void openListBinAndWasteContainerActivity(View view){
        Intent intent = new Intent(this, ListBinAndWasteContainerActivity.class);
        Intent intentService = new Intent(this, PollingService.class);
        intent.putExtra("storeId", storeId);
        intent.putExtra("networkAddress", networkAddress);
        intent.putExtra("percentage", percentage);
        intentService.putExtra("storeId", storeId);
        intentService.putExtra("networkAddress", networkAddress);
        intentService.putExtra("percentage", percentage);
        startService(intentService);
        startActivity(intent);
    }

    public void openSelectStoreActivity(View view) {
        Intent intent = new Intent(this, SelectStoreActivity.class);
        intent.putExtra("storeId", storeId);
        intent.putExtra("networkAddress", networkAddress);
        intent.putExtra("percentage", percentage);
        startActivity(intent);
    }

    public void openObjectRecognitionActivity(View view) {
        Intent intent = new Intent(this, ObjectRecognitionActivity.class);
        intent.putExtra("storeId", storeId);
        intent.putExtra("networkAddress", networkAddress);
        intent.putExtra("percentage", percentage);
        startActivity(intent);
    }

    public void openSelectAlarmTimeActivity(View view) {
        Intent intent = new Intent(this, SelectAlarmTimeActivity.class);
        intent.putExtra("storeId", storeId);
        intent.putExtra("networkAddress", networkAddress);
        intent.putExtra("percentage", percentage);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopService(new Intent(this,PollingService.class));
    }
}
