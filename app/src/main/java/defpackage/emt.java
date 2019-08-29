package defpackage;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: emt reason: default package */
/* compiled from: AmapVersionInfo */
public final class emt {
    String a;
    String b;
    String c;
    String d;
    String e;

    public static emt a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        emt emt = new emt();
        try {
            JSONObject jSONObject = new JSONObject(str);
            emt.a = jSONObject.optString("versionName");
            emt.b = jSONObject.optString("chanel");
            emt.c = jSONObject.optString("buildNumber");
            emt.d = jSONObject.optString("firstInstalTime");
            emt.e = jSONObject.optString("lastUpdateTime");
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return emt;
    }

    public final String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("versionName", this.a);
            jSONObject.put("chanel", this.b);
            jSONObject.put("buildNumber", this.c);
            jSONObject.put("firstInstalTime", this.d);
            jSONObject.put("lastUpdateTime", this.e);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        emt emt = (emt) obj;
        if (this.a != null && !this.a.equals(emt.a)) {
            return false;
        }
        if (this.a == null && emt.a != null) {
            return false;
        }
        if (this.b != null && !this.b.equals(emt.b)) {
            return false;
        }
        if (this.b == null && emt.b != null) {
            return false;
        }
        if (this.c != null && !this.c.equals(emt.c)) {
            return false;
        }
        if (this.c == null && emt.c != null) {
            return false;
        }
        if (this.d != null && !this.d.equals(emt.d)) {
            return false;
        }
        if (this.d == null && emt.d != null) {
            return false;
        }
        if (this.e != null) {
            return this.e.equals(emt.e);
        }
        return emt.e == null;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((this.a != null ? this.a.hashCode() : 0) * 31) + (this.b != null ? this.b.hashCode() : 0)) * 31) + (this.c != null ? this.c.hashCode() : 0)) * 31) + (this.d != null ? this.d.hashCode() : 0)) * 31;
        if (this.e != null) {
            i = this.e.hashCode();
        }
        return hashCode + i;
    }
}
