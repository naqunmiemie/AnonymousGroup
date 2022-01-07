package com.yjn.anonymousgroup.util;

import com.blankj.utilcode.util.NetworkUtils;
import com.hjq.toast.ToastUtils;

public class Net {
    public static boolean checkWifi() {
        if (!NetworkUtils.isWifiConnected()){
            ToastUtils.show("未连接WIFI");
            return false;
        }
        return true;
    }
}
