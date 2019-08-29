package defpackage;

import java.util.List;
import org.json.JSONObject;

/* renamed from: ddx reason: default package */
/* compiled from: EtaResponseData */
public final class ddx {
    public int a;
    public String b;
    public String c;
    public String d;
    public List<ddy> e;
    private JSONObject f;

    public ddx(JSONObject jSONObject) {
        this.f = jSONObject;
    }

    public final String toString() {
        return this.f.toString();
    }
}
