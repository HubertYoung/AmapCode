package defpackage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/* renamed from: ahw reason: default package */
/* compiled from: CalendarUtil */
public final class ahw {
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0031 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long a(java.lang.String r5, java.lang.String r6) {
        /*
            r0 = 0
            if (r5 == 0) goto L_0x0043
            java.lang.String r2 = ""
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x000d
            goto L_0x0043
        L_0x000d:
            if (r6 == 0) goto L_0x0042
            java.lang.String r2 = ""
            boolean r2 = r6.equals(r2)
            if (r2 == 0) goto L_0x0018
            goto L_0x0042
        L_0x0018:
            java.text.SimpleDateFormat r2 = new java.text.SimpleDateFormat
            java.lang.String r3 = "yyyy-MM-dd"
            java.util.Locale r4 = java.util.Locale.getDefault()
            r2.<init>(r3, r4)
            r3 = 0
            java.util.Date r5 = r2.parse(r5)     // Catch:{ Exception -> 0x002d }
            java.util.Date r6 = r2.parse(r6)     // Catch:{ Exception -> 0x002e }
            goto L_0x002f
        L_0x002d:
            r5 = r3
        L_0x002e:
            r6 = r3
        L_0x002f:
            if (r5 == 0) goto L_0x0041
            if (r6 == 0) goto L_0x0041
            long r0 = r6.getTime()
            long r5 = r5.getTime()
            long r0 = r0 - r5
            r5 = 86400000(0x5265c00, double:4.2687272E-316)
            long r0 = r0 / r5
            return r0
        L_0x0041:
            return r0
        L_0x0042:
            return r0
        L_0x0043:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ahw.a(java.lang.String, java.lang.String):long");
    }

    public static String a(Date date) {
        StringBuilder sb = new StringBuilder();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        sb.append(instance.get(2) + 1);
        sb.append(".");
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date);
        sb.append(instance2.get(5));
        return sb.toString();
    }

    public static String a(Date date, String str) {
        return new SimpleDateFormat(str).format(date);
    }
}
