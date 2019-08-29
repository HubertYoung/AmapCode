package com.huawei.android.pushselfshow.richpush.html.api;

import android.app.Activity;
import android.webkit.WebView;
import com.huawei.android.pushagent.a.a.c;
import com.sina.weibo.sdk.constant.WBConstants;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NativeToJsMessageQueue {
    public WebView a;
    private final LinkedList b = new LinkedList();
    private final a c;
    /* access modifiers changed from: private */
    public final Activity d;
    private String e;

    class OnlineEventsBridgeMode implements a {
        boolean a = true;
        final Runnable b = new a(this);

        OnlineEventsBridgeMode() {
            StringBuilder sb = new StringBuilder("OnlineEventsBridgeMode() the webview is ");
            sb.append(NativeToJsMessageQueue.this.a);
            c.a("PushSelfShowLog", sb.toString());
            NativeToJsMessageQueue.this.a.setNetworkAvailable(true);
        }

        public void onNativeToJsMessageAvailable() {
            NativeToJsMessageQueue.this.d.runOnUiThread(this.b);
        }
    }

    interface a {
        void onNativeToJsMessageAvailable();
    }

    static class b {
        final String a;
        final d b;

        b(d dVar, String str) {
            this.a = str;
            this.b = dVar;
        }

        /* access modifiers changed from: 0000 */
        public JSONObject a() {
            if (this.b == null) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", this.b.a());
                if (this.b.b() != null) {
                    jSONObject.put("message", this.b.b());
                }
                jSONObject.put(WBConstants.SHARE_CALLBACK_ID, this.a);
                return jSONObject;
            } catch (JSONException unused) {
                return null;
            }
        }
    }

    public NativeToJsMessageQueue(Activity activity, WebView webView, String str) {
        c.a("PushSelfShowLog", "activity is ".concat(String.valueOf(activity)));
        c.a("PushSelfShowLog", "webView is ".concat(String.valueOf(webView)));
        c.a("PushSelfShowLog", "localPath is ".concat(String.valueOf(str)));
        this.d = activity;
        this.a = webView;
        this.e = str;
        this.c = new OnlineEventsBridgeMode();
        b();
    }

    /* access modifiers changed from: private */
    public boolean d() {
        boolean isEmpty;
        synchronized (this) {
            isEmpty = this.b.isEmpty();
        }
        return isEmpty;
    }

    public String a() {
        return this.e;
    }

    public void a(String str, com.huawei.android.pushselfshow.richpush.html.api.d.a aVar, String str2, JSONObject jSONObject) {
        try {
            StringBuilder sb = new StringBuilder("addPluginResult status is ");
            sb.append(d.c()[aVar.ordinal()]);
            c.a("PushSelfShowLog", sb.toString());
            if (str == null) {
                c.e("JsMessageQueue", "Got plugin result with no callbackId");
                return;
            }
            b bVar = new b(jSONObject == null ? new d(str2, aVar) : new d(str2, aVar, jSONObject), str);
            synchronized (this) {
                this.b.add(bVar);
                if (this.c != null) {
                    this.c.onNativeToJsMessageAvailable();
                }
            }
        } catch (Exception e2) {
            c.d("PushSelfShowLog", "addPluginResult failed", e2);
        }
    }

    public void b() {
        synchronized (this) {
            this.b.clear();
        }
    }

    public String c() {
        synchronized (this) {
            try {
                if (this.b.isEmpty()) {
                    return null;
                }
                JSONArray jSONArray = new JSONArray();
                int size = this.b.size();
                for (int i = 0; i < size; i++) {
                    JSONObject a2 = ((b) this.b.removeFirst()).a();
                    if (a2 != null) {
                        jSONArray.put(a2);
                    }
                }
                String jSONArray2 = jSONArray.toString();
                return jSONArray2;
            }
        }
    }
}
