package com.alipay.android.phone.mobilesdk.permission.utils;

import android.content.Context;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.taobao.accs.utl.UtilityImpl;

/* compiled from: NetType */
public final class f {
    public static String a(Context context) {
        switch (NetworkUtils.getNetworkType(context)) {
            case 1:
                return UtilityImpl.NET_TYPE_2G;
            case 2:
            case 14:
            case 15:
                return UtilityImpl.NET_TYPE_3G;
            case 3:
                return "wifi";
            case 4:
            case 13:
                return UtilityImpl.NET_TYPE_4G;
            default:
                return UtilityImpl.NET_TYPE_2G;
        }
    }
}
