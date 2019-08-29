package defpackage;

import java.net.MalformedURLException;
import java.net.URL;

/* renamed from: cs reason: default package */
/* compiled from: HttpUrl */
public final class cs {
    public String a;
    public String b;
    public String c;
    public int d;
    public String e;
    public String f;
    public volatile boolean g = false;

    private cs() {
    }

    public cs(cs csVar) {
        this.a = csVar.a;
        this.b = csVar.b;
        this.c = csVar.c;
        this.e = csVar.e;
        this.f = csVar.f;
        this.g = csVar.g;
    }

    /* JADX WARNING: Removed duplicated region for block: B:65:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00d9  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0126  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x012c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static defpackage.cs a(java.lang.String r13) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r13)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            java.lang.String r13 = r13.trim()
            cs r0 = new cs
            r0.<init>()
            r0.e = r13
            java.lang.String r2 = "//"
            boolean r2 = r13.startsWith(r2)
            r8 = 0
            if (r2 == 0) goto L_0x0020
            r0.a = r1
            r2 = 0
            goto L_0x0045
        L_0x0020:
            r3 = 1
            r4 = 0
            java.lang.String r5 = "https:"
            r6 = 0
            r7 = 6
            r2 = r13
            boolean r2 = r2.regionMatches(r3, r4, r5, r6, r7)
            if (r2 == 0) goto L_0x0033
            java.lang.String r2 = "https"
            r0.a = r2
            r2 = 6
            goto L_0x0045
        L_0x0033:
            r3 = 1
            r4 = 0
            java.lang.String r5 = "http:"
            r6 = 0
            r7 = 5
            r2 = r13
            boolean r2 = r2.regionMatches(r3, r4, r5, r6, r7)
            if (r2 == 0) goto L_0x0147
            java.lang.String r2 = "http"
            r0.a = r2
            r2 = 5
        L_0x0045:
            int r3 = r13.length()
            int r2 = r2 + 2
            r4 = r2
            r5 = 0
        L_0x004d:
            r6 = 58
            r7 = 35
            r9 = 63
            r10 = 47
            if (r4 >= r3) goto L_0x007b
            char r11 = r13.charAt(r4)
            r12 = 91
            if (r11 != r12) goto L_0x0061
            r5 = 1
            goto L_0x0072
        L_0x0061:
            r12 = 93
            if (r11 != r12) goto L_0x0067
            r5 = 0
            goto L_0x0072
        L_0x0067:
            if (r11 == r10) goto L_0x0075
            if (r11 == r9) goto L_0x0075
            if (r11 == r7) goto L_0x0075
            if (r11 != r6) goto L_0x0072
            if (r5 != 0) goto L_0x0072
            goto L_0x0075
        L_0x0072:
            int r4 = r4 + 1
            goto L_0x004d
        L_0x0075:
            java.lang.String r5 = r13.substring(r2, r4)
            r0.b = r5
        L_0x007b:
            if (r4 != r3) goto L_0x0083
            java.lang.String r2 = r13.substring(r2)
            r0.b = r2
        L_0x0083:
            r2 = 0
        L_0x0084:
            if (r4 >= r3) goto L_0x009d
            char r5 = r13.charAt(r4)
            if (r5 != r6) goto L_0x0091
            if (r2 != 0) goto L_0x0091
            int r2 = r4 + 1
            goto L_0x0098
        L_0x0091:
            if (r5 == r10) goto L_0x009b
            if (r5 == r7) goto L_0x009b
            if (r5 != r9) goto L_0x0098
            goto L_0x009b
        L_0x0098:
            int r4 = r4 + 1
            goto L_0x0084
        L_0x009b:
            r5 = r4
            goto L_0x009e
        L_0x009d:
            r5 = r3
        L_0x009e:
            if (r2 == 0) goto L_0x00b7
            java.lang.String r2 = r13.substring(r2, r5)
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ NumberFormatException -> 0x00b6 }
            r0.d = r2     // Catch:{ NumberFormatException -> 0x00b6 }
            int r2 = r0.d     // Catch:{ NumberFormatException -> 0x00b6 }
            if (r2 <= 0) goto L_0x00b5
            int r2 = r0.d     // Catch:{ NumberFormatException -> 0x00b6 }
            r5 = 65535(0xffff, float:9.1834E-41)
            if (r2 <= r5) goto L_0x00b7
        L_0x00b5:
            return r1
        L_0x00b6:
            return r1
        L_0x00b7:
            if (r4 >= r3) goto L_0x00cf
            char r2 = r13.charAt(r4)
            if (r2 != r10) goto L_0x00c3
            if (r8 != 0) goto L_0x00c3
            r8 = r4
            goto L_0x00c8
        L_0x00c3:
            if (r2 == r9) goto L_0x00cb
            if (r2 != r7) goto L_0x00c8
            goto L_0x00cb
        L_0x00c8:
            int r4 = r4 + 1
            goto L_0x00b7
        L_0x00cb:
            if (r8 == 0) goto L_0x00cf
            r2 = r4
            goto L_0x00d0
        L_0x00cf:
            r2 = r3
        L_0x00d0:
            if (r8 == 0) goto L_0x00d9
            java.lang.String r2 = r13.substring(r8, r2)
            r0.c = r2
            goto L_0x00db
        L_0x00d9:
            r0.c = r1
        L_0x00db:
            java.lang.String r2 = r0.a
            if (r2 != 0) goto L_0x0101
            int r2 = r0.d
            r5 = 80
            if (r2 != r5) goto L_0x00ea
            java.lang.String r1 = "http"
            r0.a = r1
            goto L_0x0101
        L_0x00ea:
            int r2 = r0.d
            r5 = 443(0x1bb, float:6.21E-43)
            if (r2 != r5) goto L_0x00f5
            java.lang.String r1 = "https"
            r0.a = r1
            goto L_0x0101
        L_0x00f5:
            bq r2 = defpackage.bu.a()
            java.lang.String r5 = r0.b
            java.lang.String r1 = r2.a(r5, r1)
            r0.a = r1
        L_0x0101:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = r0.a
            r1.<init>(r2)
            java.lang.String r2 = "://"
            r1.append(r2)
            java.lang.String r2 = r0.b
            r1.append(r2)
            boolean r2 = r0.b()
            if (r2 == 0) goto L_0x0122
            java.lang.String r2 = ":"
            r1.append(r2)
            int r2 = r0.d
            r1.append(r2)
        L_0x0122:
            java.lang.String r2 = r0.c
            if (r2 == 0) goto L_0x012c
            java.lang.String r2 = r0.c
            r1.append(r2)
            goto L_0x0133
        L_0x012c:
            if (r4 == r3) goto L_0x0133
            java.lang.String r2 = "/"
            r1.append(r2)
        L_0x0133:
            java.lang.String r2 = r1.toString()
            r0.f = r2
            java.lang.String r13 = r13.substring(r4)
            r1.append(r13)
            java.lang.String r13 = r1.toString()
            r0.e = r13
            return r0
        L_0x0147:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cs.a(java.lang.String):cs");
    }

    public final URL a() {
        try {
            return new URL(this.e);
        } catch (MalformedURLException unused) {
            return null;
        }
    }

    public final boolean b() {
        return this.d != 0 && (("http".equals(this.a) && this.d != 80) || ("https".equals(this.a) && this.d != 443));
    }

    public final void a(String str, int i) {
        if (str != null) {
            int indexOf = this.e.indexOf("//") + 2;
            while (indexOf < this.e.length() && this.e.charAt(indexOf) != '/') {
                indexOf++;
            }
            boolean b2 = ci.b(str);
            StringBuilder sb = new StringBuilder(this.e.length() + str.length());
            sb.append(this.a);
            sb.append("://");
            if (b2) {
                sb.append('[');
            }
            sb.append(str);
            if (b2) {
                sb.append(']');
            }
            if (i != 0) {
                sb.append(':');
                sb.append(i);
            } else if (this.d != 0) {
                sb.append(':');
                sb.append(this.d);
            }
            sb.append(this.e.substring(indexOf));
            this.e = sb.toString();
        }
    }

    public final String toString() {
        return this.e;
    }
}
