package com.xiaomi.network;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import com.autonavi.minimap.basemap.traffic.TrafficTopic;
import com.xiaomi.channel.commonutils.network.c;
import com.xiaomi.channel.commonutils.network.d;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

public abstract class HttpUtils {

    public static class DefaultHttpGetProcessor extends HttpProcessor {
        public DefaultHttpGetProcessor() {
            super(1);
        }

        public String b(Context context, String str, List<c> list) {
            URL url;
            if (list == null) {
                url = new URL(str);
            } else {
                Builder buildUpon = Uri.parse(str).buildUpon();
                for (c next : list) {
                    buildUpon.appendQueryParameter(next.a(), next.b());
                }
                url = new URL(buildUpon.toString());
            }
            return d.a(context, url);
        }
    }

    static int a(int i, int i2) {
        return (((i2 + FavoritesPointFragment.REQUEST_EDIT_POINT) / 1448) * 132) + TrafficTopic.NOTIFY + i + i2;
    }

    static int a(int i, int i2, int i3) {
        return (((i2 + 200) / 1448) * 132) + 1011 + i2 + i + i3;
    }

    private static int a(HttpProcessor httpProcessor, String str, List<c> list, String str2) {
        if (httpProcessor.a() == 1) {
            return a(str.length(), a(str2));
        }
        if (httpProcessor.a() != 2) {
            return -1;
        }
        return a(str.length(), a(list), a(str2));
    }

    static int a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException unused) {
            return 0;
        }
    }

    static int a(List<c> list) {
        int i = 0;
        for (c next : list) {
            if (!TextUtils.isEmpty(next.a())) {
                i += next.a().length();
            }
            if (!TextUtils.isEmpty(next.b())) {
                i += next.b().length();
            }
        }
        return i * 2;
    }

    public static String a(Context context, String str, List<c> list) {
        return a(context, str, list, new DefaultHttpGetProcessor(), true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x00a0 A[SYNTHETIC, Splitter:B:50:0x00a0] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00b4 A[Catch:{ MalformedURLException -> 0x00bc }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r21, java.lang.String r22, java.util.List<com.xiaomi.channel.commonutils.network.c> r23, com.xiaomi.network.HttpProcessor r24, boolean r25) {
        /*
            r1 = r21
            r2 = r22
            r3 = r23
            r4 = r24
            boolean r5 = com.xiaomi.channel.commonutils.network.d.c(r21)
            if (r5 == 0) goto L_0x00c1
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ MalformedURLException -> 0x00bc }
            r5.<init>()     // Catch:{ MalformedURLException -> 0x00bc }
            if (r25 == 0) goto L_0x0025
            com.xiaomi.network.HostManager r7 = com.xiaomi.network.HostManager.getInstance()     // Catch:{ MalformedURLException -> 0x00bc }
            com.xiaomi.network.Fallback r7 = r7.getFallbacksByURL(r2)     // Catch:{ MalformedURLException -> 0x00bc }
            if (r7 == 0) goto L_0x0023
            java.util.ArrayList r5 = r7.a(r2)     // Catch:{ MalformedURLException -> 0x00bc }
        L_0x0023:
            r14 = r7
            goto L_0x0026
        L_0x0025:
            r14 = 0
        L_0x0026:
            boolean r7 = r5.contains(r2)     // Catch:{ MalformedURLException -> 0x00bc }
            if (r7 != 0) goto L_0x002f
            r5.add(r2)     // Catch:{ MalformedURLException -> 0x00bc }
        L_0x002f:
            java.util.Iterator r2 = r5.iterator()     // Catch:{ MalformedURLException -> 0x00bc }
            r5 = 0
        L_0x0034:
            boolean r7 = r2.hasNext()     // Catch:{ MalformedURLException -> 0x00bc }
            if (r7 == 0) goto L_0x00bb
            java.lang.Object r7 = r2.next()     // Catch:{ MalformedURLException -> 0x00bc }
            r15 = r7
            java.lang.String r15 = (java.lang.String) r15     // Catch:{ MalformedURLException -> 0x00bc }
            if (r3 == 0) goto L_0x004a
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ MalformedURLException -> 0x00bc }
            r7.<init>(r3)     // Catch:{ MalformedURLException -> 0x00bc }
            r13 = r7
            goto L_0x004b
        L_0x004a:
            r13 = 0
        L_0x004b:
            long r16 = java.lang.System.currentTimeMillis()     // Catch:{ MalformedURLException -> 0x00bc }
            boolean r7 = r4.a(r1, r15, r13)     // Catch:{ IOException -> 0x009a }
            if (r7 != 0) goto L_0x0056
            return r5
        L_0x0056:
            java.lang.String r11 = r4.b(r1, r15, r13)     // Catch:{ IOException -> 0x009a }
            boolean r5 = android.text.TextUtils.isEmpty(r11)     // Catch:{ IOException -> 0x0097 }
            if (r5 != 0) goto L_0x007b
            if (r14 == 0) goto L_0x0079
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0097 }
            r5 = 0
            long r9 = r7 - r16
            int r5 = a(r4, r15, r13, r11)     // Catch:{ IOException -> 0x0097 }
            long r7 = (long) r5
            r18 = r7
            r7 = r14
            r8 = r15
            r5 = r11
            r11 = r18
            r7.a(r8, r9, r11)     // Catch:{ IOException -> 0x009a }
            return r5
        L_0x0079:
            r5 = r11
            return r5
        L_0x007b:
            r5 = r11
            if (r14 == 0) goto L_0x0034
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x009a }
            r9 = 0
            long r9 = r7 - r16
            int r7 = a(r4, r15, r13, r5)     // Catch:{ IOException -> 0x009a }
            long r11 = (long) r7
            r18 = 0
            r7 = r14
            r8 = r15
            r6 = r13
            r13 = r18
            r7.a(r8, r9, r11, r13)     // Catch:{ IOException -> 0x0095 }
            goto L_0x0034
        L_0x0095:
            r0 = move-exception
            goto L_0x009c
        L_0x0097:
            r0 = move-exception
            r5 = r11
            goto L_0x009b
        L_0x009a:
            r0 = move-exception
        L_0x009b:
            r6 = r13
        L_0x009c:
            r13 = r5
            r5 = r0
            if (r14 == 0) goto L_0x00b4
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ MalformedURLException -> 0x00bc }
            r9 = 0
            long r9 = r7 - r16
            int r6 = a(r4, r15, r6, r13)     // Catch:{ MalformedURLException -> 0x00bc }
            long r11 = (long) r6     // Catch:{ MalformedURLException -> 0x00bc }
            r7 = r14
            r8 = r15
            r6 = r13
            r13 = r5
            r7.a(r8, r9, r11, r13)     // Catch:{ MalformedURLException -> 0x00bc }
            goto L_0x00b5
        L_0x00b4:
            r6 = r13
        L_0x00b5:
            r5.printStackTrace()     // Catch:{ MalformedURLException -> 0x00bc }
            r5 = r6
            goto L_0x0034
        L_0x00bb:
            return r5
        L_0x00bc:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
        L_0x00c1:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.network.HttpUtils.a(android.content.Context, java.lang.String, java.util.List, com.xiaomi.network.HttpProcessor, boolean):java.lang.String");
    }
}
