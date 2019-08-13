package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;


public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String message = intent.getStringExtra("toastMessage");
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(2); //dismiss  the notification
    }
}
