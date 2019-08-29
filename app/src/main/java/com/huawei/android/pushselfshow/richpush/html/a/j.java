package com.huawei.android.pushselfshow.richpush.html.a;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.richpush.html.a.a.a;
import com.huawei.android.pushselfshow.richpush.html.a.a.b;
import com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue;
import com.huawei.android.pushselfshow.richpush.html.api.d;
import org.json.JSONException;
import org.json.JSONObject;

public class j implements h {
    public boolean a = false;
    public boolean b = false;
    public long c = 2000;
    public int d = 0;
    public String e;
    public NativeToJsMessageQueue f;
    private a g;
    private b h;
    private LocationManager i;
    private Activity j = null;

    public j(Activity activity) {
        try {
            c.e("PushSelfShowLog", "init GeoBroker");
            this.j = activity;
        } catch (Exception e2) {
            c.d("PushSelfShowLog", "init GeoBroker error ", e2);
        }
    }

    private void a() {
        d();
    }

    private void e() {
        if (this.a) {
            if (this.g != null) {
                this.g.a(this.c, (float) this.d);
            }
            if (this.i != null) {
                Location lastKnownLocation = this.i.getLastKnownLocation(WidgetType.GPS);
                if (lastKnownLocation != null) {
                    b(lastKnownLocation);
                }
            }
        } else {
            if (this.h != null) {
                this.h.a(this.c, (float) this.d);
            }
            if (this.i != null) {
                Location lastKnownLocation2 = this.i.getLastKnownLocation("network");
                if (lastKnownLocation2 == null) {
                    lastKnownLocation2 = this.i.getLastKnownLocation(WidgetType.GPS);
                }
                if (lastKnownLocation2 != null) {
                    b(lastKnownLocation2);
                }
            }
        }
    }

    public String a(String str, JSONObject jSONObject) {
        return null;
    }

