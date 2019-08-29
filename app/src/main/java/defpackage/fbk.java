package defpackage;

import android.content.Context;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: fbk reason: default package */
/* compiled from: WeiboSsoSdkConfig */
public final class fbk implements Cloneable {
    public Context a;
    public String b = "";
    public String c = "";
    public String d = "";
    public String e = "";
    public String f = "";
    public String g = "";
    private String h = "";
    private HashMap<String, String> i = new HashMap<>();

    public static String a(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    public final String a() {
        if (this.i.isEmpty()) {
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        for (Entry next : this.i.entrySet()) {
            try {
                jSONObject.put((String) next.getKey(), next.getValue());
            } catch (JSONException unused) {
                return "";
            }
        }
        return a(jSONObject.toString());
    }

    public final String a(boolean z) {
        if (z) {
            return a(this.b);
        }
        return this.b;
    }

    public final boolean b() {
        return this.a != null && !TextUtils.isEmpty(this.b) && !TextUtils.isEmpty(this.d) && !TextUtils.isEmpty(this.e);
    }

    public final Object clone() {
        try {
            fbk fbk = (fbk) super.clone();
            HashMap<String, String> hashMap = new HashMap<>();
            for (Entry next : fbk.i.entrySet()) {
                hashMap.put(next.getKey(), next.getValue());
            }
            fbk.i = hashMap;
            return fbk;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
