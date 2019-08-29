package com.huawei.android.pushselfshow.richpush.html.a;

import android.content.Context;
import android.content.Intent;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue;
import com.huawei.android.pushselfshow.richpush.html.api.d.a;
import org.json.JSONObject;

public class k implements h {
    private NativeToJsMessageQueue a;
    private String b;
    private Context c;
    private String d = null;

    public k(Context context) {
        c.e("PushSelfShowLog", "init VideoPlayer");
        this.c = context;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x007f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(org.json.JSONObject r7) {
        /*
            r6 = this;
            com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue r0 = r6.a
            if (r0 != 0) goto L_0x000c
            java.lang.String r7 = "PushSelfShowLog"
            java.lang.String r0 = "jsMessageQueue is null while run into Video Player exec"
            com.huawei.android.pushagent.a.a.c.a(r7, r0)
            return
        L_0x000c:
            r0 = 0
            if (r7 == 0) goto L_0x00e6
            java.lang.String r1 = "url"
            boolean r1 = r7.has(r1)
            if (r1 == 0) goto L_0x00e6
            java.lang.String r1 = "url"
            java.lang.String r1 = r7.getString(r1)     // Catch:{ Exception | JSONException -> 0x00de }
            com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue r2 = r6.a     // Catch:{ Exception | JSONException -> 0x00de }
            java.lang.String r2 = r2.a()     // Catch:{ Exception | JSONException -> 0x00de }
            java.lang.String r2 = com.huawei.android.pushselfshow.richpush.html.api.b.a(r2, r1)     // Catch:{ Exception | JSONException -> 0x00de }
            if (r2 == 0) goto L_0x00bc
            int r3 = r2.length()     // Catch:{ Exception | JSONException -> 0x00de }
            if (r3 <= 0) goto L_0x00bc
            r6.d = r2     // Catch:{ Exception | JSONException -> 0x00de }
            java.lang.String r1 = "video/*"
            java.lang.String r2 = "mime-type"
            boolean r2 = r7.has(r2)     // Catch:{ Exception | JSONException -> 0x00de }
            if (r2 == 0) goto L_0x0067
            java.lang.String r2 = "mime-type"
            java.lang.String r2 = r7.getString(r2)     // Catch:{ JSONException -> 0x0062, Exception -> 0x005a }
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.String r4 = "the custom mimetype is "
            java.lang.String r5 = java.lang.String.valueOf(r2)     // Catch:{ JSONException -> 0x0062, Exception -> 0x005a }
            java.lang.String r4 = r4.concat(r5)     // Catch:{ JSONException -> 0x0062, Exception -> 0x005a }
            com.huawei.android.pushagent.a.a.c.e(r3, r4)     // Catch:{ JSONException -> 0x0062, Exception -> 0x005a }
            java.lang.String r3 = "video/"
            boolean r3 = r2.startsWith(r3)     // Catch:{ JSONException -> 0x0062, Exception -> 0x005a }
            if (r3 == 0) goto L_0x0067
            r1 = r2
            goto L_0x0067
        L_0x005a:
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = "get mime-type error"
        L_0x005e:
            com.huawei.android.pushagent.a.a.c.e(r2, r3)     // Catch:{ Exception | JSONException -> 0x00de }
            goto L_0x0067
        L_0x0062:
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = "get mime-type error"
            goto L_0x005e
        L_0x0067:
            android.content.Intent r2 = new android.content.Intent     // Catch:{ Exception | JSONException -> 0x00de }
            java.lang.String r3 = "android.intent.action.VIEW"
            r2.<init>(r3)     // Catch:{ Exception | JSONException -> 0x00de }
            java.lang.String r3 = r6.d     // Catch:{ Exception | JSONException -> 0x00de }
            android.net.Uri r3 = android.net.Uri.parse(r3)     // Catch:{ Exception | JSONException -> 0x00de }
            r2.setDataAndType(r3, r1)     // Catch:{ Exception | JSONException -> 0x00de }
            java.lang.String r1 = "package-name"
            boolean r1 = r7.has(r1)     // Catch:{ Exception | JSONException -> 0x00de }
            if (r1 == 0) goto L_0x00ab
            java.lang.String r1 = "package-name"
            java.lang.String r7 = r7.getString(r1)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r3 = "the custom packageName is "
            java.lang.String r4 = java.lang.String.valueOf(r7)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ JSONException -> 0x00a4 }
            com.huawei.android.pushagent.a.a.c.e(r1, r3)     // Catch:{ JSONException -> 0x00a4 }
            android.content.Context r1 = r6.c     // Catch:{ JSONException -> 0x00a4 }
            java.util.ArrayList r1 = com.huawei.android.pushselfshow.richpush.html.api.b.a(r1, r2)     // Catch:{ JSONException -> 0x00a4 }
            boolean r1 = r1.contains(r7)     // Catch:{ JSONException -> 0x00a4 }
            if (r1 == 0) goto L_0x00ab
            r2.setPackage(r7)     // Catch:{ JSONException -> 0x00a4 }
            goto L_0x00ab
        L_0x00a4:
            java.lang.String r7 = "PushSelfShowLog"
            java.lang.String r1 = "get packageName error"
            com.huawei.android.pushagent.a.a.c.e(r7, r1)     // Catch:{ Exception | JSONException -> 0x00de }
        L_0x00ab:
            android.content.Context r7 = r6.c     // Catch:{ Exception | JSONException -> 0x00de }
            r7.startActivity(r2)     // Catch:{ Exception | JSONException -> 0x00de }
            com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue r7 = r6.a     // Catch:{ Exception | JSONException -> 0x00de }
            java.lang.String r1 = r6.b     // Catch:{ Exception | JSONException -> 0x00de }
            com.huawei.android.pushselfshow.richpush.html.api.d$a r2 = com.huawei.android.pushselfshow.richpush.html.api.d.a.OK     // Catch:{ Exception | JSONException -> 0x00de }
            java.lang.String r3 = "success"
            r7.a(r1, r2, r3, r0)     // Catch:{ Exception | JSONException -> 0x00de }
            return
        L_0x00bc:
            java.lang.String r7 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception | JSONException -> 0x00de }
            r2.<init>()     // Catch:{ Exception | JSONException -> 0x00de }
            r2.append(r1)     // Catch:{ Exception | JSONException -> 0x00de }
            java.lang.String r1 = "File not exist"
            r2.append(r1)     // Catch:{ Exception | JSONException -> 0x00de }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception | JSONException -> 0x00de }
            com.huawei.android.pushagent.a.a.c.e(r7, r1)     // Catch:{ Exception | JSONException -> 0x00de }
            com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue r7 = r6.a     // Catch:{ Exception | JSONException -> 0x00de }
            java.lang.String r1 = r6.b     // Catch:{ Exception | JSONException -> 0x00de }
            com.huawei.android.pushselfshow.richpush.html.api.d$a r2 = com.huawei.android.pushselfshow.richpush.html.api.d.a.AUDIO_ONLY_SUPPORT_HTTP     // Catch:{ Exception | JSONException -> 0x00de }
            java.lang.String r3 = "error"
            r7.a(r1, r2, r3, r0)     // Catch:{ Exception | JSONException -> 0x00de }
            return
        L_0x00de:
            r7 = move-exception
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = "startPlaying failed "
            com.huawei.android.pushagent.a.a.c.d(r1, r2, r7)
        L_0x00e6:
            com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue r7 = r6.a
            java.lang.String r1 = r6.b
            com.huawei.android.pushselfshow.richpush.html.api.d$a r2 = com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION
            java.lang.String r3 = "error"
            r7.a(r1, r2, r3, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.html.a.k.a(org.json.JSONObject):void");
    }

    public String a(String str, JSONObject jSONObject) {
        return null;
    }

    public void a(int i, int i2, Intent intent) {
    }

    public void a(NativeToJsMessageQueue nativeToJsMessageQueue, String str, String str2, JSONObject jSONObject) {
        if (nativeToJsMessageQueue == null) {
            c.a("PushSelfShowLog", "jsMessageQueue is null while run into Video Player exec");
            return;
        }
        this.a = nativeToJsMessageQueue;
        if ("playVideo".equals(str)) {
            d();
            if (str2 != null) {
                this.b = str2;
                a(jSONObject);
                return;
            }
            c.a("PushSelfShowLog", "Audio exec callback is null ");
            return;
        }
        nativeToJsMessageQueue.a(str2, a.METHOD_NOT_FOUND_EXCEPTION, "error", null);
    }

    public void b() {
    }

    public void c() {
        d();
    }

    public void d() {
        this.b = null;
        this.d = null;
    }
}
