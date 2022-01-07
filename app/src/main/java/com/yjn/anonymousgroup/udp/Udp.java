package com.yjn.anonymousgroup.udp;

import com.blankj.utilcode.util.NetworkUtils;

import java.util.HashSet;
import java.util.Set;

public class Udp {
    //定义单独端口
    public static final int PORT_OWN = 52000;
    //定义群聊端口
    public static final int PORT_ALL = 51000;

    public static final String CHECKED_CODE = "check_code_123456789-";

    public static Set<String> peopleSet = new HashSet<>();

    //获取255ip
    public static String getIpToAll(){
        try {
            String ip = getIp();
            if(ip == null)
                return  null;
            return getIp().substring(0, 10) + "255";
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //获取本地ip
    public static String getIp(){
        if (NetworkUtils.isWifiConnected()){
            return NetworkUtils.getIpAddressByWifi();
        }else {
            return NetworkUtils.getIPAddress(true);
        }

    }
}
