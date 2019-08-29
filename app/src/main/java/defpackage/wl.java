package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import java.util.HashMap;

/* renamed from: wl reason: default package */
/* compiled from: AjxViewAdapter */
public final class wl implements wm {
    private JsAdapter a;

    public final String c() {
        return "";
    }

    public wl(JsAdapter jsAdapter) {
        this.a = jsAdapter;
    }

    public final void a(String str, String str2) {
        if (this.a != null) {
            HashMap<String, JsFunctionCallback> ajxCallbackMap = this.a.getAjxCallbackMap();
            if (ajxCallbackMap.containsKey(str)) {
                JsFunctionCallback jsFunctionCallback = ajxCallbackMap.get(str);
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(str2);
                }
                ajxCallbackMap.remove(str);
            }
        }
    }

    public final void a(String str) {
        AMapLog.e("JavaScriptMethod", "AjxView 不支持LoadUrl");
    }

    public final boolean a() {
        AMapLog.e("JavaScriptMethod", "AjxView 不支持Goback");
        return false;
    }

    public final boolean b() {
        AMapLog.e("JavaScriptMethod", "AjxView 不支持Goback");
        return false;
    }

    public final void a(int i) {
        AMapLog.e("JavaScriptMethod", "AjxView 不支持goBackOrForward");
    }

    public final void a(boolean z) {
        AMapLog.e("JavaScriptMethod", "AjxView 不支持setLongClickable");
    }
}
