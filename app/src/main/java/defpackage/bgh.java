package defpackage;

import android.text.TextUtils;
import org.json.JSONObject;

/* renamed from: bgh reason: default package */
/* compiled from: MitVuiSetRouteTravelToolModel */
public final class bgh extends bgd {
    public final String a() {
        return "setRouteTravelTool";
    }

    public final boolean a(bgb bgb, bfb bfb) {
        if (TextUtils.isEmpty(bgb.b)) {
            return false;
        }
        JSONObject c = eqa.c(bgb.b);
        if (c == null) {
            return false;
        }
        int a = agd.a(c, "param_type");
        if (a == -1 || eqg.a(a)) {
            StringBuilder sb = new StringBuilder("handleVUICmd ");
            sb.append(bgb.b);
            bfh.a("MitVuiSetRouteTravelToolModel", sb.toString());
            d.a.a(bgb.a, 10000, (String) null);
            return true;
        }
        d.a.a(bgb.a, 10128, (String) null);
        return true;
    }
}
