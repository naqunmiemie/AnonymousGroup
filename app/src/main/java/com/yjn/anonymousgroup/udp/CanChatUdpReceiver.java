package com.yjn.anonymousgroup.udp;

import android.content.Context;

import com.tcl.common.util.L;
import com.yjn.anonymousgroup.App;
import com.yjn.anonymousgroup.model.Message;
import com.yjn.anonymousgroup.repository.MessageRepository;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class CanChatUdpReceiver extends Thread {

    private int port;
    //停止标志
    private boolean flag = true;

    private DatagramSocket datagramSocket = null;

    public void run(){
        try{
            if(datagramSocket == null)
                datagramSocket = new DatagramSocket(Udp.PORT_ALL);
            while (flag){
                byte[] buf = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(buf,buf.length);
                datagramSocket.receive(datagramPacket);
                String ip = datagramPacket.getAddress().getHostAddress();
                String data = new String(datagramPacket.getData(),0,datagramPacket.getLength());

                Message message = new Message();
                message.setMessage(data);
                message.setTimestamp(System.currentTimeMillis());
                MessageRepository.getInstance().insertMessage(message);

                L.e("receiver data:"+data);
            }
        }catch(Exception e){
            e.printStackTrace();
            L.e("CanChatUdpReceiver error");
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

