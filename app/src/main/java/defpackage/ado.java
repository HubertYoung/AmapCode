package defpackage;

import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: ado reason: default package */
/* compiled from: SchoolbusStatusRequest */
public final class ado {
    public JsFunctionCallback a;
    public adm b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g = "SchoolbusStatusRequest";

    public ado(Map<String, String> map, JsFunctionCallback jsFunctionCallback, adm adm) {
        this.b = adm;
        this.a = jsFunctionCallback;
        JSONObject jSONObject = new JSONObject(map);
        this.c = jSONObject.optString("role");
        this.d = jSONObject.optString("routeId");
        this.e = jSONObject.optString("latitude");
        this.f = jSONObject.optString("longitude");
    }
}
