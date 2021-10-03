package com.cengcelil.eywintodotask.service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.cengcelil.eywintodotask.ui.dialog.DialogActivity;
import static com.cengcelil.eywintodotask.other.Constants.ACTION_STOP_LISTEN;
import static com.cengcelil.eywintodotask.other.Constants.OPEN_ADD_TODO_DIALOG_ACTION;

public class NotificationActionManageReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationActionManag";

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if (OPEN_ADD_TODO_DIALOG_ACTION.equals(action)) {
            Intent popup = new Intent(context, DialogActivity.class);
            popup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(popup);
        }
        else if (ACTION_STOP_LISTEN.equals(action))
        {
            context.stopService(new Intent(context, AddNoteService.class));
        }
    }

}
