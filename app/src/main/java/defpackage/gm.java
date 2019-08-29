package defpackage;

import android.graphics.Color;
import org.json.JSONArray;

/* renamed from: gm reason: default package */
/* compiled from: ColorFactory */
public final class gm implements a<Integer> {
    public static final gm a = new gm();

    public final /* synthetic */ Object a(Object obj, float f) {
        JSONArray jSONArray = (JSONArray) obj;
        if (jSONArray.length() != 4) {
            return Integer.valueOf(-16777216);
        }
        boolean z = true;
        for (int i = 0; i < jSONArray.length(); i++) {
            if (jSONArray.optDouble(i) > 1.0d) {
                z = false;
            }
        }
        double d = (double) (z ? 255.0f : 1.0f);
        return Integer.valueOf(Color.argb((int) (jSONArray.optDouble(3) * d), (int) (jSONArray.optDouble(0) * d), (int) (jSONArray.optDouble(1) * d), (int) (jSONArray.optDouble(2) * d)));
    }
}
