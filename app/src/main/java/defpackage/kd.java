package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Map;

/* renamed from: kd reason: default package */
/* compiled from: GDBehaviorTracker */
public final class kd {
    private static ke a = ((ke) ank.a(ke.class));

    public static void a(Application application) {
        a.a(application);
    }

    public static int a(@NonNull String str, @NonNull Object obj) {
        if (a != null) {
            return a.a(str, obj);
        }
        return -2;
    }

    public static int a(@NonNull String str, @NonNull Object obj, @Nullable Map<String, String> map) {
        if (a != null) {
            return a.a(str, obj, map);
        }
        return -2;
    }

    public static int a(@NonNull Object obj) {
        if (a != null) {
            return a.a(obj);
        }
        return -2;
    }

    public static int a(@NonNull String str, @Nullable Map<String, String> map) {
        if (a != null) {
            return a.a(str, map);
        }
        return -2;
    }

    public static int b(@NonNull String str, @Nullable Map<String, String> map) {
        if (a != null) {
            return a.b(str, map);
        }
        return -2;
    }
}
