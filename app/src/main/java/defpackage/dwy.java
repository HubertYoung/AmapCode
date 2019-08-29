package defpackage;

import android.support.annotation.Nullable;
import org.json.JSONObject;

/* renamed from: dwy reason: default package */
/* compiled from: ChangeAlterRouteInfoData */
public final class dwy {
    public int a;
    public int b;
    public int c;
    public String d;

    @Nullable
    public static dwy a(String str) {
        dwy dwy = new dwy();
        try {
            JSONObject jSONObject = new JSONObject(str);
            dwy.a = jSONObject.optInt("listNumber", -1);
            dwy.b = jSONObject.optInt("segmentIndex", -1);
            dwy.c = jSONObject.optInt("alterIndex", -1);
            dwy.d = jSONObject.optString("data", "");
            return dwy;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
