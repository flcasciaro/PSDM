package com.google.sample.cloudvision.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.sample.cloudvision.R;
import com.google.sample.cloudvision.async_classes.AsyncGetColour;
import com.google.sample.cloudvision.custom_objects.Colour;

/**
 * Created by Simone on 08/07/2017.
 */

public class EmptyTheBinActivity extends AppCompatActivity {

    private String storeId;
    private String networkAddress;
    private int percentage;
    private int trashId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_the_bin);
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        trashId = intent.getIntExtra("trashId", -1);
        storeId = intent.getStringExtra("storeId");
        networkAddress = intent.getStringExtra("networkAddress");
        percentage = intent.getIntExtra("percentage", 80);

        AsyncGetColour colour = new AsyncGetColour(this.getBaseContext(), this);
        colour.execute("http://" + networkAddress + "/api/v1.0/colour/" + trashId);

    }


    public void returnBackgroundedMethod(Colour colour) {
        try {
            ImageView someView = (ImageView) findViewById(R.id.selectedColour);
            TextView wasteConteinerText = (TextView) findViewById(R.id.wasteContainerText);
            someView.setBackgroundColor(Color.parseColor(colour.getCode()));
            if(colour.getWasteContainer().equals("WasteContainer_X")) {
                wasteConteinerText.setText("There aren't waste containers with enough free space.\n" +
                                           "Split your trash in diffent ones.");
            }
//            someView.setBackgroundColor(Color.parseColor("#ffFF0000"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackToMenuButton2Clicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("storeId", storeId);
        intent.putExtra("networkAddress", networkAddress);
        intent.putExtra("percentage", percentage);
        startActivity(intent);
    }

}
