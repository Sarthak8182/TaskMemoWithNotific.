package com.task.taskreminder;


import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.taskreminder.R;

public class Notification extends BroadcastReceiver {
  @Override
    public void onReceive(Context context, Intent intent) {
        String Title = intent.getStringExtra(context.getString(R.string.titttle));
        String content = intent.getStringExtra(context.getString(R.string.alert_content));

        Notif notificationHelper = new Notif(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification( context, Title,content);
        notificationHelper.getManager().notify(1, nb.build());



    }

}