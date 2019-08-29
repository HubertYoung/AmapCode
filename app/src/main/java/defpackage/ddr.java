package defpackage;

import com.alipay.mobile.h5container.api.H5Param;
import org.json.JSONObject;

/* renamed from: ddr reason: default package */
/* compiled from: ToolsBoxModel */
public final class ddr {
    public int a;
    public String b;
    public String c;
    public String d;
    public int e;
    public int f;
    public int g;
    public a h;
    public int i = -1;

    /* renamed from: ddr$a */
    /* compiled from: ToolsBoxModel */
    public static class a {
        public int a;
        public String b;
    }

    public final JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", this.a);
            jSONObject.put("name", this.b);
            jSONObject.put(H5Param.MENU_ICON, this.d);
            jSONObject.put("position", this.e);
            jSONObject.put("type", this.f);
            jSONObject.put("is_new", this.g);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("action_type", this.h.a);
            jSONObject2.put("url", this.h.b);
            jSONObject.put("action", jSONObject2);
        } catch (Exception e2) {
            kf.a((Throwable) e2);
        }
        return jSONObject;
    }
}
