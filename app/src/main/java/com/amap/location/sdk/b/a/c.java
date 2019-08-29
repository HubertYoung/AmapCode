package com.amap.location.sdk.b.a;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.amap.location.g.b.a;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.Iterator;

/* compiled from: UpTunnelUtil */
public class c {
    public static byte[] a(Context context) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        String str;
        try {
            if (!a.a(context).a((String) WidgetType.GPS)) {
                return null;
            }
            GpsStatus a = a.a(context).a((GpsStatus) null);
            if (a == null) {
                return null;
            }
            Iterator<GpsSatellite> it = a.getSatellites().iterator();
            boolean hasNext = it.hasNext();
            if (hasNext) {
                sb3 = new StringBuilder("{\"snrF\":[");
                sb2 = new StringBuilder("\"prnF\":[");
                sb = new StringBuilder("\"fixB\":[");
            } else {
                sb3 = null;
                sb2 = null;
                sb = null;
            }
            while (it.hasNext()) {
                GpsSatellite next = it.next();
                sb3.append(next.getSnr());
                sb3.append(",");
                sb2.append(next.getPrn());
                sb2.append(",");
                sb.append(next.usedInFix() ? 1 : 0);
                sb.append(",");
            }
            if (!hasNext) {
                str = "{\"snrF\":[],\"prnF\":[],\"fixB\":[]}";
            } else {
                StringBuilder delete = sb3.delete(sb3.length() - 1, sb3.length());
                delete.append("],");
                StringBuilder delete2 = sb2.delete(sb2.length() - 1, sb2.length());
                delete2.append("]");
                delete.append(delete2);
                delete.append(",");
                StringBuilder delete3 = sb.delete(sb.length() - 1, sb.length());
                delete3.append("]");
                delete.append(delete3);
                delete.append(h.d);
                str = delete.toString();
                sb3.delete(0, sb3.length());
                sb2.delete(0, sb2.length());
                sb.delete(0, sb.length());
            }
            if (!TextUtils.isEmpty(str)) {
                com.amap.location.common.d.a.b("gpserror", "gps timeout info :".concat(String.valueOf(str)));
            }
            return str.getBytes();
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(9:0|1|2|3|4|(2:6|7)|8|9|10) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x003c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(android.location.Location r4, int r5) {
        /*
            r0 = 100
            com.google.flatbuffers.FlatBufferBuilder r1 = new com.google.flatbuffers.FlatBufferBuilder     // Catch:{ Exception -> 0x004b }
            r1.<init>()     // Catch:{ Exception -> 0x004b }
            com.amap.location.sdk.b.a.a.a.a(r1)     // Catch:{ Exception -> 0x004b }
            double r2 = r4.getLongitude()     // Catch:{ Exception -> 0x004b }
            com.amap.location.sdk.b.a.a.a.b(r1, r2)     // Catch:{ Exception -> 0x004b }
            double r2 = r4.getLatitude()     // Catch:{ Exception -> 0x004b }
            com.amap.location.sdk.b.a.a.a.a(r1, r2)     // Catch:{ Exception -> 0x004b }
            float r2 = r4.getAccuracy()     // Catch:{ Exception -> 0x004b }
            com.amap.location.sdk.b.a.a.a.a(r1, r2)     // Catch:{ Exception -> 0x004b }
            short r5 = (short) r5     // Catch:{ Exception -> 0x004b }
            com.amap.location.sdk.b.a.a.a.a(r1, r5)     // Catch:{ Exception -> 0x004b }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x004b }
            com.amap.location.sdk.b.a.a.a.a(r1, r2)     // Catch:{ Exception -> 0x004b }
            android.os.Bundle r4 = r4.getExtras()     // Catch:{ Exception -> 0x003c }
            if (r4 == 0) goto L_0x003c
            java.lang.String r5 = "retype"
            java.lang.String r4 = r4.getString(r5)     // Catch:{ Exception -> 0x003c }
            int r4 = java.lang.Integer.parseInt(r4)     // Catch:{ Exception -> 0x003c }
            r0 = r4
        L_0x003c:
            com.amap.location.sdk.b.a.a.a.a(r1, r0)     // Catch:{ Exception -> 0x004b }
            int r4 = com.amap.location.sdk.b.a.a.a.b(r1)     // Catch:{ Exception -> 0x004b }
            r1.finish(r4)     // Catch:{ Exception -> 0x004b }
            byte[] r4 = r1.sizedByteArray()     // Catch:{ Exception -> 0x004b }
            return r4
        L_0x004b:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.b.a.c.a(android.location.Location, int):byte[]");
    }
}
