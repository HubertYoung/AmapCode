package defpackage;

import org.json.JSONObject;

/* renamed from: cvi reason: default package */
/* compiled from: PluginData */
public final class cvi {
    public String a;
    public JSONObject b;
    public boolean c;

    public cvi(String str, boolean z, JSONObject jSONObject) {
        this.a = str;
        this.c = z;
        this.b = jSONObject;
    }

    public cvi(String str) {
        this(str, true, null);
    }
}
