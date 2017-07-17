package com.google.sample.cloudvision.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import com.google.sample.cloudvision.R;
import com.google.sample.cloudvision.async_classes.AsyncGetTrashList;
import com.google.sample.cloudvision.custom_objects.Trash;

public class ListBinAndWasteContainerActivity extends AppCompatActivity {

    private String storeId;
    private String networkAddress;
    private int percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bin_and_waste_container);
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        storeId = intent.getStringExtra("storeId");
        networkAddress = intent.getStringExtra("networkAddress");
        percentage = intent.getIntExtra("percentage", 80);

        AsyncGetTrashList trash = new AsyncGetTrashList(this.getBaseContext(), this);
        trash.execute("http://" + networkAddress + "/api/v1.0/amount/" + storeId); // inserisci URL server es: "http://10.0.2.2:5000/api/v1.0/tasks"
    }

    public void populateListBackground(List<Trash> trashList) {
        ListView trashListView = (ListView) findViewById(R.id.trashListView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, trashList);
        trashListView.setAdapter(adapter);

        trashListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final Trash item = (Trash) parent.getItemAtPosition(position);
                Intent intent = new Intent(parent.getContext(), TrashDetailActivity.class);
                intent.putExtra("storeId", storeId);
                intent.putExtra("networkAddress", networkAddress);
                intent.putExtra("percentage", percentage);
                intent.putExtra("trashId", item.getId());

                startActivity(intent);
            }
        });
    }
}
