package com.ali.auth.third.ui.context;

import android.app.Activity;
import android.webkit.WebView;
import com.ali.auth.third.core.context.KernelContext;
import org.json.JSONException;
import org.json.JSONObject;

public class a {
    public WebView a;
    public int b;

    /* access modifiers changed from: private */
    public void d(String str) {
        if (this.a != null) {
            this.a.loadUrl(str);
        }
    }

    /* access modifiers changed from: private */
    public static String e(String str) {
        return str.replace("\\", "\\\\");
    }

    public Activity a() {
        return (Activity) this.a.getContext();
    }

    public void a(int i, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", i);
            jSONObject.put("message", str);
            b(jSONObject.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void a(String str) {
        KernelContext.executorService.postUITask(new b(this, str));
    }

    public void b(String str) {
        KernelContext.executorService.postUITask(new c(this, str));
    }
}
