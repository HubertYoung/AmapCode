package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

/* renamed from: cl reason: default package */
/* compiled from: ALog */
public final class cl {
    public static b a = null;
    private static volatile a b = null;
    private static Object c = MergeUtil.SEPARATOR_KV;
    private static boolean d = true;
    private static boolean e = true;

    /* renamed from: cl$a */
    /* compiled from: ALog */
    public interface a {
        void a(String str, String str2);

        void a(String str, String str2, Throwable th);

        boolean a();

        boolean a(int i);

        void b(String str, String str2);

        void c(String str, String str2);

        void d(String str, String str2);

        void e(String str, String str2);
    }

    /* renamed from: cl$b */
    /* compiled from: ALog */
    public static class b implements a {
        int a = 1;

        public final void a(String str, String str2) {
        }

        public final void a(String str, String str2, Throwable th) {
        }

        public final boolean a() {
            return true;
        }

        public final void b(String str, String str2) {
        }

        public final void c(String str, String str2) {
        }

        public final void d(String str, String str2) {
        }

        public final void e(String str, String str2) {
        }

        public final boolean a(int i) {
            return i >= this.a;
        }
    }

    static {
        b bVar = new b();
        a = bVar;
        b = bVar;
    }

    public static void a(a aVar) {
        if ((e || !aVar.getClass().getSimpleName().toLowerCase().contains("tlog")) && aVar.a()) {
            b = aVar;
        }
    }

    public static void a(boolean z) {
        d = z;
    }

    public static boolean a(int i) {
        if (d && b != null) {
            return b.a(i);
        }
        return false;
    }

    public static void a(String str, String str2, String str3, Object... objArr) {
        if (a(1) && b != null) {
            b.a(str, a(str2, str3, objArr));
        }
    }

    public static void b(String str, String str2, String str3, Object... objArr) {
        if (a(2) && b != null) {
            b.b(str, a(str2, str3, objArr));
        }
    }

    public static void c(String str, String str2, String str3, Object... objArr) {
        if (a(3) && b != null) {
            b.c(str, a(str2, str3, objArr));
        }
    }

    public static void a(String str, String str2, String str3, Throwable th, Object... objArr) {
        if (a(3) && b != null) {
            b.a(str, a(str2, str3, objArr), th);
        }
    }

    public static void d(String str, String str2, String str3, Object... objArr) {
        if (a(4) && b != null) {
            b.d(str, a(str2, str3, objArr));
        }
    }

    public static void e(String str, String str2, String str3, Object... objArr) {
        if (a(4) && b != null) {
            b.e(str, a(str2, str3, objArr));
        }
    }

    private static String a(String str, String str2, Object... objArr) {
        if (str == null && str2 == null && objArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(64);
        if (!TextUtils.isEmpty(str2)) {
            sb.append(c);
            sb.append("[seq:");
            sb.append(str2);
            sb.append("]");
        }
        if (str != null) {
            sb.append(Token.SEPARATOR);
            sb.append(str);
        }
        if (objArr != null) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                if (i2 >= objArr.length) {
                    break;
                }
                sb.append(Token.SEPARATOR);
                sb.append(objArr[i] != null ? objArr[i] : "");
                sb.append(":");
                sb.append(objArr[i2] != null ? objArr[i2] : "");
                i += 2;
            }
            if (i < objArr.length) {
                sb.append(Token.SEPARATOR);
                sb.append(objArr[i]);
            }
        }
        return sb.toString();
    }

    @Deprecated
    public static void a() {
        e = false;
        b = a;
    }
}
