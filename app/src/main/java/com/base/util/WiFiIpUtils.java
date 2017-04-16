package com.base.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by chenbaolin on 2017/4/16.
 */

public class WiFiIpUtils {
    // 获取wifi ip
    public static String getWIFIiP(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiinfo = wifiManager.getConnectionInfo();
        String ip = intToIp(wifiinfo.getIpAddress());
        return ip;
    }

    private static String intToIp(int i) {
        return ((i & 0xff) + "." + (i >> 8 & 0xff) + "." + (i >> 16 & 0xff) + "." + (i >> 24 & 0xff));
    }
}
