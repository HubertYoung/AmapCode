package com.alipay.apmobilesecuritysdk.model;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.type.DevType;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class DeviceInfoManager {
    private static DeviceInfoManager a;
    private Map<String, DevType<?>> b = null;

    public static DeviceInfoManager a() {
        if (a == null) {
            synchronized (DeviceInfoManager.class) {
                try {
                    if (a == null) {
                        a = new DeviceInfoManager();
                    }
                }
            }
        }
        return a;
    }

    public final String a(Context context) {
        b(context);
        ArrayList arrayList = new ArrayList(this.b.keySet());
        Collections.sort(arrayList);
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            for (int i = 0; i < arrayList.size(); i++) {
                String str = (String) arrayList.get(i);
                DevType devType = this.b.get(str);
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("=");
                instance.update(sb.toString().getBytes());
                if (devType != null) {
                    instance.update(devType.a());
                }
            }
            byte[] digest = instance.digest();
            StringBuilder sb2 = new StringBuilder();
            for (byte valueOf : digest) {
                sb2.append(String.format("%02x", new Object[]{Byte.valueOf(valueOf)}));
            }
            return sb2.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public final Map<String, DevType<?>> a(Context context, Map<String, Object> map) {
        if (this.b == null) {
            b(context);
        }
        this.b.putAll(CustomInfoModel.a(map));
        this.b.putAll(EnvironmentInfoModel.a(context, map));
        this.b.putAll(DeviceInfoModel.b(context));
        this.b.putAll(ApplicationInfoModel.a(context, map));
        return this.b;
    }

    private void b(Context context) {
        this.b = new TreeMap();
        this.b.putAll(CustomInfoModel.a(context));
        this.b.putAll(EnvironmentInfoModel.a(context));
        this.b.putAll(DeviceInfoModel.a(context));
        this.b.putAll(ApplicationInfoModel.a(context));
    }
}
