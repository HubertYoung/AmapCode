package com.alipay.sdk.authjs;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.sdk.authjs.a.C0005a;
import java.util.Timer;
import org.json.JSONException;
import org.json.JSONObject;

public final class d {
    /* access modifiers changed from: 0000 */
    public c a;
    Context b;

    public d(Context context, c cVar) {
        this.b = context;
        this.a = cVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0046 A[SYNTHETIC, Splitter:B:17:0x0046] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.lang.String r6) {
        /*
            r5 = this;
            r0 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x003f }
            r1.<init>(r6)     // Catch:{ Exception -> 0x003f }
            java.lang.String r6 = "clientId"
            java.lang.String r6 = r1.getString(r6)     // Catch:{ Exception -> 0x003f }
            boolean r2 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x0040 }
            if (r2 == 0) goto L_0x0013
            return
        L_0x0013:
            java.lang.String r2 = "param"
            org.json.JSONObject r2 = r1.getJSONObject(r2)     // Catch:{ Exception -> 0x0040 }
            boolean r3 = r2 instanceof org.json.JSONObject     // Catch:{ Exception -> 0x0040 }
            if (r3 == 0) goto L_0x0020
            r0 = r2
            org.json.JSONObject r0 = (org.json.JSONObject) r0     // Catch:{ Exception -> 0x0040 }
        L_0x0020:
            java.lang.String r2 = "func"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x0040 }
            java.lang.String r3 = "bundleName"
            java.lang.String r1 = r1.getString(r3)     // Catch:{ Exception -> 0x0040 }
            com.alipay.sdk.authjs.a r3 = new com.alipay.sdk.authjs.a     // Catch:{ Exception -> 0x0040 }
            java.lang.String r4 = "call"
            r3.<init>(r4)     // Catch:{ Exception -> 0x0040 }
            r3.j = r1     // Catch:{ Exception -> 0x0040 }
            r3.k = r2     // Catch:{ Exception -> 0x0040 }
            r3.m = r0     // Catch:{ Exception -> 0x0040 }
            r3.i = r6     // Catch:{ Exception -> 0x0040 }
            r5.a(r3)     // Catch:{ Exception -> 0x0040 }
            return
        L_0x003f:
            r6 = r0
        L_0x0040:
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 != 0) goto L_0x004c
            int r0 = com.alipay.sdk.authjs.a.C0005a.d     // Catch:{ JSONException -> 0x004c }
            r5.a(r6, r0)     // Catch:{ JSONException -> 0x004c }
            return
        L_0x004c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.authjs.d.a(java.lang.String):void");
    }

    public final void a(String str, int i) throws JSONException {
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("error", i - 1);
            a aVar = new a("callback");
            aVar.m = jSONObject;
            aVar.i = str;
            this.a.a(aVar);
        }
    }

    private static void a(Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    public final void a(a aVar) throws JSONException {
        if (TextUtils.isEmpty(aVar.k)) {
            a(aVar.i, C0005a.c);
            return;
        }
        e eVar = new e(this, aVar);
        if (Looper.getMainLooper() == Looper.myLooper()) {
            eVar.run();
        } else {
            new Handler(Looper.getMainLooper()).post(eVar);
        }
    }

    private int b(a aVar) {
        if (aVar != null && "toast".equals(aVar.k)) {
            JSONObject jSONObject = aVar.m;
            String optString = jSONObject.optString("content");
            int i = 1;
            if (jSONObject.optInt("duration") < 2500) {
                i = 0;
            }
            Toast.makeText(this.b, optString, i).show();
            new Timer().schedule(new f(this, aVar), (long) i);
        }
        return C0005a.a;
    }

    private void c(a aVar) {
        JSONObject jSONObject = aVar.m;
        String optString = jSONObject.optString("content");
        int i = jSONObject.optInt("duration") < 2500 ? 0 : 1;
        Toast.makeText(this.b, optString, i).show();
        new Timer().schedule(new f(this, aVar), (long) i);
    }

    private static /* synthetic */ int a(d dVar, a aVar) {
        if (aVar != null && "toast".equals(aVar.k)) {
            JSONObject jSONObject = aVar.m;
            String optString = jSONObject.optString("content");
            int i = 1;
            if (jSONObject.optInt("duration") < 2500) {
                i = 0;
            }
            Toast.makeText(dVar.b, optString, i).show();
            new Timer().schedule(new f(dVar, aVar), (long) i);
        }
        return C0005a.a;
    }
}
