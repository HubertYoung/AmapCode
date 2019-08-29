package defpackage;

import java.io.Closeable;

/* renamed from: bow reason: default package */
/* compiled from: IOUtils */
public final class bow {
    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }
}
