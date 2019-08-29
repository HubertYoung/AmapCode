package defpackage;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: lq reason: default package */
/* compiled from: ModuleData */
public final class lq {
    public String a;
    public int b = 0;
    public String c;
    public String d;
    public long e = -1;

    public lq(String str, JSONObject jSONObject) {
        this.a = str;
        this.b = jSONObject.optInt("status", 0);
        this.c = jSONObject.optString("version");
        this.d = jSONObject.optString("value");
        if (!TextUtils.isEmpty(this.d)) {
            try {
                this.e = new JSONObject(this.d).optLong("_update_frequency") * 1000;
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("name = ");
        sb2.append(this.a);
        sb.append(sb2.toString());
        sb.append("/n");
        StringBuilder sb3 = new StringBuilder("status = ");
        sb3.append(this.b);
        sb.append(sb3.toString());
        sb.append("/n");
        StringBuilder sb4 = new StringBuilder("version = ");
        sb4.append(this.c);
        sb.append(sb4.toString());
        sb.append("/n");
        StringBuilder sb5 = new StringBuilder("value = ");
        sb5.append(this.d);
        sb.append(sb5.toString());
        sb.append("/n");
        StringBuilder sb6 = new StringBuilder("durationtime = ");
        sb6.append(this.e);
        sb.append(sb6.toString());
        return sb.toString();
    }
}
