package com.example.simone.psdm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.simone.psdm.async_classes.AsyncGetTrash;
import com.example.simone.psdm.R;
import com.example.simone.psdm.custom_objects.Trash;

public class TrashDetailActivity extends AppCompatActivity {

    int trashId;
    String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_detail);

        Bundle b = getIntent().getExtras();
        this.trashId = -1;
        if( b != null) {
            this.trashId = b.getInt("trashId");
        }

        //run an async process to get the details of the specified trash
        AsyncGetTrash trash = new AsyncGetTrash(this.getBaseContext(), this);
        // TODO aggiungi indirizzo es:"http://10.0.2.2:5000/api/v1.0/tasks/"
        trash.execute(""+this.trashId);

    }

    //function executed when all the task details are loaded
    public void returnBackgroundedMethod(Trash trash) {
        try {

            //insert the acquired information in the widgets contained in the layout
            EditText trashType = (EditText) this.findViewById(R.id.trashType);
            EditText trashTypeOfWaste = (EditText) this.findViewById(R.id.trashTypeOfWaste);
            EditText trashStoreName = (EditText) this.findViewById(R.id.trashStoreName);
            EditText trashId = (EditText) this.findViewById(R.id.trashId);
            EditText trashVolume = (EditText) this.findViewById(R.id.trashVolume);
            double maxVolume = trash.getMaxVolume();
            double currentVolume = trash.getCurrentVolume();

            trashType.setText(trash.getType());
            trashTypeOfWaste.setText(trash.getTypeOfWaste());
            trashStoreName.setText(trash.getStoreName());
            trashId.setText(trash.getId());
            trashVolume.setText(currentVolume + "/" + maxVolume);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void openEmptyTheBinActivity(View view) {
        Intent intent = new Intent(this, EmptyTheBinActivity.class);
        startActivity(intent);
    }

    public void setStoreId(String storeName) {
        this.storeId = storeName;
    }
}
