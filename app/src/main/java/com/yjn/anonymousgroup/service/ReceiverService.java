package com.yjn.anonymousgroup.service;

import static com.yjn.anonymousgroup.util.Net.checkWifi;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.yjn.anonymousgroup.R;
import com.yjn.anonymousgroup.activity.MainActivity;
import com.yjn.anonymousgroup.udp.CanChatUdpReceiver;
import com.yjn.anonymousgroup.udp.CanChatUdpSend;
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
        ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Void>() {
            @Override
            public Void doInBackground() throws Throwable {
                new CanChatUdpReceiver().run();
                return null;
            }

            @Override
            public void onSuccess(Void result) {

            }
        });

        if (checkWifi()){
            ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Void>() {
                @Override
                public Void doInBackground() throws Throwable {
                    while (true){
                        Thread.sleep(400);
                        new CanChatUdpSend(Udp.CHECKED_CODE, Udp.getIpToAll(),Udp.PORT_ALL).run();
                    }
                }

                @Override
                public void onSuccess(Void result) {

                }
            });
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
        if (canChatUdpReceiver != null){
            canChatUdpReceiver.interrupt();
        }
    }

    private void createNotificationChannel() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent hangIntent = new Intent(this, MainActivity.class);

        PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 1001, hangIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String CHANNEL_ID = AppUtils.getAppPackageName();//应用频道Id唯一值， 长度若太长可能会被截断，
//        String CHANNEL_NAME = AppUtils.getAppPackageName();//最长40个字符，太长会被截断
//        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
//                CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
//        manager.createNotificationChannel(notificationChannel);


        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("后台运行")
                .setSmallIcon(R.mipmap.logo)
                .setContentIntent(hangPendingIntent)
                .setAutoCancel(true)
                .build();



        startForeground(1,notification);
    }

}