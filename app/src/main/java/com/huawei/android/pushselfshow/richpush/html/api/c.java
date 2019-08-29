package com.huawei.android.pushselfshow.richpush.html.api;

import android.app.Activity;
import android.content.Intent;
import com.autonavi.miniapp.plugin.PluginManager;
import com.huawei.android.pushselfshow.richpush.html.a.a;
import com.huawei.android.pushselfshow.richpush.html.a.d;
import com.huawei.android.pushselfshow.richpush.html.a.f;
import com.huawei.android.pushselfshow.richpush.html.a.h;
import com.huawei.android.pushselfshow.richpush.html.a.i;
import com.huawei.android.pushselfshow.richpush.html.a.j;
import com.huawei.android.pushselfshow.richpush.html.a.k;
import com.sina.weibo.sdk.constant.WBConstants;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class c {
    public HashMap a = new HashMap();

    public c(Activity activity, boolean z) {
        try {
            this.a.clear();
            this.a.put("Audio", new f(activity));
            this.a.put("Video", new k(activity));
            this.a.put("App", new d(activity));
            this.a.put("Geo", new j(activity));
            this.a.put("Accelerometer", new a(activity));
            this.a.put("Device", new i(activity, z));
        } catch (Exception e) {
            com.huawei.android.pushagent.a.a.c.c(PluginManager.TAG, e.toString(), e);
        }
    }

    public String a(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject(str2);
            if (jSONObject2.has("method")) {
                String string = jSONObject2.getString("method");
                com.huawei.android.pushagent.a.a.c.a(PluginManager.TAG, "method is ".concat(String.valueOf(string)));
                if (jSONObject2.has("options")) {
                    jSONObject = jSONObject2.getJSONObject("options");
                }
                if (!this.a.containsKey(str)) {
                    return d.a(d.a.SERVICE_NOT_FOUND_EXCEPTION).toString();
                }
                StringBuilder sb = new StringBuilder("plugins.containsKey(");
                sb.append(str);
                sb.append(") ");
                com.huawei.android.pushagent.a.a.c.a(PluginManager.TAG, sb.toString());
                return ((h) this.a.get(str)).a(string, jSONObject);
            }
            com.huawei.android.pushagent.a.a.c.a(PluginManager.TAG, "method is null");
            return d.a(d.a.METHOD_NOT_FOUND_EXCEPTION).toString();
        } catch (JSONException unused) {
            return d.a(d.a.JSON_EXCEPTION).toString();
        }
    }

    public void a() {
        for (Entry entry : this.a.entrySet()) {
            StringBuilder sb = new StringBuilder("call plugin: ");
            sb.append((String) entry.getKey());
            sb.append(" reset");
            com.huawei.android.pushagent.a.a.c.e(PluginManager.TAG, sb.toString());
            ((h) entry.getValue()).d();
        }
    }

    public void a(int i, int i2, Intent intent) {
        for (Entry entry : this.a.entrySet()) {
            StringBuilder sb = new StringBuilder("call plugin: ");
            sb.append((String) entry.getKey());
            sb.append(" reset");
            com.huawei.android.pushagent.a.a.c.e(PluginManager.TAG, sb.toString());
            ((h) entry.getValue()).a(i, i2, intent);
        }
    }

    public void a(String str, String str2, NativeToJsMessageQueue nativeToJsMessageQueue) {
        String str3;
        if (nativeToJsMessageQueue == null) {
            com.huawei.android.pushagent.a.a.c.a(PluginManager.TAG, "plugin.exec,jsMessageQueue is null");
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject(str2);
            if (jSONObject2.has(WBConstants.SHARE_CALLBACK_ID)) {
                str3 = jSONObject2.getString(WBConstants.SHARE_CALLBACK_ID);
                try {
                    com.huawei.android.pushagent.a.a.c.a(PluginManager.TAG, "callbackId is ".concat(String.valueOf(str3)));
                } catch (JSONException unused) {
                    nativeToJsMessageQueue.a(str3, d.a.JSON_EXCEPTION, "error", null);
                }
            } else {
                str3 = null;
            }
            if (jSONObject2.has("method")) {
                String string = jSONObject2.getString("method");
                com.huawei.android.pushagent.a.a.c.a(PluginManager.TAG, "method is ".concat(String.valueOf(string)));
                if (jSONObject2.has("options")) {
                    jSONObject = jSONObject2.getJSONObject("options");
                }
                if (this.a.containsKey(str)) {
                    StringBuilder sb = new StringBuilder("plugins.containsKey(");
                    sb.append(str);
                    sb.append(") ");
                    com.huawei.android.pushagent.a.a.c.a(PluginManager.TAG, sb.toString());
                    ((h) this.a.get(str)).a(nativeToJsMessageQueue, string, str3, jSONObject);
                    return;
                }
                nativeToJsMessageQueue.a(str3, d.a.SERVICE_NOT_FOUND_EXCEPTION, "error", null);
                return;
            }
            com.huawei.android.pushagent.a.a.c.a(PluginManager.TAG, "method is null");
            nativeToJsMessageQueue.a(str3, d.a.METHOD_NOT_FOUND_EXCEPTION, "error", null);
        } catch (JSONException unused2) {
            str3 = null;
            nativeToJsMessageQueue.a(str3, d.a.JSON_EXCEPTION, "error", null);
        }
    }

    public void b() {
        for (Entry entry : this.a.entrySet()) {
            StringBuilder sb = new StringBuilder("call plugin: ");
            sb.append((String) entry.getKey());
            sb.append(" reset");
            com.huawei.android.pushagent.a.a.c.e(PluginManager.TAG, sb.toString());
            ((h) entry.getValue()).b();
        }
    }

    public void c() {
        for (Entry entry : this.a.entrySet()) {
            StringBuilder sb = new StringBuilder("call plugin: ");
            sb.append((String) entry.getKey());
            sb.append(" reset");
            com.huawei.android.pushagent.a.a.c.e(PluginManager.TAG, sb.toString());
            ((h) entry.getValue()).c();
        }
    }
}
