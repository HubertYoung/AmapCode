package com.amap.location.common.d;

import android.location.Location;
import android.text.TextUtils;
import com.amap.location.common.model.AmapLoc;

/* compiled from: CommonLogUtils */
public class b {
    public static String a(Location location) {
        boolean z;
        if (location == null) {
            return "null";
        }
        String str = "unknow";
        int i = 0;
        if ("network".equals(location.getProvider())) {
            try {
                int i2 = location.getExtras().getInt("locType", 0);
                try {
                    z = location.getExtras().getBoolean("isFilter", false);
                    try {
                        str = location.getExtras().getString("serverTraceId", str);
                    } catch (Throwable unused) {
                    }
                    i = i2;
                } catch (Throwable unused2) {
                    i = i2;
                }
            } catch (Throwable unused3) {
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Location[");
            sb.append(location.getProvider());
            sb.append(",");
            sb.append(location.getLatitude());
            sb.append(",");
            sb.append(location.getLongitude());
            sb.append(",");
            sb.append(location.getAccuracy());
            sb.append(",");
            sb.append(location.getBearing());
            sb.append(",");
            sb.append(location.getSpeed());
            sb.append(",");
            sb.append(location.getTime());
            sb.append(",");
            sb.append(i);
            sb.append(",");
            sb.append(z);
            sb.append(",");
            sb.append(str);
            sb.append(']');
            return sb.toString();
        }
        z = false;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Location[");
        sb2.append(location.getProvider());
        sb2.append(",");
        sb2.append(location.getLatitude());
        sb2.append(",");
        sb2.append(location.getLongitude());
        sb2.append(",");
        sb2.append(location.getAccuracy());
        sb2.append(",");
        sb2.append(location.getBearing());
        sb2.append(",");
        sb2.append(location.getSpeed());
        sb2.append(",");
        sb2.append(location.getTime());
        sb2.append(",");
        sb2.append(i);
        sb2.append(",");
        sb2.append(z);
        sb2.append(",");
        sb2.append(str);
        sb2.append(']');
        return sb2.toString();
    }

    public static String a(AmapLoc amapLoc) {
        if (amapLoc == null) {
            return "null";
        }
        String serverTraceId = amapLoc.getServerTraceId();
        if (TextUtils.isEmpty(serverTraceId)) {
            serverTraceId = "unknow";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("AmapLoc[");
        sb.append(amapLoc.getLat());
        sb.append(",");
        sb.append(amapLoc.getLon());
        sb.append(",");
        sb.append(amapLoc.getTime());
        sb.append(",");
        sb.append(amapLoc.getPoiid());
        sb.append(",");
        sb.append(amapLoc.getFloor());
        sb.append(",");
        sb.append(amapLoc.getRetype());
        sb.append(",");
        sb.append(amapLoc.getIsLast());
        sb.append(",");
        sb.append(AmapLoc.getLocType(amapLoc));
        sb.append(",");
        sb.append(serverTraceId);
        sb.append(']');
        return sb.toString();
    }
}
