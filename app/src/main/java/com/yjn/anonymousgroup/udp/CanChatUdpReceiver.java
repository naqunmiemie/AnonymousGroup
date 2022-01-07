package com.yjn.anonymousgroup.udp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.tcl.common.util.L;
import com.yjn.anonymousgroup.App;
import com.yjn.anonymousgroup.model.Message;
import com.yjn.anonymousgroup.repository.MessageRepository;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashSet;
import java.util.Set;

public class CanChatUdpReceiver implements Runnable {

    //停止标志
    private boolean flag = true;

    private DatagramSocket datagramSocket = null;

    @Override
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
                if (Udp.CHECKED_CODE.equals(data)){
                    Udp.peopleSet.add(ip);
                }else {
                    Message message = new Message();
                    message.setMessage(data);
                    message.setTimestamp(System.currentTimeMillis());
                    MessageRepository.getInstance().insertMessage(message);
                }

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

