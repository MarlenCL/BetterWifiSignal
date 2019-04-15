package com.betterwifisignal.betterwifisignal;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Service extends android.app.Service {

    public static final String YOUR_CUSTOM_ACTION = "d";
    private SearchWifi searchWifi;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTimer = new Timer();
        mTimer.schedule(timerTask, 2000, 30000);
        searchWifi = new SearchWifi(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private Timer mTimer;
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            boolean connect = false;
            int i = 0;
            List<WifiElementModel> wifiElements = searchWifi.scanWifi();
            if (wifiElements.size() > 0) {
                do {
                    WifiElementModel element = wifiElements.get(i);
                    if (searchWifi.isRegisteredWifi(element.getSSDI())) {
                        if (searchWifi.getConnectionSSID() != null && searchWifi.getConnectionStrength() != 0) {
                            if (!(element.getSSDI().equals(searchWifi.getConnectionSSID())) && element.getStrength() > searchWifi.getConnectionStrength()) {
                                searchWifi.connectWifi(element);
                                Log.d("equal1", connect + "");
                                connect = true;
                            }
                        } else {
                            searchWifi.connectWifi(element);
                            connect = true;
                        }
                    }
                    i++;
                    if (i == wifiElements.size()) {
                        connect = true;
                    }
                } while (!connect);
                Log.d("d", wifiElements.toString());
                Intent intent = new Intent(YOUR_CUSTOM_ACTION);
                intent.putExtra("s", (Serializable) wifiElements);

                // put extras in the intent as you wish
                sendBroadcast(intent);

            }
        }
    };

    public void onDestroy() {
        try {
            mTimer.cancel();
            timerTask.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent("com.android.techtrainner");
        intent.putExtra("yourvalue", "torestore");
        sendBroadcast(intent);
    }

}
