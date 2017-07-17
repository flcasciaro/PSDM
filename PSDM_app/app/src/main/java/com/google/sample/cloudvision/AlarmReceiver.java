package com.google.sample.cloudvision;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.sample.cloudvision.activities.ListBinAndWasteContainerActivity;

/**
 * Created by Simone on 16/07/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            String storeId = intent.getStringExtra("storeId");
            String networkAddress = intent.getStringExtra("networkAddress");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentTitle("Check if your bins are full!")
                    .setContentText("")
                    .setSmallIcon(R.drawable.bin_512_512)
                    .setAutoCancel(true)
                    .setSound(sound);
            int NOTIFICATION_ID = 2;

            Intent intentList = new Intent(context, ListBinAndWasteContainerActivity.class);
            intentList.putExtra("networkAddress", networkAddress);
            intentList.putExtra("storeId", storeId);

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intentList, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, builder.build());

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
