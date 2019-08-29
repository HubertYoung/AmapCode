package defpackage;

import android.graphics.PointF;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: gv reason: default package */
/* compiled from: PointFFactory */
public final class gv implements a<PointF> {
    public static final gv a = new gv();

    private gv() {
    }

    public final /* synthetic */ Object a(Object obj, float f) {
        if (obj instanceof JSONArray) {
            return JsonUtils.a((JSONArray) obj, f);
        }
        if (obj instanceof JSONObject) {
            return JsonUtils.a((JSONObject) obj, f);
        }
        throw new IllegalArgumentException("Unable to parse point from ".concat(String.valueOf(obj)));
    }
}
