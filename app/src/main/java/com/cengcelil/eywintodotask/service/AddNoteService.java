package com.cengcelil.eywintodotask.service;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.cengcelil.eywintodotask.R;

import static com.cengcelil.eywintodotask.other.Constants.ACTION_STOP_LISTEN;
import static com.cengcelil.eywintodotask.other.Constants.ADD_TO_DO_NOTE_NOTIFICATION_CHANNEL;
import static com.cengcelil.eywintodotask.other.Constants.OPEN_ADD_TODO_DIALOG_ACTION;

public class AddNoteService extends Service {
    private static final String TAG = "AddNoteService";
    public static Notification notification;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupNotification();
        startForeground(1, notification);
    }

    private void setupNotification() {
        if (notification == null) {
            Intent contentIntent = new Intent(AddNoteService.this, NotificationActionManageReceiver.class);
            contentIntent.setAction(OPEN_ADD_TODO_DIALOG_ACTION);
            PendingIntent contentPendingIntent = PendingIntent.getBroadcast(this, 1, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent actionIntent = new Intent(this, NotificationActionManageReceiver.class);
            actionIntent.setAction(ACTION_STOP_LISTEN);
            PendingIntent actionPendingIntent = PendingIntent.getBroadcast(this, 2, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            notification = new NotificationCompat.Builder(this, ADD_TO_DO_NOTE_NOTIFICATION_CHANNEL)
                    .setSmallIcon(R.drawable.add_note_notification_icon)
                    .setContentTitle(getString(R.string.tap_to_add_todo_note))
                    .setContentIntent(contentPendingIntent)
                    .addAction(R.drawable.ic_baseline_close_24,getString(R.string.exit),actionPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT).build();

        }

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servis stopped", Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true);
        }

    }


}
