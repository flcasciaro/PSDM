package com.example.simone.psdm.async_classes;

/**
 * Created by Simone on 05/06/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.simone.psdm.activities.MainActivity;
import com.example.simone.psdm.activities.ListBinAndWasteContainerActivity;
import com.example.simone.psdm.custom_objects.Trash;
import com.example.simone.psdm.GetTrashListFromJSON;

public class AsyncGetTrashList extends AsyncTask<String, Integer, List<Trash>>{

    Context mContext;
    ListBinAndWasteContainerActivity lActivity;

    ProgressDialog barPD;
    public AsyncGetTrashList(Context context, Activity activity){
        this.mContext = context;
        this.lActivity = (ListBinAndWasteContainerActivity) activity;

        barPD = new ProgressDialog(this.mContext);
    }
    protected List<Trash> doInBackground(String... urls) {

        List<Trash> trashList = new ArrayList<>();
        try{
            HttpURLConnection conn;
            URL urlObj = new URL(""); //TODO aggiungi indirizzo connessione server

            //open an HTTP connection and send request
            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15000);
            conn.connect();

            //get response and convert it to string
            InputStream in = new BufferedInputStream(conn.getInputStream());

            //convert the inputStream obtained from the server in a List of task (ArrayList<Task>)
            trashList = GetTrashListFromJSON.readJsonStream(in);
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trashList;
    }

    @Override
    protected void onPreExecute() {
        try {
            barPD = barPD.show(this.lActivity, "Loading", "Please wait");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(List<Trash> result) {
        try {
            lActivity.populateListBackground(result);
            barPD.dismiss();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
