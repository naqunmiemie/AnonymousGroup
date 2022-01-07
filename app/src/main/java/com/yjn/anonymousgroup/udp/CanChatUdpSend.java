package com.yjn.anonymousgroup.udp;

import com.tcl.common.util.L;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class CanChatUdpSend implements Runnable {
    //发送的数据文本
    private String data;

    private int port;

    private String ip;

    public CanChatUdpSend(String data,String ip,int port){
        this.data = data;
        this.port = port;
        this.ip = ip;
    }

    @Override
    public void run() {
        DatagramSocket datagramSocket = null;
        try{
            datagramSocket = new DatagramSocket();
//            L.e("send data:"+data);
            byte[] buf = data.getBytes();
            DatagramPacket dp = new DatagramPacket(buf,buf.length, InetAddress.getByName(ip),port);
            datagramSocket.send(dp);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(datagramSocket != null)
                    datagramSocket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

