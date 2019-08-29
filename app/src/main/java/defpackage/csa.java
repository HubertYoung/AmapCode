package defpackage;

import com.alibaba.analytics.core.device.Constants;
import org.json.JSONObject;

/* renamed from: csa reason: default package */
/* compiled from: RelationTrafficEvent */
public final class csa {
    public String a = "";
    public String b = "";
    public String c = "";
    public long d = 0;
    public int e;
    public double f;
    public double g;

    public csa(JSONObject jSONObject) {
        this.a = jSONObject.optString("eventid");
        this.b = jSONObject.optString("mainkey");
        this.c = jSONObject.optString("subkey");
        this.d = jSONObject.optLong("rank");
        this.e = jSONObject.optInt("minzoom");
        this.f = jSONObject.optDouble("coordx");
        this.g = jSONObject.optDouble("coordy");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(Constants.SEPARATOR);
        sb.append(this.b);
        sb.append(Constants.SEPARATOR);
        sb.append(this.c);
        sb.append(Constants.SEPARATOR);
        sb.append(this.d);
        sb.append(Constants.SEPARATOR);
        sb.append(this.e);
        sb.append(Constants.SEPARATOR);
        sb.append(this.f);
        sb.append(Constants.SEPARATOR);
        sb.append(this.g);
        return sb.toString();
    }
}
