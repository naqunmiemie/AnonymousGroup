package com.yjn.anonymousgroup;

import android.content.Context;
import android.util.Log;

import com.hjq.toast.ToastUtils;
import com.tcl.common.util.L;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class CanChatUdpReceiver extends Thread {

    private int port;
    //停止标志
    private boolean flag = true;

    private DatagramSocket datagramSocket = null;

    private Context context;

    public CanChatUdpReceiver(Context context,int port) {
        this.context = context;
        this.port = port;
    }

    public void run(){
        try{
            if(datagramSocket == null)
                datagramSocket = new DatagramSocket(port);
            while (flag){
                byte[] buf = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(buf,buf.length);
                datagramSocket.receive(datagramPacket);
                String ip = datagramPacket.getAddress().getHostAddress();
                String data = new String(datagramPacket.getData(),0,datagramPacket.getLength());
                L.e("receiver data:"+data);
            }
        }catch(Exception e){
            e.printStackTrace();
            L.e("服务器挂了");
        }finally {
            try{
                if(datagramSocket !=null)
                    datagramSocket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

