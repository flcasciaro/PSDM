package com.example.simone.psdm.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.simone.psdm.async_classes.AsyncGetTrash;
import com.example.simone.psdm.async_classes.AsyncUpdateTrash;
import com.example.simone.psdm.R;
import com.example.simone.psdm.custom_objects.Trash;

public class TrashDetailActivity extends AppCompatActivity {

    int trashId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_detail);

        Bundle b = getIntent().getExtras();
        this.trashId = -1;
        if( b != null) {
            this.trashId = b.getInt("taskId");
        }

        //run an async process to get the details of the specified trash
        AsyncGetTrash trash = new AsyncGetTrash(this.getBaseContext(), this);
        trash.execute(""+this.trashId);

    }

    //function executed when all the task details are loaded
    public void returnBackgroundedMethod(Trash trash) {
        try {

            //insert the acquired information in the widgets contained in the layout
            EditText taskDescriptionet = (EditText) this.findViewById(R.id.taskDescription);
            CheckBox urgentcb = (CheckBox) this.findViewById(R.id.urgentCheckBox);
            taskDescriptionet.setText(task.getDescription());
            if (task.getUrgent() == 1)
                urgentcb.setChecked(true);
            else
                urgentcb.setChecked(false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
