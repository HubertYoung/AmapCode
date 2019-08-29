package com.xiaomi.push.mpcd.job;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.xiaomi.channel.commonutils.android.d;
import com.xiaomi.channel.commonutils.android.f;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Iterator;

public class g extends f {
    private boolean a;
    private boolean b;
    private boolean e;
    private boolean f;

    public g(Context context, int i, boolean z, boolean z2, boolean z3, boolean z4) {
        super(context, i);
        this.a = z;
        this.b = z2;
        if (!f.e()) {
            this.b = false;
        }
        this.e = z3;
        this.f = z4;
    }

    private String a(Context context) {
        if (!this.f) {
            return CameraParams.FLASH_MODE_OFF;
        }
        String str = "";
        try {
            if (f.e()) {
                Iterator<String> it = d.g(context).iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    if (!TextUtils.isEmpty(str)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append(";");
                        str = sb.toString();
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(com.xiaomi.channel.commonutils.string.d.a(next));
                    sb2.append(",");
                    sb2.append(com.xiaomi.channel.commonutils.string.d.b(next));
                    str = sb2.toString();
                }
            }
            return str;
        } catch (Throwable unused) {
            return "";
        }
    }

    private String f() {
        if (!this.a) {
            return CameraParams.FLASH_MODE_OFF;
        }
        try {
            String g = g();
            if (TextUtils.isEmpty(g)) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(com.xiaomi.channel.commonutils.string.d.a(g));
            sb.append(",");
            sb.append(com.xiaomi.channel.commonutils.string.d.b(g));
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    @TargetApi(9)
    private String g() {
        if (!f.e()) {
            return "";
        }
        String str = "";
        if (VERSION.SDK_INT < 23) {
            str = ((WifiManager) this.d.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        }
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        if (VERSION.SDK_INT < 9) {
            return "";
        }
        try {
            for (T t : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if ("wlan0".equalsIgnoreCase(t.getName())) {
                    byte[] hardwareAddress = t.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte valueOf : hardwareAddress) {
                        sb.append(String.format("%02x:", new Object[]{Byte.valueOf(valueOf)}));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString().toLowerCase();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return "";
    }

    private String h() {
        if (!this.b) {
            return CameraParams.FLASH_MODE_OFF;
        }
        try {
            String subscriberId = ((TelephonyManager) this.d.getSystemService("phone")).getSubscriberId();
            if (TextUtils.isEmpty(subscriberId)) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(com.xiaomi.channel.commonutils.string.d.a(subscriberId));
            sb.append(",");
            sb.append(com.xiaomi.channel.commonutils.string.d.b(subscriberId));
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    private String i() {
        if (!this.e) {
            return CameraParams.FLASH_MODE_OFF;
        }
        try {
            String simSerialNumber = ((TelephonyManager) this.d.getSystemService("phone")).getSimSerialNumber();
            if (TextUtils.isEmpty(simSerialNumber)) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(com.xiaomi.channel.commonutils.string.d.a(simSerialNumber));
            sb.append(",");
            sb.append(com.xiaomi.channel.commonutils.string.d.b(simSerialNumber));
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public int a() {
        return 13;
    }

    public String b() {
        StringBuilder sb = new StringBuilder();
        sb.append(f());
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(h());
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(i());
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(a(this.d));
        return sb.toString();
    }

    public com.xiaomi.xmpush.thrift.d d() {
        return com.xiaomi.xmpush.thrift.d.DeviceBaseInfo;
    }
}
