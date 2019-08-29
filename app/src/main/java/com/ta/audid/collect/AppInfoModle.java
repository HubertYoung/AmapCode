package com.ta.audid.collect;

import android.content.Context;
import android.os.Process;
import com.ta.audid.Constants;
import com.ta.audid.Variables;
import com.ta.audid.permission.PermissionUtils;
import com.ta.audid.utils.AppInfoUtils;
import com.ta.audid.utils.UtUtils;
import java.util.HashMap;
import java.util.Map;

public class AppInfoModle {
    private static final String A10_TIMESTAMP = "A10";
    private static final String A11_CHANNEL = "A11";
    private static final String A13_TAOBAO_NICKNAME = "A13";
    private static final String A14_TAOBAO_USERID = "A14";
    private static final String A15_SDKVERSION = "A15";
    private static final String A1_APP_KEY = "A1";
    private static final String A2_APP_VERSION = "A2";
    private static final String A3_TARGET_SDK_VERSION = "A3";
    private static final String A4_WRITE_EXTERNAL_STORAGE = "A4";
    private static final String A5_READ_PHONE_STATE = "A5";
    private static final String A6_ACCESS_WIFI_STATE = "A6";
    private static final String A7_CUR_PROCCESS_ID = "A7";
    private static final String A8_CUR_PROCCESS_NAME = "A8";
    private static final String A9_APP_NAME = "A9";

    static Map<String, String> getAppInfoModle(Context context) {
        HashMap hashMap = new HashMap();
        hashMap.put(A1_APP_KEY, Variables.getInstance().getAppkey());
        hashMap.put(A2_APP_VERSION, AppInfoUtils.getAppVersionName(context));
        StringBuilder sb = new StringBuilder();
        sb.append(AppInfoUtils.getTargetSdkVersion(context));
        hashMap.put(A3_TARGET_SDK_VERSION, sb.toString());
        hashMap.put(A4_WRITE_EXTERNAL_STORAGE, PermissionUtils.checkStoragePermissionGranted(context) ? "1" : "0");
        hashMap.put(A5_READ_PHONE_STATE, PermissionUtils.checkReadPhoneStatePermissionGranted(context) ? "1" : "0");
        hashMap.put(A6_ACCESS_WIFI_STATE, PermissionUtils.checkWifiStatePermissionGranted(context) ? "1" : "0");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Process.myPid());
        hashMap.put(A7_CUR_PROCCESS_ID, sb2.toString());
        hashMap.put(A8_CUR_PROCCESS_NAME, AppInfoUtils.getCurProcessName(context));
        hashMap.put(A9_APP_NAME, AppInfoUtils.getAppPackageName(context));
        StringBuilder sb3 = new StringBuilder();
        sb3.append(System.currentTimeMillis());
        hashMap.put(A10_TIMESTAMP, sb3.toString());
        hashMap.put(A11_CHANNEL, Variables.getInstance().getAppChannel());
        hashMap.put(A13_TAOBAO_NICKNAME, UtUtils.getUserNick());
        hashMap.put(A14_TAOBAO_USERID, UtUtils.getUserId());
        hashMap.put(A15_SDKVERSION, Constants.SDK_VERSION);
        return hashMap;
    }
}
