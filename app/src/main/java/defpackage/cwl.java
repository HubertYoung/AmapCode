package defpackage;

import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;

/* renamed from: cwl reason: default package */
/* compiled from: TelescopeLog */
public final class cwl {
    public static int a = 1;

    public static <T> void a(String str, T... tArr) {
        if (a >= 5) {
            StringBuilder sb = new StringBuilder("[");
            sb.append(str);
            sb.append("] ");
            sb.append(f(str, tArr));
        }
    }

    public static <T> void b(String str, T... tArr) {
        if (a >= 3) {
            StringBuilder sb = new StringBuilder("[");
            sb.append(str);
            sb.append("] ");
            sb.append(f(str, tArr));
        }
    }

    public static <T> void c(String str, T... tArr) {
        if (a >= 4) {
            StringBuilder sb = new StringBuilder("[");
            sb.append(str);
            sb.append("] ");
            sb.append(f(str, tArr));
        }
    }

    public static <T> void d(String str, T... tArr) {
        if (a >= 2) {
            StringBuilder sb = new StringBuilder("[");
            sb.append(str);
            sb.append("] ");
            sb.append(f(str, tArr));
        }
    }

    public static <T> void e(String str, T... tArr) {
        if (a > 0) {
            StringBuilder sb = new StringBuilder("[");
            sb.append(str);
            sb.append("] ");
            sb.append(f(str, tArr));
        }
    }

    public static void a(String str, String str2) {
        if (a > 0) {
            StringBuilder sb = new StringBuilder("[");
            sb.append(str);
            sb.append("] ");
            sb.append(str2);
        }
    }

    private static <T> String f(String str, T... tArr) {
        if (tArr == null) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        if (tArr.length > 0) {
            sb.append(tArr[0]);
            for (int i = 1; i < tArr.length; i++) {
                sb.append(MergeUtil.SEPARATOR_KV);
                sb.append(tArr[i]);
            }
        }
        return sb.toString();
    }
}
