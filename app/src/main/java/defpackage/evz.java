package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: evz reason: default package */
/* compiled from: PostObjActivity */
public class evz {
    private static final String k = "evz";
    String a;
    String b;
    String c;
    int d;
    String e;
    String f;
    String g;
    String h;
    String i;
    private int j;

    public evz(int i2, String str, String str2, String str3, String str4, int i3, String str5, String str6, String str7, String str8) {
        this.j = i2;
        this.a = str2;
        this.e = str;
        this.b = str3;
        this.c = str4;
        this.d = i3;
        this.f = str5;
        this.g = str6;
        this.h = str7;
        this.i = str8;
    }

    public final Boolean a(int i2, int i3, boolean z, String str) {
        boolean z2 = false;
        if (z) {
            euw.a("hashCode = ".concat(String.valueOf(i2)));
            StringBuilder sb = new StringBuilder("uniqueId = ");
            sb.append(this.j);
            euw.a(sb.toString());
            euw.a("extraPageName = ".concat(String.valueOf(str)));
            if (i2 == this.j) {
                z2 = true;
            }
            return Boolean.valueOf(z2);
        }
        euw.a("hashCode = ".concat(String.valueOf(i2)));
        StringBuilder sb2 = new StringBuilder("uniqueId = ");
        sb2.append(this.j);
        euw.a(sb2.toString());
        StringBuilder sb3 = new StringBuilder("type = ");
        sb3.append(i3);
        sb3.append(",this.type = ");
        sb3.append(i3);
        euw.a(sb3.toString());
        StringBuilder sb4 = new StringBuilder("extraPageName = ");
        sb4.append(str);
        sb4.append(",this.extraPageName = ");
        sb4.append(this.i);
        euw.a(sb4.toString());
        if (TextUtils.isEmpty(this.i)) {
            if (!TextUtils.isEmpty(str)) {
                return Boolean.FALSE;
            }
            if (i2 == this.j && this.d == i3) {
                z2 = true;
            }
            return Boolean.valueOf(z2);
        } else if (TextUtils.isEmpty(str)) {
            return Boolean.FALSE;
        } else {
            if (i2 == this.j && this.d == i3 && this.i.equals(str)) {
                z2 = true;
            }
            return Boolean.valueOf(z2);
        }
    }

    public final JSONObject a(Context context, String str) {
        JSONObject jSONObject;
        StringBuilder sb = new StringBuilder();
        sb.append(Long.valueOf(str).longValue() - Long.valueOf(this.c).longValue());
        String sb2 = sb.toString();
        try {
            jSONObject = evx.a(context, (String) WidgetType.ACTIVITY);
            try {
                jSONObject.put("session_id", this.b);
                jSONObject.put(WidgetType.ACTIVITY, this.a);
                jSONObject.put("start_ts", this.c);
                jSONObject.put("end_ts", str);
                jSONObject.put("duration", sb2);
                jSONObject.put("_activity", this.e);
                jSONObject.put("_androidid", this.h);
            } catch (JSONException e2) {
                e = e2;
            }
        } catch (JSONException e3) {
            e = e3;
            jSONObject = new JSONObject();
            StringBuilder sb3 = new StringBuilder("Collected:");
            sb3.append(e.getMessage());
            euw.a(sb3.toString());
            return jSONObject;
        }
        return jSONObject;
    }
}
