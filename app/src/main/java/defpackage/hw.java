package defpackage;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import org.json.JSONObject;

/* renamed from: hw reason: default package */
/* compiled from: ShapePath */
public final class hw implements hn {
    public final String a;
    public final he b;
    private final int c;

    /* renamed from: hw$a */
    /* compiled from: ShapePath */
    static class a {
        static hw a(JSONObject jSONObject, ev evVar) {
            return new hw(jSONObject.optString(LogItem.MM_C18_K4_NM), jSONObject.optInt("ind"), defpackage.he.a.a(jSONObject.optJSONObject("ks"), evVar), 0);
        }
    }

    /* synthetic */ hw(String str, int i, he heVar, byte b2) {
        this(str, i, heVar);
    }

    private hw(String str, int i, he heVar) {
        this.a = str;
        this.c = i;
        this.b = heVar;
    }

    public final fe a(ew ewVar, hx hxVar) {
        return new fr(ewVar, hxVar, this);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ShapePath{name=");
        sb.append(this.a);
        sb.append(", index=");
        sb.append(this.c);
        sb.append(", hasAnimation=");
        sb.append(this.b.d());
        sb.append('}');
        return sb.toString();
    }
}
