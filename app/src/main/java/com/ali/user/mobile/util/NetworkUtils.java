package com.ali.user.mobile.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.ali.user.mobile.log.AliUserLogUtil;

public class NetworkUtils {
    public static int a(Context context) {
        NetworkInfo b = b(context);
        if (b == null || !b.isConnected()) {
            return 0;
        }
        int type = b.getType();
        if (type == 1) {
            return 3;
        }
        if (type != 0 || b == null) {
            return 0;
        }
        return a(b.getSubtype());
    }

    private static NetworkInfo b(Context context) {
        try {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable th) {
            AliUserLogUtil.a("NetworkUtils", "getActiveNetworkInfo exception ", th);
            return null;
        }
    }

    private static int a(int i) {
        try {
            Integer num = (Integer) TelephonyManager.class.getMethod("getNetworkClass", new Class[]{Integer.TYPE}).invoke(TelephonyManager.class, new Object[]{Integer.valueOf(i)});
            if (num.intValue() == 1) {
                return 1;
            }
            if (num.intValue() == 2) {
                return 2;
            }
            return num.intValue() == 3 ? 4 : 0;
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("TelephonyManager#getNetworkClass exception: ");
            sb.append(th.toString());
            AliUserLogUtil.d("NetworkUtils", sb.toString());
            if (i != 18) {
                switch (i) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return 1;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        break;
                    case 13:
                        return 4;
                    default:
                        return 0;
                }
            }
            return 2;
        }
    }
}
