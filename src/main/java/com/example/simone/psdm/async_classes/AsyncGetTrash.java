package com.example.simone.psdm.async_classes;

/**
 * Created by Simone on 08/06/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.simone.psdm.activities.TrashDetailActivity;
import com.example.simone.psdm.custom_objects.Trash;

import static com.example.simone.psdm.GetSingleTrashFromJSON.readJsonStream;

public class AsyncGetTrash extends AsyncTask<String, Integer, Trash> {

    //context and activity of the activity that called the service
    Context mContext;
    TrashDetailActivity mActivity;

    ProgressDialog barPD;
    public AsyncGetTrash(Context context, Activity activity){
        this.mContext = context;
        this.mActivity = (TrashDetailActivity) activity;

        barPD = new ProgressDialog(this.mContext);
    }

    //while data is acquired, we show a progress bar
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
            //open an HTTP connection and send request
            HttpURLConnection conn;
            URL urlObj = new URL(urls[0]);

            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15000);
            conn.connect();

            //get response and convert it to string
            InputStream in = new BufferedInputStream(conn.getInputStream());

            //convert the inputStream obtained from the server in a Task
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
