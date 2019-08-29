package com.amap.location.sdk.d.a;

import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.util.Base64;
import com.amap.location.d.b;
import com.amap.location.d.c;
import com.autonavi.indoor.onlinelocation.OnlineLocator;
import org.json.JSONObject;

/* compiled from: IndoorLocationProvider */
public class a {
    private c a;
    private boolean b;

    public a(Context context, b bVar, JSONObject jSONObject, Looper looper) {
        c cVar = new c(context, bVar, jSONObject.toString(), "m5.amap.com", new com.amap.location.d.c.a() {
            public String a(String str) {
                return com.amap.location.sdk.d.b.b.a(str);
            }

            public byte[] a(byte[] bArr) {
                return com.amap.location.sdk.d.b.b.a(bArr);
            }
        }, looper, false);
        this.a = cVar;
    }

    public void a() {
        this.a.a();
        this.b = true;
    }

    public void b() {
        this.a.b();
        this.b = false;
    }

    public void a(JSONObject jSONObject) {
        this.a.a(jSONObject.toString());
    }

    public int c() {
        return this.a.c();
    }

    public void a(Location location) {
        if (this.b) {
            this.a.a(location);
        }
    }

    public String d() {
        try {
            byte[] feedbackBuffer = OnlineLocator.getInstance().getFeedbackBuffer();
            if (feedbackBuffer != null) {
                return Base64.encodeToString(feedbackBuffer, 2);
            }
        } catch (Exception e) {
            com.amap.location.common.d.a.a((Throwable) e);
        }
        return null;
    }
}
