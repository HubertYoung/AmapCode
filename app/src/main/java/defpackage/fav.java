package defpackage;

/* renamed from: fav reason: default package */
/* compiled from: OperateUtil */
public final class fav {
    public static int a(exd exd) {
        fba b = fba.b();
        int i = exd.f;
        long currentTimeMillis = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("com.vivo.push_preferences.operate.");
        sb.append(i);
        sb.append("OPERATE_COUNT");
        int b2 = b.b(sb.toString());
        StringBuilder sb2 = new StringBuilder("com.vivo.push_preferences.operate.");
        sb2.append(i);
        sb2.append("START_TIME");
        long b3 = currentTimeMillis - b.b(sb2.toString(), 0);
        if (b3 > 86400000 || b3 < 0) {
            StringBuilder sb3 = new StringBuilder("com.vivo.push_preferences.operate.");
            sb3.append(i);
            sb3.append("START_TIME");
            b.a(sb3.toString(), System.currentTimeMillis());
            StringBuilder sb4 = new StringBuilder("com.vivo.push_preferences.operate.");
            sb4.append(i);
            sb4.append("OPERATE_COUNT");
            b.a(sb4.toString(), 1);
        } else if (b2 >= exd.d) {
            return 1001;
        } else {
            StringBuilder sb5 = new StringBuilder("com.vivo.push_preferences.operate.");
            sb5.append(i);
            sb5.append("OPERATE_COUNT");
            b.a(sb5.toString(), b2 + 1);
        }
        return 0;
    }
}
