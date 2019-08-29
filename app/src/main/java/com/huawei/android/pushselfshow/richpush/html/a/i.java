package com.huawei.android.pushselfshow.richpush.html.a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.android.phone.a.a.a;
import com.huawei.android.pushagent.a.a.a.e;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushagent.a.a.d;
import com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue;
import com.taobao.accs.common.Constants;
import org.json.JSONObject;

public class i implements h {
    private Activity a;
    private NativeToJsMessageQueue b;
    private String c;
    private boolean d = false;

    public i(Activity activity, boolean z) {
        c.e("PushSelfShowLog", "init App");
        this.a = activity;
        this.d = z;
    }

    private String e() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("manufacturer", Build.MANUFACTURER);
            jSONObject.put(Constants.KEY_MODEL, Build.MODEL);
            jSONObject.put("version", Build.DISPLAY);
            jSONObject.put("os", a.a);
            jSONObject.put("osVersion", VERSION.RELEASE);
            jSONObject.put("uuid", a());
            jSONObject.put(Constants.KEY_SDK_VERSION, "2816");
            if (this.d) {
                jSONObject.put(Constants.KEY_IMEI, com.huawei.android.pushselfshow.utils.a.a(com.huawei.android.pushselfshow.utils.a.b((Context) this.a)));
            }
        } catch (Exception e) {
            c.d("PushSelfShowLog", "onError error", e);
        }
        return jSONObject.toString();
    }

    public String a() {
        try {
            String a2 = d.a(this.a, "push_client_self_info", "token_info");
            if (TextUtils.isEmpty(a2)) {
                a2 = com.huawei.android.pushselfshow.utils.a.b((Context) this.a);
            }
            return e.a(a2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String a(String str, JSONObject jSONObject) {
        return "getDeviceInfo".equals(str) ? e() : com.huawei.android.pushselfshow.richpush.html.api.d.a(com.huawei.android.pushselfshow.richpush.html.api.d.a.ERROR).toString();
    }

    public void a(int i, int i2, Intent intent) {
    }

    public void a(NativeToJsMessageQueue nativeToJsMessageQueue, String str, String str2, JSONObject jSONObject) {
        if (nativeToJsMessageQueue == null) {
            c.a("PushSelfShowLog", "jsMessageQueue is null while run into App exec");
            return;
        }
        this.b = nativeToJsMessageQueue;
        if (str2 != null) {
            this.c = str2;
        } else {
            c.a("PushSelfShowLog", "get DeviceInfo exec callback is null ");
        }
        this.b.a(this.c, com.huawei.android.pushselfshow.richpush.html.api.d.a.METHOD_NOT_FOUND_EXCEPTION, "error", null);
    }

    public void b() {
    }

    public void c() {
    }

    public void d() {
    }
}
