package com.example.dailyphoto;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent mainIntent = new Intent(this, MainActivity.class);

        NotificationManager notificationManager
            = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification noti = new Notification.Builder(this)
            .setAutoCancel(true)
            .setContentIntent(PendingIntent.getActivity(this, 0, mainIntent,
                              PendingIntent.FLAG_CANCEL_CURRENT))
            .setContentTitle("Daily Photo")
            .setContentText("Time for another photo")
            .setDefaults(Notification.DEFAULT_LIGHTS)
            .setSmallIcon(R.drawable.ic_launcher)
            .setTicker("Daily Photo: time for another photo")
            .setWhen(System.currentTimeMillis())
            .build();

        notificationManager.notify(0, noti);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
    

