package com.yjn.anonymousgroup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CanChatUdpReceiver(this, Udp.PORT_ALL).start();
        new Thread(new CanChatUdpSend("huawei",Udp.getIpToAll(),Udp.PORT_ALL)).start();
//        new Thread(new CanChatUdpSend("test",Udp.getIpToAll(),Udp.PORT_ALL).run());
    }
}