package com.task.taskreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class NotifReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        String msg =intent.getStringExtra("toastmsg");
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();

    }
}
