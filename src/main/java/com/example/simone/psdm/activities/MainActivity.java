package com.example.simone.psdm.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.example.simone.psdm.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void openListBinAndWasteContainerActivity(View view){
        Intent intent = new Intent(this, ListBinAndWasteContainerActivity.class);
        startActivity(intent);
    }
}
