package com.alipay.apmobilesecuritysdk.model;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.storage.SettingsStorage;
import com.alipay.apmobilesecuritysdk.type.DevType;
import com.alipay.apmobilesecuritysdk.type.DevTypeString;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.util.HashMap;
import java.util.Map;

public class CustomInfoModel {
    public static Map<String, DevType<?>> a(Context context) {
        HashMap hashMap = new HashMap();
        hashMap.put("AC4", new DevTypeString(SettingsStorage.h(context)));
        return hashMap;
    }

    public static Map<String, DevType<?>> a(Map<String, Object> map) {
        HashMap hashMap = new HashMap();
        String stringFromMap = CommonUtils.getStringFromMap(map, "tid", "");
        String stringFromMap2 = CommonUtils.getStringFromMap(map, "userId", "");
        String stringFromMap3 = CommonUtils.getStringFromMap(map, "appName", "");
        String stringFromMap4 = CommonUtils.getStringFromMap(map, "appKeyClient", "");
        hashMap.put("AC1", new DevTypeString(stringFromMap));
        hashMap.put("AC5", new DevTypeString(stringFromMap2));
        hashMap.put("AC8", new DevTypeString(stringFromMap3));
        hashMap.put("AC9", new DevTypeString(stringFromMap4));
        return hashMap;
    }
}
