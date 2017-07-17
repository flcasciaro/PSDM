package com.google.sample.cloudvision.async_classes;

/**
 * Created by Simone on 05/06/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.sample.cloudvision.activities.ListBinAndWasteContainerActivity;

import com.google.sample.cloudvision.custom_objects.Trash;

import static com.google.sample.cloudvision.GetJSON.GetTrashListFromJSON.readJsonStream;

public class AsyncGetTrashList extends AsyncTask<String, Integer, List<Trash>>{

    Context mContext;
    ListBinAndWasteContainerActivity lActivity;

    ProgressDialog barPD;

    public AsyncGetTrashList(Context context, ListBinAndWasteContainerActivity activity){
        this.mContext = context;
        this.lActivity = (ListBinAndWasteContainerActivity) activity;

        barPD = new ProgressDialog(this.mContext);
    }

    protected List<Trash> doInBackground(String... urls) {

        List<Trash> trashList = new ArrayList<>();
        try{


            HttpURLConnection conn;
            URL urlObj = new URL(urls[0]);

            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15000);
            conn.connect();

            InputStream in = new BufferedInputStream(conn.getInputStream());

            trashList = readJsonStream(in);
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trashList;
    }

    protected void onPreExecute() {
        try {
            barPD = barPD.show(this.lActivity, "Loading", "Please wait");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onPostExecute(List<Trash> result) {
        try {
            lActivity.populateListBackground(result);
            barPD.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
