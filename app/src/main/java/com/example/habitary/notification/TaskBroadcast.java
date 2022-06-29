package com.example.habitary.notification;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.habitary.MainActivity;
import com.example.habitary.R;

import java.util.Date;

public class TaskBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            String name = bundle.getString("name");
            String desc = bundle.getString("description");
            int id = bundle.getInt("id");
            Log.d("nazwa",name);
            Log.d("desc",desc);
            Intent intentt = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentt, PendingIntent.FLAG_IMMUTABLE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "taskHabitary")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_calendar)
                    .setContentTitle(name)
                    .setContentText(desc)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            notificationManager.notify(id, builder.build());
        }
        else{
            Intent intentt = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentt, PendingIntent.FLAG_IMMUTABLE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "taskHabitary")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_calendar)
                    .setContentTitle("Task")
                    .setContentText("Test of notification")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

            notificationManager.notify(m, builder.build());
        }
    }
}
