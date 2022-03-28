package com.application.appnimal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.application.appnimal.activities.SetNotificationActivity;
import com.example.appnimal.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // builds a notification with the following attributes
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Appnimal")
                .setSmallIcon(R.drawable.pets_icon)
                .setContentTitle("Don't forget:")
                .setContentText(SetNotificationActivity.message)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
    }
}
