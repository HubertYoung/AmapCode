package com.ut.device;

import android.content.Context;
import com.ta.audid.Variables;
import com.ta.audid.device.AppUtdid;

public class UTDevice {
    @Deprecated
    public static String getAid(String str, String str2, Context context) {
        return "";
    }

    @Deprecated
    public static void getAidAsync(String str, String str2, Context context, AidCallback aidCallback) {
    }

    public static String getUtdid(Context context) {
        return com.ta.utdid2.device.UTDevice.getUtdid(context);
    }

    @Deprecated
    public static String getUtdidForUpdate(Context context) {
        return com.ta.utdid2.device.UTDevice.getUtdidForUpdate(context);
    }

    public static void setAppKey(String str) {
        Variables.getInstance().setAppkey(str);
    }

    public static void setAppChannel(String str) {
        Variables.getInstance().setAppChannel(str);
    }

    @Deprecated
    public static void setDebug(boolean z) {
        Variables.getInstance().setDebug(z);
    }

    @Deprecated
    public static void setOldMode(Context context, boolean z) {
        if (context != null) {
            Variables.getInstance().initContext(context);
            Variables.getInstance().setOldMode(z);
        }
    }

    public static void setCollectDelayTime(long j) {
        AppUtdid.setCollectDelayTime(j);
    }
}
