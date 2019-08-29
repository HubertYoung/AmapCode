package defpackage;

import java.io.Closeable;

/* renamed from: enr reason: default package */
/* compiled from: IOUtil */
public final class enr {
    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
