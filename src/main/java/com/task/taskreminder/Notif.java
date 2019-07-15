package com.task.taskreminder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.taskreminder.R;


public class Notif extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private NotificationManager mManager;

    private static final String NOTIFICATION_TAG = "Notif";

    public Notif(Context base) {
        super(base);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }


    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        }
        return mManager;

    }


    public NotificationCompat.Builder getChannelNotification(Context context,String title , String content) {
        long[] vibrate = { 0, 100, 200, 300 };
        Intent activityintent = new Intent(this,MainActivity.class);
        PendingIntent contentintent = PendingIntent.getActivity(this,0,activityintent,0);

        Intent brodacastintent = new Intent(this,NotifReceiver.class);
        brodacastintent.putExtra("toastmsg","Title:- "+title+"\nContent:- "+content);
        PendingIntent actionintent =PendingIntent.getBroadcast(this,0,brodacastintent,PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher,"PopUp",actionintent)
                .setContentIntent(contentintent)
                .setOnlyAlertOnce(true)
                .setColor(Color.RED)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + context.getPackageName() + "/raw/alarmtone"))
                .setVibrate(vibrate);
    }

}






