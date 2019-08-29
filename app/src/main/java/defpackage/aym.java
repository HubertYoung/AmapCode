package defpackage;

import com.alipay.mobile.beehive.video.views.OriVideoPreviewCon;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: aym reason: default package */
/* compiled from: CommuteTimeInfo */
public final class aym {
    public a a;
    public a b;
    public a c;
    public a d;

    /* renamed from: aym$a */
    /* compiled from: CommuteTimeInfo */
    public static class a {
        Date a;
        Date b;

        /* access modifiers changed from: 0000 */
        public final boolean a() {
            return azk.a(azk.c(), this.a, this.b);
        }

        public final String toString() {
            String str;
            StringBuilder sb = new StringBuilder("（start:");
            sb.append(this.a == null ? "null" : this.a.toString());
            sb.append(",finish:");
            if (this.b == null) {
                str = "null";
            } else {
                str = this.b.toString();
            }
            sb.append(str);
            sb.append(")");
            return sb.toString();
        }

        public static a a(String str, String str2) {
            a aVar = new a();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
            aVar.a = azk.a(str, simpleDateFormat);
            aVar.b = azk.a(str2, simpleDateFormat);
            return aVar;
        }
    }

    /* renamed from: aym$b */
    /* compiled from: CommuteTimeInfo */
    public static class b {
        public static aym a;

        static {
            aym aym = new aym(0);
            a = aym;
            aym.a = a.a("06:00", "10:00");
            a.b = a.a("15:30", "23:59");
            a.c = a.a("10:00", "15:30");
            a.d = a.a(OriVideoPreviewCon.ZERO_DURATION, "06:00");
        }
    }

    /* synthetic */ aym(byte b2) {
        this();
    }

    private aym() {
    }

    public static boolean a(a aVar) {
        if (aVar == null) {
            return false;
        }
        return aVar.a();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("onDutyTimeInfo:");
        sb.append(this.a.toString());
        sb.append("\n,offDutyTimeInfo:");
        sb.append(this.b.toString());
        sb.append("\n,onDutyNoCommuteTimeInfo:");
        sb.append(this.c.toString());
        sb.append("\n,offDutyNoCommuteTimeInfo:");
        sb.append(this.d.toString());
        return sb.toString();
    }
}
