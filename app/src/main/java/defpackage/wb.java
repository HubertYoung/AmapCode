package defpackage;

import org.json.JSONObject;

/* renamed from: wb reason: default package */
/* compiled from: JsCommonAction */
public abstract class wb extends vz {
    private long a = 0;
    private long b = 0;

    public abstract void b(JSONObject jSONObject, wa waVar);

    public boolean b() {
        return false;
    }

    public final void a(JSONObject jSONObject, wa waVar) {
        boolean z;
        if (b()) {
            this.b = System.currentTimeMillis();
            if (this.b - this.a < 1000) {
                z = false;
            } else {
                this.a = this.b;
                z = true;
            }
            if (!z) {
                return;
            }
        }
        if (a() != null) {
            b(jSONObject, waVar);
        }
    }
}
