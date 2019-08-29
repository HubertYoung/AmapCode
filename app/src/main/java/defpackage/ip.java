package defpackage;

/* renamed from: ip reason: default package */
/* compiled from: AosContext */
public class ip {
    private static ir a;
    private static iq b;
    private static is c;
    private static iq d = new iq() {
        public final io a(String str, int i) {
            return null;
        }
    };
    private static is e = new is() {
        public final String a() {
            return "";
        }

        public final String a(String str) {
            return str;
        }

        public final String a(byte[] bArr) {
            return "";
        }

        public final String a(String... strArr) {
            return "";
        }

        public final boolean b() {
            return false;
        }

        public final byte[] b(byte[] bArr) {
            return bArr;
        }

        public final String c() {
            return "";
        }

        public final String d() {
            return "";
        }
    };

    public static void a(ir irVar) {
        synchronized (ip.class) {
            if (a == null) {
                a = irVar;
            }
        }
    }

    public static is a() {
        is isVar;
        if (c != null) {
            return c;
        }
        synchronized (ip.class) {
            try {
                is a2 = a == null ? null : a.a();
                c = a2;
                isVar = a2 == null ? e : c;
            }
        }
        return isVar;
    }

    public static iq b() {
        iq iqVar;
        if (b != null) {
            return b;
        }
        synchronized (ip.class) {
            try {
                iq b2 = a == null ? null : a.b();
                b = b2;
                iqVar = b2 == null ? d : b;
            }
        }
        return iqVar;
    }
}
