package com.task.taskreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.taskreminder.R;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){

    //Toast.makeText(context, context.getString(R.string.Alertnotifty) + intent.getStringExtra("title") , Toast.LENGTH_LONG).show();
    String Title = intent.getStringExtra(context.getString(R.string.titttle));
    Intent x = new Intent(context, Alert.class);
        x.putExtra(context.getString(R.string.titttle), Title);
        x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(x);

    }
}