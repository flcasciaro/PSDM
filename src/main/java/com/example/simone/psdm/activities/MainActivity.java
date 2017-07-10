package com.example.simone.psdm.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.example.simone.psdm.R;

import java.util.EmptyStackException;

public class MainActivity extends AppCompatActivity {

    String storeId = "negozio1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        storeId = intent.getStringExtra("storeId");
    }

    public void onResume() {
        super.onResume();
    }

    public void openListBinAndWasteContainerActivity(View view){
        Intent intent = new Intent(this, ListBinAndWasteContainerActivity.class);
        intent.putExtra("storeId", storeId);
        startActivity(intent);
    }

    public void openSelectStoreActivity(View view) {
        Intent intent = new Intent(this, SelectStoreActivity.class);
        startActivity(intent);
    }
}
