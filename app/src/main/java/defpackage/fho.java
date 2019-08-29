package defpackage;

import android.annotation.SuppressLint;
import android.content.Context;

/* renamed from: fho reason: default package */
/* compiled from: LibraryLoader */
public final class fho {
    @SuppressLint({"StaticFieldLeak"})
    private static Context a;

    public static Context a() {
        if (a == null) {
            try {
                a = (Context) Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication", new Class[0]).invoke(null, new Object[0]);
            } catch (Exception e) {
                throw new IllegalStateException("LibraryLoader not initialized. Call LibraryLoader.initialize() before using library classes.", e);
            }
        }
        return a;
    }
}
