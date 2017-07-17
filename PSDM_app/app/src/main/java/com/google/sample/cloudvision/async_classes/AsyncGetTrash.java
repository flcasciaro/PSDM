package com.google.sample.cloudvision.async_classes;

/**
 * Created by Simone on 08/06/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.sample.cloudvision.activities.TrashDetailActivity;
import com.google.sample.cloudvision.custom_objects.Trash;

import static com.google.sample.cloudvision.GetJSON.GetSingleTrashFromJSON.readJsonStream;

public class AsyncGetTrash extends AsyncTask<String, Integer, Trash> {

    Context mContext;
    TrashDetailActivity mActivity;

    ProgressDialog barPD;
    public AsyncGetTrash(Context context, Activity activity){
        this.mContext = context;
        this.mActivity = (TrashDetailActivity) activity;

        barPD = new ProgressDialog(this.mContext);
    }

    @Override
    protected void onPreExecute() {
        try {
            barPD = barPD.show(this.mActivity, "Loading", "Please Wait");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Trash doInBackground(String... urls) {
        Trash returnedTrash = new Trash();

        try {
            HttpURLConnection conn;
            URL urlObj = new URL(urls[0]);

            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15000);
            conn.connect();

            InputStream in = new BufferedInputStream(conn.getInputStream());

            returnedTrash = readJsonStream(in);
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnedTrash;
    }

    @Override
    protected void onPostExecute(Trash trash) {
        try {
            mActivity.returnBackgroundedMethod(trash);
            barPD.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
