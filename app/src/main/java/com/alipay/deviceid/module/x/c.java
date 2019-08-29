package com.alipay.deviceid.module.x;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.rpc.gen.DeviceData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class c {
    private static String a;
    private static List<String> b;

    public static Map<String, String> a(Context context) {
        HashMap hashMap = new HashMap();
        hashMap.put("AA1", context.getPackageName());
        j.a();
        hashMap.put("AA2", j.a(context));
        hashMap.put("AA3", DeviceData.DEFAULT_AA3);
        hashMap.put("AA4", "6.1.0.20180401");
        return hashMap;
    }

    public static Map<String, String> a(Map<String, String> map) {
        String a2 = e.a(map, "appchannel", "");
        HashMap hashMap = new HashMap();
        hashMap.put("AA6", a2);
        return hashMap;
    }
}
