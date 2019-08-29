package defpackage;

import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: dqc reason: default package */
/* compiled from: AosHotelResponser */
public final class dqc extends dog {
    public int i;
    public JSONObject j;
    public int k = 1;

    public dqc(int i2, int i3) {
        this.i = i2;
        this.k = i3;
    }

    public dqc(int i2) {
        this.i = i2;
    }

    public final void a(byte[] bArr) {
        super.a(bArr);
        this.j = this.h;
    }

    public final String a() {
        return this.c != 0 ? "" : "";
    }

    public final JSONArray b() {
        if (this.j != null) {
            return this.j.optJSONArray("orders");
        }
        return null;
    }
}
