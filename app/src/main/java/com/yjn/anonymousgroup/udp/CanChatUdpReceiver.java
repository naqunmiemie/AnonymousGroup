package com.yjn.anonymousgroup.udp;

import android.content.Context;

import com.tcl.common.util.L;
import com.yjn.anonymousgroup.model.Message;
import com.yjn.anonymousgroup.repository.MessageRepository;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class CanChatUdpReceiver extends Thread {

    private int port;
    //停止标志
    private boolean flag = true;

    private DatagramSocket datagramSocket = null;

    private Context context;

    private MessageRepository messageRepository;

    public CanChatUdpReceiver(Context context, int port, MessageRepository messageRepository) {
        this.context = context;
        this.port = port;
        this.messageRepository = messageRepository;
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

                Message message = new Message();
                message.setMessage(data);
                message.setTimestamp(System.currentTimeMillis());
                messageRepository.insertMessage(message);

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

