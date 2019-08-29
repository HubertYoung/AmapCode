package com.alipay.android.phone.mobilesdk.permission.guide.info;

import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import java.util.HashMap;
import java.util.Map;

/* compiled from: PermissionMapping */
public final class d {
    public static Map<String, String> a;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put("android.permission.ACCESS_COARSE_LOCATION", RPCDataItems.LBSINFO);
        a.put("android.permission.ACCESS_FINE_LOCATION", RPCDataItems.LBSINFO);
        a.put("android.permission.READ_SMS", "SMS");
        a.put("android.permission.SEND_SMS", "SMS");
        a.put("android.permission.RECEIVE_MMS", "SMS");
        a.put("android.permission.RECEIVE_SMS", "SMS");
        a.put("android.permission.RECEIVE_WAP_PUSH", "SMS");
    }
}
