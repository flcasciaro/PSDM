package com.google.sample.cloudvision;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.sample.cloudvision.activities.TrashDetailActivity;
import com.google.sample.cloudvision.custom_objects.Trash;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;

import static com.google.sample.cloudvision.GetJSON.GetTrashListFromJSON.readJsonStream;

public class PollingService extends IntentService {
    private List<Trash> trashList;
    private String storeId;
    private String networkAddress;
    private int percentage;

    Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    public PollingService()
    {
        super("PollingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        storeId = intent.getStringExtra("storeId");
        networkAddress = intent.getStringExtra("networkAddress");
        percentage = intent.getIntExtra("percentage", 80);

        while(true) {

            try{
                HttpURLConnection conn;
                URL urlObj = new URL("http://" + networkAddress + "/api/v1.0/amount/" + storeId);

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

            for(Trash t : trashList) {
                if(t.getType().equals("bin")) {
                    double volume = t.getCurrentVolume()/t.getMaxVolume() * 100;
                    if(volume >= percentage) {
                        NotificationCompat.Builder builder  = new NotificationCompat.Builder(this)
                                .setContentTitle("Empty the " + t.getType() + ". Id: " + t.getId())
                                .setContentText( (int)volume + "% full")
                                .setSmallIcon(R.drawable.bin_512_512)
                                .setAutoCancel(true)
                                .setSound(sound);
                        int NOTIFICATION_ID = 1;

                        Intent targetIntent = new Intent(this, TrashDetailActivity.class);
                        targetIntent.putExtra("networkAddress", networkAddress);
                        targetIntent.putExtra("trashId", t.getId());
                        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(contentIntent);
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(NOTIFICATION_ID, builder.build());
                    }
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy()
    {
        Log.i("PROVA SERVICE", "Distruzione Service");
    }
}