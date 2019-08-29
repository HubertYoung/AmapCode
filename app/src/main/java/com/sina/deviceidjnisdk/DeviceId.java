package com.sina.deviceidjnisdk;

import android.content.Context;

public class DeviceId implements IDeviceId {
    private Context mContext;

    private native String getDeviceIdNative(Context context, String str, String str2, String str3);

    static {
        System.loadLibrary("weibosdkcore");
    }

    protected DeviceId(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public String getDeviceId() {
        return getDeviceIdNative(this.mContext, DeviceInfo.getImei(this.mContext), DeviceInfo.getImsi(this.mContext), DeviceInfo.getMacAddress(this.mContext));
    }

    private String genCheckId(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        return sb.toString();
    }
}
