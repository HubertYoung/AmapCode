package defpackage;

import com.amap.bundle.logs.AMapLog;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: abi reason: default package */
/* compiled from: CookieStore */
public interface abi {

    /* renamed from: abi$a */
    /* compiled from: CookieStore */
    public static class a {
        String a;
        public String b;
        String c;
        String d;
        Date e;
        private final SimpleDateFormat f = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);

        public final boolean a() {
            return this.e != null && this.e.before(new Date());
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            if (!(this.a == null || this.b == null)) {
                sb.append(this.a);
                sb.append("=");
                sb.append(this.b);
            }
            if (this.c != null) {
                sb.append(";domain=");
                sb.append(this.c);
            }
            if (this.d != null) {
                sb.append(";path=");
                sb.append(this.d);
            }
            if (this.e != null) {
                try {
                    sb.append(";expires=");
                    sb.append(this.f.format(this.e));
                } catch (AssertionError e2) {
                    StringBuilder sb2 = new StringBuilder("time format error:");
                    sb2.append(e2.getLocalizedMessage());
                    AMapLog.error("paas.network", "CookieStore", sb2.toString());
                }
            }
            return sb.toString();
        }

        public final String b() {
            if (this.a == null || this.b == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append("=");
            sb.append(this.b);
            sb.append(";");
            return sb.toString();
        }
    }
}
