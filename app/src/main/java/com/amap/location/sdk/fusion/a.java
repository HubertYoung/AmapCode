package com.amap.location.sdk.fusion;

import android.support.annotation.NonNull;
import org.json.JSONObject;

/* compiled from: ILocationProxy */
public interface a {
    String a(String str);

    void a(int i, long j, float f, boolean z);

    void a(LocationStatusListener locationStatusListener);

    void a(@NonNull String str, int i, int i2, String str2);

    void a(@NonNull JSONObject jSONObject);

    boolean a();

    void b();

    void c();
}
