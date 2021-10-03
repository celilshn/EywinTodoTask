package com.cengcelil.eywintodotask.di;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import dagger.hilt.android.HiltAndroidApp;

import static com.cengcelil.eywintodotask.other.Constants.NOTIFICATION_CHANNEL_DESC;
import static com.cengcelil.eywintodotask.other.Constants.NOTIFICATION_CHANNEL_ID;
import static com.cengcelil.eywintodotask.other.Constants.NOTIFICATION_CHANNEL_NAME;

@HiltAndroidApp
public class HiltApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(NOTIFICATION_CHANNEL_DESC);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
}