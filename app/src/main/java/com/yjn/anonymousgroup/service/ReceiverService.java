package com.yjn.anonymousgroup.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.AppUtils;
import com.yjn.anonymousgroup.R;
import com.yjn.anonymousgroup.activity.MainActivity;
import com.yjn.anonymousgroup.udp.CanChatUdpReceiver;
import com.yjn.anonymousgroup.udp.Udp;

public class ReceiverService extends Service {
    Notification notification;
    NotificationManager manager;
    Thread canChatUdpReceiver;

    public ReceiverService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        udpReceiver();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (canChatUdpReceiver != null){
            canChatUdpReceiver.interrupt();
        }
    }

    private void createNotificationChannel() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent hangIntent = new Intent(this, MainActivity.class);

        PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 1001, hangIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String CHANNEL_ID = AppUtils.getAppPackageName();//应用频道Id唯一值， 长度若太长可能会被截断，
        String CHANNEL_NAME = AppUtils.getAppPackageName();//最长40个字符，太长会被截断
        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("后台运行")
                .setSmallIcon(R.mipmap.logo)
                .setContentIntent(hangPendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
        manager.createNotificationChannel(notificationChannel);

        startForeground(1,notification);
    }

    private void udpReceiver(){
        canChatUdpReceiver = new CanChatUdpReceiver();
        canChatUdpReceiver.start();
    }
}