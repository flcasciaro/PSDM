package com.google.sample.cloudvision.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.sample.cloudvision.R;

/**
 * Created by Simone on 08/07/2017.
 */

public class SelectStoreActivity extends AppCompatActivity {

    private String storeId;
    private String networkAddress;
    private int percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_store);
    }

    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        storeId = intent.getStringExtra("storeId");
        networkAddress = intent.getStringExtra("networkAddress");
        percentage = intent.getIntExtra("percentage", 80);

        EditText networkText = (EditText) this.findViewById(R.id.networkText);
        EditText percentageText = (EditText) this.findViewById(R.id.percentageText);
        percentageText.setText(String.valueOf(percentage));
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.store1:
                if (checked)
                    storeId = "Negozio_1";
                    break;
            case R.id.store2:
                if (checked)
                    storeId = "Negozio_2";
                 break;
            case R.id.store3:
                if (checked)
                    storeId = "Negozio_3";
                    break;
        }

    }

    public void onConfirmNetworkButtonClicked(View view) {
        EditText networkText = (EditText) this.findViewById(R.id.networkText);
        EditText percentageText = (EditText) this.findViewById(R.id.percentageText);
        networkAddress = networkText.getText().toString();
        percentage = Integer.parseInt(percentageText.getText().toString());

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("storeId", storeId);
        intent.putExtra("networkAddress", networkAddress);
        intent.putExtra("percentage", percentage);
        startActivity(intent);
    }

}
