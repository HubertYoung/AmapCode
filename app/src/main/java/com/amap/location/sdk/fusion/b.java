package com.amap.location.sdk.fusion;

import android.content.Context;
import android.location.LocationListener;
import android.os.Looper;
import android.support.annotation.NonNull;
import org.json.JSONObject;

/* compiled from: LocationProviderProxy */
public class b implements a {
    private com.amap.location.sdk.d.b a;

    public void a(LocationStatusListener locationStatusListener) {
    }

    public void a(@NonNull String str, int i, int i2, String str2) {
    }

    public boolean a() {
        return true;
    }

    public b(Context context, LocationListener locationListener, Looper looper) {
        this.a = new com.amap.location.sdk.d.b(context, locationListener, false, looper);
    }

    public void a(int i, long j, float f, boolean z) {
        this.a.a(i, j, f, z);
    }

    public void b() {
        this.a.a();
    }

    public void a(@NonNull JSONObject jSONObject) {
        this.a.a(jSONObject, false);
    }

    public String a(String str) {
        return this.a.a(str);
    }

    public void c() {
        this.a.a();
    }
}
