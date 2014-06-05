/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.snippet.app;

import com.snippet.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;

public class NotificationActivity extends Activity {

    private int mCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    public void onButton1(final View v) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(
                        BitmapFactory.decodeResource(getResources(),
                                android.R.drawable.sym_def_app_icon))
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setTicker("Ticker")
                .setContentTitle("ContentTitle")
                .setContentText(
                        "ContentText: Hello world! The quick brown fox jumps over the lazy dog.")
                .setSubText("SubText")
                .setContentInfo("10")
                .setAutoCancel(true);
        Intent resultIntent = new Intent(this, NotificationResultActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationResultActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                        );
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(1, builder.build());
    }

    public void onButton2(final View v) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setLargeIcon(
                        BitmapFactory.decodeResource(getResources(),
                                android.R.drawable.sym_def_app_icon))
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setTicker("Silent notification")
                .setContentTitle("This is a silent notification")
                .setContentText(
                        "ContentText: Hello world! The quick brown fox jumps over the lazy dog.")
                .setSubText("SubText")
                .setContentInfo("10")
                .setAutoCancel(true);
        Intent resultIntent = new Intent(this, NotificationResultActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationResultActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                        );
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(2, builder.build());
    }

    public void onButton3(final View v) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(
                        BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_launcher))
                .setSmallIcon(R.drawable.ic_stat_sample)
                .setTicker("Custom icons")
                .setContentTitle("This is a custom icon notification")
                .setContentText(
                        "ContentText: Hello world! The quick brown fox jumps over the lazy dog.")
                .setSubText("SubText")
                .setContentInfo("10")
                .setAutoCancel(true);
        Intent resultIntent = new Intent(this, NotificationResultActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationResultActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                        );
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(3, builder.build());
    }

    public void onButton4(final View v) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(
                        BitmapFactory.decodeResource(getResources(),
                                android.R.drawable.sym_def_app_icon))
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setTicker("Update notification")
                .setContentTitle("Update count when you tap button.")
                .setContentText(
                        "ContentText: Hello world! The quick brown fox jumps over the lazy dog.")
                .setSubText("SubText")
                .setNumber(++mCount)
                .setAutoCancel(true);
        Intent resultIntent = new Intent(this, NotificationResultActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationResultActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                        );
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(4, builder.build());
    }

    public void onButton5(final View v) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setLargeIcon(
                        BitmapFactory.decodeResource(getResources(),
                                android.R.drawable.sym_def_app_icon))
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setTicker("Light")
                .setContentTitle("ContentTitle")
                .setContentText(
                        "ContentText: Hello world! The quick brown fox jumps over the lazy dog.")
                .setSubText("SubText")
                .setContentInfo("10")
                .setAutoCancel(true);
        Intent resultIntent = new Intent(this, NotificationResultActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationResultActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                        );
        builder.setContentIntent(resultPendingIntent);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.ledARGB = 0xff00ff00;
        notification.ledOnMS = 300;
        notification.ledOffMS = 1000;
        notificationManager.notify(5, notification);
    }

    public void onButton6(final View v) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(
                        BitmapFactory.decodeResource(getResources(),
                                android.R.drawable.sym_def_app_icon))
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setTicker("Running..")
                .setContentTitle("Ongoing notification")
                .setContentText(
                        "Remove me by NotificationManager#cancel(int).")
                .setSubText("SubText")
                .setContentInfo("10")
                .setOngoing(true);
        Intent resultIntent = new Intent(this, NotificationResultActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationResultActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                        );
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(6, builder.build());
    }

    public void onButton6Dismiss(final View v) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(6);
    }

    public void onButton7(final View v) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(
                        BitmapFactory.decodeResource(getResources(),
                                android.R.drawable.sym_def_app_icon))
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setTicker("I will alert only this time!")
                .setContentTitle("ContentTitle")
                .setContentText(
                        "ContentText: Hello world! The quick brown fox jumps over the lazy dog.")
                .setSubText("SubText")
                .setContentInfo("10")
                .setOnlyAlertOnce(true)
                .setAutoCancel(true);
        Intent resultIntent = new Intent(this, NotificationResultActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationResultActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                        );
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(7, builder.build());
    }

}
