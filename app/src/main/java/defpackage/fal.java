package defpackage;

import android.os.Looper;

/* renamed from: fal reason: default package */
/* compiled from: DebugUtil */
public final class fal {
    public static void a(String str) {
        if (fat.a() && Looper.myLooper() == Looper.getMainLooper()) {
            StringBuilder sb = new StringBuilder("Operation: ");
            sb.append(str);
            sb.append(" in main thread!");
            new Throwable();
        }
    }
}
