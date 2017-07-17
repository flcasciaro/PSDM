package com.google.sample.cloudvision.async_classes;

/**
 * Created by Simone on 10/07/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.sample.cloudvision.activities.EmptyTheBinActivity;
import com.google.sample.cloudvision.custom_objects.Colour;

import static com.google.sample.cloudvision.GetJSON.GetColourFromJSON.readJsonStream;

public class AsyncGetColour extends AsyncTask<String, Integer, Colour>{

    Context mContext;
    EmptyTheBinActivity mActivity;

    ProgressDialog barPD;
    public AsyncGetColour(Context context, Activity activity) {
        this.mContext = context;
        this.mActivity = (EmptyTheBinActivity) activity;

        barPD = new ProgressDialog(this.mContext);
    }

    @Override
    protected void onPreExecute() {
        try{
            barPD = barPD.show(this.mActivity, "Loading", "Please Wait");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Colour doInBackground(String... urls) {
        Colour returnedColour = new Colour();

        try {
            HttpURLConnection conn;
            URL urlObj = new URL(urls[0]);

            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15000);
            conn.connect();

            InputStream in = new BufferedInputStream(conn.getInputStream());

            returnedColour = readJsonStream(in);
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnedColour;
    }

    @Override
    protected void onPostExecute(Colour colour) {
        try {
            mActivity.returnBackgroundedMethod(colour);
            barPD.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
