package com.google.sample.cloudvision.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.sample.cloudvision.async_classes.AsyncGetTrash;
import com.google.sample.cloudvision.R;
import com.google.sample.cloudvision.custom_objects.Trash;

public class TrashDetailActivity extends AppCompatActivity {

    private int trashId;
    private String storeId;
    private String networkAddress;
    private int percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_detail);

    }

    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        trashId = intent.getIntExtra("trashId", -1);
        storeId = intent.getStringExtra("storeId");
        networkAddress = intent.getStringExtra("networkAddress");
        percentage = intent.getIntExtra("percentage", 80);

        AsyncGetTrash trash = new AsyncGetTrash(this.getBaseContext(), this);
        trash.execute("http://" + networkAddress + "/api/v1.0/amount/" + trashId);
    }

    public void returnBackgroundedMethod(Trash trash) {

        try {

            TextView trashType = (TextView) this.findViewById(R.id.trashType);
            TextView trashTypeOfWaste = (TextView) this.findViewById(R.id.trashTypeOfWaste);
            TextView trashStoreName = (TextView) this.findViewById(R.id.trashStoreName);
            TextView trashId = (TextView) this.findViewById(R.id.trashId);
            TextView trashVolume = (TextView) this.findViewById(R.id.trashVolume);
            TextView trashVolumePercent = (TextView) this.findViewById(R.id.trashVolumePercent);
            ProgressBar trashProgressBar = (ProgressBar) this.findViewById(R.id.volumePercentProgressBar);
            Button emptyTheBinButton = (Button) this.findViewById(R.id.emptyTheBin);

            double maxVolume = trash.getMaxVolume();
            double currentVolume = trash.getCurrentVolume();

            trashType.setText(trash.getType());
            trashTypeOfWaste.setText(trash.getTypeOfWaste());
            trashStoreName.setText(trash.getStoreName());
            trashId.setText(String.valueOf(trash.getId()));
            trashVolume.setText(String.format("%.1f", currentVolume) + "/" + String.valueOf(maxVolume));
            trashVolumePercent.setText(String.valueOf((int)(currentVolume/maxVolume * 100)) + "%");
            trashProgressBar.setProgress((int)(currentVolume/maxVolume * 100));

            if(trash.getType().equals("wastecontainer")) {
                emptyTheBinButton.setVisibility(View.INVISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openEmptyTheBinActivity(View view) {
        Intent intent = new Intent(this, EmptyTheBinActivity.class);
        intent.putExtra("trashId", trashId);
        intent.putExtra("storeId", storeId);
        intent.putExtra("networkAddress", networkAddress);
        intent.putExtra("percentage", percentage);
        startActivity(intent);
    }

    public void onBackToMenuButton1Clicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("storeId", storeId);
        intent.putExtra("networkAddress", networkAddress);
        intent.putExtra("percentage", percentage);
        startActivity(intent);
    }

}
