package com.alipay.apmobilesecuritysdk.model;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.constant.Constant;
import com.alipay.apmobilesecuritysdk.proxydetect.WebRTCClient;
import com.alipay.apmobilesecuritysdk.type.DevType;
import com.alipay.apmobilesecuritysdk.type.DevTypeBoolean;
import com.alipay.apmobilesecuritysdk.type.DevTypeInt;
import com.alipay.apmobilesecuritysdk.type.DevTypeString;
import com.alipay.mobile.security.senative.APSE;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.deviceinfo.DeviceInfo;
import com.alipay.security.mobile.module.deviceinfo.EnvironmentInfo;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentInfoModel {
    public static Map<String, DevType<?>> a(Context context) {
        EnvironmentInfo instance = EnvironmentInfo.getInstance();
        HashMap hashMap = new HashMap();
        hashMap.put("AE1", new DevTypeString(instance.getOSName()));
        hashMap.put("AE2", new DevTypeBoolean(Boolean.valueOf(instance.isRooted())));
        hashMap.put("AE3", new DevTypeBoolean(Boolean.valueOf(instance.isEmulator(context))));
        hashMap.put("AE4", new DevTypeString(instance.getProductBoard()));
        hashMap.put("AE5", new DevTypeString(instance.getProductBrand()));
        hashMap.put("AE6", new DevTypeString(instance.getProductDevice()));
        hashMap.put("AE7", new DevTypeString(instance.getBuildDisplayId()));
        hashMap.put("AE8", new DevTypeString(instance.getBuildVersionIncremental()));
        hashMap.put("AE9", new DevTypeString(instance.getProductManufacturer()));
        hashMap.put("AE10", new DevTypeString(instance.getProductModel()));
        hashMap.put("AE11", new DevTypeString(instance.getProductName()));
        hashMap.put("AE12", new DevTypeString(instance.getBuildVersionRelease()));
        hashMap.put("AE13", new DevTypeString(instance.getBuildVersionSDK()));
        hashMap.put("AE14", new DevTypeString(instance.getBuildTags()));
        hashMap.put("AE15", new DevTypeString(instance.getKernelQemu()));
        try {
            hashMap.put("AE20", new DevTypeBoolean(Boolean.valueOf(APSE.getInstance(context).isX86Machine())));
        } catch (Throwable unused) {
        }
        return hashMap;
    }

    public static Map<String, DevType<?>> a(Context context, Map<String, Object> map) {
        DeviceInfo instance = DeviceInfo.getInstance();
        HashMap hashMap = new HashMap();
        int integerFromMap = CommonUtils.getIntegerFromMap(map, "why_update", Constant.c.intValue());
        hashMap.put("AE16", new DevTypeString(WebRTCClient.a(context).a()));
        hashMap.put("AE18", new DevTypeInt(Integer.valueOf(integerFromMap)));
        if (CommonUtils.isAlipayWallet(context)) {
            hashMap.put("AE19", new DevTypeString(instance.getSecGuardWuaForDeviceID(context)));
        }
        hashMap.put("AE21", new DevTypeString(instance.getCpuName()));
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis() / 1000);
        hashMap.put("AE22", new DevTypeString(sb.toString()));
        return hashMap;
    }
}
