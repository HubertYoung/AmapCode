package com.alipay.apmobilesecuritysdk.model;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.commonbiz.AppLaunchTimeUtil;
import com.alipay.apmobilesecuritysdk.commonbiz.ApplistUtil;
import com.alipay.apmobilesecuritysdk.type.DevType;
import com.alipay.apmobilesecuritysdk.type.DevTypeByteArray;
import com.alipay.apmobilesecuritysdk.type.DevTypeString;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.deviceinfo.AppInfo;
import java.util.HashMap;
import java.util.Map;

public class ApplicationInfoModel {
    public static Map<String, DevType<?>> a(Context context) {
        HashMap hashMap = new HashMap();
        hashMap.put("AA1", new DevTypeString(context.getPackageName()));
        hashMap.put("AA2", new DevTypeString(AppInfo.getInstance().getAppVersion(context)));
        hashMap.put("AA3", new DevTypeString("security-sdk-VR"));
        if (CommonUtils.isAlipayWallet(context)) {
            hashMap.put("AA4", new DevTypeString("P3.6.5-20170830"));
        } else {
            hashMap.put("AA4", new DevTypeString("3.6.5-20170830"));
        }
        hashMap.put("AA5", new DevTypeByteArray(ApplistUtil.a(context)));
        return hashMap;
    }

    public static Map<String, DevType<?>> a(Context context, Map<String, Object> map) {
        String stringFromMap = CommonUtils.getStringFromMap(map, "appchannel", "");
        HashMap hashMap = new HashMap();
        hashMap.put("AA6", new DevTypeString(stringFromMap));
        hashMap.put("AA7", new DevTypeByteArray(CommonUtils.gzipCompress(ApplistUtil.c(context))));
        if (CommonUtils.isAlipayWallet(context)) {
            hashMap.put("AA8", new DevTypeByteArray(CommonUtils.gzipCompress(AppLaunchTimeUtil.a(context))));
        }
        return hashMap;
    }
}
