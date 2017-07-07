package com.example.simone.psdm.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import com.example.simone.psdm.R;
import com.example.simone.psdm.async_classes.AsyncGetTrashList;
import com.example.simone.psdm.custom_objects.Trash;

public class ListBinAndWasteContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bin_and_waste_container);

        // Importare stato bidoni da server tramite Async?

        AsyncGetTrashList trash = new AsyncGetTrashList(this.getBaseContext(), this);
        trash.execute("");
    }

    public void onResume() {
        //when the user come back to the main activity after using other activities, the list of tasks should be updated
        super.onResume();
        //run an async process to get the list of tasks
        AsyncGetTrashList task = new AsyncGetTrashList(this.getBaseContext(), this);
        task.execute(""); // TODO inserisci URL server
    }

    //this method will be invoked when the async process finishes returning the acquired list of tasks
    public void populateListBackground(List<Trash> trashList) {
        ListView trashListView = (ListView) findViewById(R.id.trashListView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, trashList);
        trashListView.setAdapter(adapter);

        trashListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final Trash item = (Trash) parent.getItemAtPosition(position);
                Intent intent = new Intent(parent.getContext(), TrashDetailActivity.class);
            }
        });
    }
}