    public JSONObject a(Location location) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("latitude", location.getLatitude());
            jSONObject.put("longitude", location.getLongitude());
            Float f2 = null;
            jSONObject.put("altitude", location.hasAltitude() ? Double.valueOf(location.getAltitude()) : null);
            jSONObject.put(CameraControllerManager.MY_POILOCATION_ACR, (double) location.getAccuracy());
            if (location.hasBearing() && location.hasSpeed()) {
                f2 = Float.valueOf(location.getBearing());
            }
            jSONObject.put("heading", f2);
            jSONObject.put("velocity", (double) location.getSpeed());
            jSONObject.put("timestamp", location.getTime());
            return jSONObject;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return jSONObject;
        }
    }

    public void a(int i2, int i3, Intent intent) {
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:23|24) */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        a(com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION);
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0083 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue r3, java.lang.String r4, java.lang.String r5, org.json.JSONObject r6) {
        /*
            r2 = this;
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r1 = "run into geo broker exec"
            com.huawei.android.pushagent.a.a.c.e(r0, r1)     // Catch:{ Exception -> 0x00a5 }
            r2.d()     // Catch:{ Exception -> 0x00a5 }
            android.app.Activity r0 = r2.j     // Catch:{ Exception -> 0x00a5 }
            java.lang.String r1 = "location"
            java.lang.Object r0 = r0.getSystemService(r1)     // Catch:{ Exception -> 0x00a5 }
            android.location.LocationManager r0 = (android.location.LocationManager) r0     // Catch:{ Exception -> 0x00a5 }
            r2.i = r0     // Catch:{ Exception -> 0x00a5 }
            com.huawei.android.pushselfshow.richpush.html.a.a.b r0 = new com.huawei.android.pushselfshow.richpush.html.a.a.b     // Catch:{ Exception -> 0x00a5 }
            android.location.LocationManager r1 = r2.i     // Catch:{ Exception -> 0x00a5 }
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x00a5 }
            r2.h = r0     // Catch:{ Exception -> 0x00a5 }
            com.huawei.android.pushselfshow.richpush.html.a.a.a r0 = new com.huawei.android.pushselfshow.richpush.html.a.a.a     // Catch:{ Exception -> 0x00a5 }
            android.location.LocationManager r1 = r2.i     // Catch:{ Exception -> 0x00a5 }
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x00a5 }
            r2.g = r0     // Catch:{ Exception -> 0x00a5 }
            r2.e = r5     // Catch:{ Exception -> 0x00a5 }
            if (r3 != 0) goto L_0x0034
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.String r4 = "jsMessageQueue is null while run into App exec"
            com.huawei.android.pushagent.a.a.c.a(r3, r4)     // Catch:{ Exception -> 0x00a5 }
            return
        L_0x0034:
            r2.f = r3     // Catch:{ Exception -> 0x00a5 }
            java.lang.String r3 = "getLocation"
            boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x00a5 }
            if (r3 == 0) goto L_0x008c
            java.lang.String r3 = "useGps"
            boolean r3 = r6.has(r3)     // Catch:{ JSONException -> 0x0083 }
            if (r3 == 0) goto L_0x004e
            java.lang.String r3 = "useGps"
            boolean r3 = r6.getBoolean(r3)     // Catch:{ JSONException -> 0x0083 }
            r2.a = r3     // Catch:{ JSONException -> 0x0083 }
        L_0x004e:
            java.lang.String r3 = "keepLoc"
            boolean r3 = r6.has(r3)     // Catch:{ JSONException -> 0x0083 }
            if (r3 == 0) goto L_0x0088
            java.lang.String r3 = "keepLoc"
            boolean r3 = r6.getBoolean(r3)     // Catch:{ JSONException -> 0x0083 }
            r2.b = r3     // Catch:{ JSONException -> 0x0083 }
            boolean r3 = r2.b     // Catch:{ JSONException -> 0x0083 }
            if (r3 == 0) goto L_0x0088
            java.lang.String r3 = "minTime"
            boolean r3 = r6.has(r3)     // Catch:{ JSONException -> 0x0083 }
            if (r3 == 0) goto L_0x0072
            java.lang.String r3 = "minTime"
            long r3 = r6.getLong(r3)     // Catch:{ JSONException -> 0x0083 }
            r2.c = r3     // Catch:{ JSONException -> 0x0083 }
        L_0x0072:
            java.lang.String r3 = "minDistance"
            boolean r3 = r6.has(r3)     // Catch:{ JSONException -> 0x0083 }
            if (r3 == 0) goto L_0x0088
            java.lang.String r3 = "minDistance"
            int r3 = r6.getInt(r3)     // Catch:{ JSONException -> 0x0083 }
            r2.d = r3     // Catch:{ JSONException -> 0x0083 }
            goto L_0x0088
        L_0x0083:
            com.huawei.android.pushselfshow.richpush.html.api.d$a r3 = com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION     // Catch:{ Exception -> 0x00a5 }
            r2.a(r3)     // Catch:{ Exception -> 0x00a5 }
        L_0x0088:
            r2.e()     // Catch:{ Exception -> 0x00a5 }
            return
        L_0x008c:
            java.lang.String r3 = "clearWatch"
            boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x00a5 }
            if (r3 == 0) goto L_0x009f
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.String r4 = "call method clearWatch"
            com.huawei.android.pushagent.a.a.c.e(r3, r4)     // Catch:{ Exception -> 0x00a5 }
            r2.a()     // Catch:{ Exception -> 0x00a5 }
            return
        L_0x009f:
            com.huawei.android.pushselfshow.richpush.html.api.d$a r3 = com.huawei.android.pushselfshow.richpush.html.api.d.a.METHOD_NOT_FOUND_EXCEPTION     // Catch:{ Exception -> 0x00a5 }
            r2.a(r3)     // Catch:{ Exception -> 0x00a5 }
            return
        L_0x00a5:
            r3 = move-exception
            java.lang.String r4 = "PushSelfShowLog"
            java.lang.String r5 = "run into geo broker exec error "
            com.huawei.android.pushagent.a.a.c.d(r4, r5, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.html.a.j.a(com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue, java.lang.String, java.lang.String, org.json.JSONObject):void");
    }

    public void a(d.a aVar) {
        c.a((String) "PushSelfShowLog", (String) "geo broker fail ,reason is %s", d.c()[aVar.ordinal()]);
        if (this.f != null) {
            this.f.a(this.e, aVar, "error", null);
        }
    }

    public void b() {
    }

    public void b(Location location) {
        if (this.f != null) {
            this.f.a(this.e, d.a.OK, "success", a(location));
        }
    }

    public void c() {
        d();
    }

    public void d() {
        c.e("PushSelfShowLog", "call geo broker reset");
        try {
            if (this.h != null) {
                this.h.a();
                this.h = null;
            }
            if (this.g != null) {
                this.g.a();
                this.g = null;
            }
            this.i = null;
            this.a = false;
            this.b = false;
            this.c = 2000;
            this.d = 0;
            this.e = null;
            this.f = null;
        } catch (Exception e2) {
            c.d("PushSelfShowLog", "call GeoBroker reset error", e2);
        }
    }
}
