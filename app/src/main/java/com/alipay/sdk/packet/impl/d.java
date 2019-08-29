package com.alipay.sdk.packet.impl;

import android.content.Context;
import com.alipay.mobile.common.transport.http.multipart.FilePart;
import com.alipay.sdk.packet.b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

public final class d extends com.alipay.sdk.packet.d {
    public static final String t = "log_v";

    public final String a(String str, JSONObject jSONObject) {
        return str;
    }

    public final JSONObject a() throws JSONException {
        return null;
    }

    public final List<Header> a(boolean z, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BasicHeader(com.alipay.sdk.packet.d.a, String.valueOf(z)));
        arrayList.add(new BasicHeader("content-type", FilePart.DEFAULT_CONTENT_TYPE));
        arrayList.add(new BasicHeader(com.alipay.sdk.packet.d.g, "CBC"));
        return arrayList;
    }

    public final String c() throws JSONException {
        HashMap hashMap = new HashMap();
        hashMap.put(com.alipay.sdk.packet.d.i, "/sdk/log");
        hashMap.put(com.alipay.sdk.packet.d.j, "1.0.0");
        HashMap hashMap2 = new HashMap();
        hashMap2.put(t, "1.0");
        return a(hashMap, hashMap2);
    }

    public final b a(Context context, String str) throws Throwable {
        return a(context, str, "https://mcgw.alipay.com/sdklog.do", true);
    }
}
