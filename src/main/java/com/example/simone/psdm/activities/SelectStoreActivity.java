package com.example.simone.psdm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.example.simone.psdm.R;

/**
 * Created by Simone on 08/07/2017.
 */

public class SelectStoreActivity extends AppCompatActivity {

    String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_store);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.store1:
                if (checked)
                    storeId = "negozio1";
                    break;
            case R.id.store2:
                if (checked)
                    storeId = "negozio2";
                break;
            case R.id.store3:
                if (checked)
                    storeId = "negozio3";
                break;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("storeId", storeId);
        startActivity(intent);

    }

}
