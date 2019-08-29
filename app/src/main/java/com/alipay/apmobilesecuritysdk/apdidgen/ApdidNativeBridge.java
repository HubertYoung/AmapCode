package com.alipay.apmobilesecuritysdk.apdidgen;

import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApdidNativeBridge {
    public static native String getDeviceInfo(String str, int i);

    public static native String getFileStat(String str, int i);

    public static native String needUpload(String str, int i);

    public static Map<String, String> getDeviceInfos(List<String> list, int i) {
        HashMap hashMap = new HashMap();
        if (list != null) {
            for (String next : list) {
                String deviceInfo = getDeviceInfo(next, i);
                if (CommonUtils.isNotBlank(deviceInfo)) {
                    hashMap.put(next, deviceInfo);
                }
            }
        }
        return hashMap;
    }
}
