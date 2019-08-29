package defpackage;

import android.support.v4.os.TraceCompat;

/* renamed from: eu reason: default package */
/* compiled from: L */
public final class eu {
    private static boolean a = false;
    private static String[] b;
    private static long[] c;
    private static int d;
    private static int e;

    public static void a(String str) {
        if (a) {
            if (d == 20) {
                e++;
                return;
            }
            b[d] = str;
            c[d] = System.nanoTime();
            TraceCompat.beginSection(str);
            d++;
        }
    }

    public static float b(String str) {
        if (e > 0) {
            e--;
            return 0.0f;
        } else if (!a) {
            return 0.0f;
        } else {
            int i = d - 1;
            d = i;
            if (i == -1) {
                throw new IllegalStateException("Can't end trace section. There are none.");
            } else if (!str.equals(b[d])) {
                StringBuilder sb = new StringBuilder("Unbalanced trace call ");
                sb.append(str);
                sb.append(". Expected ");
                sb.append(b[d]);
                sb.append(".");
                throw new IllegalStateException(sb.toString());
            } else {
                TraceCompat.endSection();
                return ((float) (System.nanoTime() - c[d])) / 1000000.0f;
            }
        }
    }
}
