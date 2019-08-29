package com.alipay.mobile.security.bio.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import com.taobao.accs.utl.UtilityImpl;
import java.io.BufferedReader;
import java.io.FileReader;

public class NetworkUtil {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
        if (allNetworkInfo == null) {
            return false;
        }
        for (NetworkInfo state : allNetworkInfo) {
            if (state.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static String getNetworkType(Context context) {
        Exception e;
        String str;
        if (context != null) {
            try {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
                    if ("wifi".equals(activeNetworkInfo.getTypeName().toLowerCase())) {
                        return "wifi";
                    }
                    str = UtilityImpl.NET_TYPE_2G;
                    try {
                        switch (activeNetworkInfo.getSubtype()) {
                            case 1:
                            case 2:
                            case 4:
                            case 11:
                                return str;
                            case 3:
                                return UtilityImpl.NET_TYPE_3G;
                            case 5:
                                return UtilityImpl.NET_TYPE_3G;
                            case 6:
                                return UtilityImpl.NET_TYPE_3G;
                            case 7:
                                return UtilityImpl.NET_TYPE_3G;
                            case 8:
                                return UtilityImpl.NET_TYPE_3G;
                            case 9:
                                return UtilityImpl.NET_TYPE_3G;
                            case 10:
                                return UtilityImpl.NET_TYPE_3G;
                            case 12:
                                return UtilityImpl.NET_TYPE_3G;
                            case 13:
                                return UtilityImpl.NET_TYPE_4G;
                            case 14:
                                return UtilityImpl.NET_TYPE_3G;
                            case 15:
                                return UtilityImpl.NET_TYPE_3G;
                            default:
                                return str;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        BioLog.e("NetworkTypeUtil" + e.toString());
                        return str;
                    }
                }
            } catch (Exception e3) {
                Exception exc = e3;
                str = "";
                e = exc;
                BioLog.e("NetworkTypeUtil" + e.toString());
                return str;
            }
        }
        r0 = "";
        return "";
    }

    public static String getMacAddress() {
        try {
            StringBuffer stringBuffer = new StringBuffer(1000);
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/sys/class/net/eth0/address"));
            char[] cArr = new char[1024];
            while (true) {
                int read = bufferedReader.read(cArr);
                if (read != -1) {
                    stringBuffer.append(String.valueOf(cArr, 0, read));
                } else {
                    bufferedReader.close();
                    return stringBuffer.toString().substring(0, 17);
                }
            }
        } catch (Exception e) {
            return "";
        }
    }
}
