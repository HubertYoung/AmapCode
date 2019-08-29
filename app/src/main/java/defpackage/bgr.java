package defpackage;

import org.json.JSONObject;

/* renamed from: bgr reason: default package */
/* compiled from: VPageUtil */
public final class bgr {
    public static long a(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof bgm) {
            return ((bgm) obj).getScenesID();
        }
        if (obj instanceof bfk) {
            return ((bfk) obj).h();
        }
        return 0;
    }

    public static JSONObject b(Object obj) {
        if (obj == null) {
            return new JSONObject();
        }
        if (obj instanceof bgm) {
            return ((bgm) obj).getScenesData();
        }
        if (obj instanceof bfk) {
            return null;
        }
        return new JSONObject();
    }
}
