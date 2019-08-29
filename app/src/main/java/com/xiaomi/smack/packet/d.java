package com.xiaomi.smack.packet;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class d {
    protected static final String a = Locale.getDefault().getLanguage().toLowerCase();
    public static final DateFormat b;
    private static String c;
    private static String d;
    private static long e;
    private String f = c;
    private String g = null;
    private String h = null;
    private String i = null;
    private String j = null;
    private String k = null;
    private List<a> l = new CopyOnWriteArrayList();
    private final Map<String, Object> m = new HashMap();
    private h n = null;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        b = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        StringBuilder sb = new StringBuilder();
        sb.append(com.xiaomi.smack.util.d.a(5));
        sb.append("-");
        d = sb.toString();
    }

    public d() {
    }

    public d(Bundle bundle) {
        this.h = bundle.getString("ext_to");
        this.i = bundle.getString("ext_from");
        this.j = bundle.getString("ext_chid");
        this.g = bundle.getString("ext_pkt_id");
        Parcelable[] parcelableArray = bundle.getParcelableArray("ext_exts");
        if (parcelableArray != null) {
            this.l = new ArrayList(parcelableArray.length);
            for (Parcelable parcelable : parcelableArray) {
                a a2 = a.a((Bundle) parcelable);
                if (a2 != null) {
                    this.l.add(a2);
                }
            }
        }
        Bundle bundle2 = bundle.getBundle("ext_ERROR");
        if (bundle2 != null) {
            this.n = new h(bundle2);
        }
    }

    public static synchronized String j() {
        String sb;
        synchronized (d.class) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(d);
            long j2 = e;
            e = 1 + j2;
            sb2.append(Long.toString(j2));
            sb = sb2.toString();
        }
        return sb;
    }

    public static String u() {
        return a;
    }

    public void a(a aVar) {
        this.l.add(aVar);
    }

    public void a(h hVar) {
        this.n = hVar;
    }

    public Bundle b() {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(this.f)) {
            bundle.putString("ext_ns", this.f);
        }
        if (!TextUtils.isEmpty(this.i)) {
            bundle.putString("ext_from", this.i);
        }
        if (!TextUtils.isEmpty(this.h)) {
            bundle.putString("ext_to", this.h);
        }
        if (!TextUtils.isEmpty(this.g)) {
            bundle.putString("ext_pkt_id", this.g);
        }
        if (!TextUtils.isEmpty(this.j)) {
            bundle.putString("ext_chid", this.j);
        }
        if (this.n != null) {
            bundle.putBundle("ext_ERROR", this.n.a());
        }
        if (this.l != null) {
            Bundle[] bundleArr = new Bundle[this.l.size()];
            int i2 = 0;
            for (a e2 : this.l) {
                Bundle e3 = e2.e();
                if (e3 != null) {
                    bundleArr[i2] = e3;
                    i2++;
                }
            }
            bundle.putParcelableArray("ext_exts", bundleArr);
        }
        return bundle;
    }

    public a b(String str, String str2) {
        for (a next : this.l) {
            if ((str2 == null || str2.equals(next.b())) && str.equals(next.a())) {
                return next;
            }
        }
        return null;
    }

    public abstract String c();

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        d dVar = (d) obj;
        if (this.n != null) {
            if (!this.n.equals(dVar.n)) {
                return false;
            }
        } else if (dVar.n != null) {
            return false;
        }
        if (this.i != null) {
            if (!this.i.equals(dVar.i)) {
                return false;
            }
        } else if (dVar.i != null) {
            return false;
        }
        if (!this.l.equals(dVar.l)) {
            return false;
        }
        if (this.g != null) {
            if (!this.g.equals(dVar.g)) {
                return false;
            }
        } else if (dVar.g != null) {
            return false;
        }
        if (this.j != null) {
            if (!this.j.equals(dVar.j)) {
                return false;
            }
        } else if (dVar.j != null) {
            return false;
        }
        if (this.m != null) {
            if (!this.m.equals(dVar.m)) {
                return false;
            }
        } else if (dVar.m != null) {
            return false;
        }
        if (this.h != null) {
            if (!this.h.equals(dVar.h)) {
                return false;
            }
        } else if (dVar.h != null) {
            return false;
        }
        if (this.f == null) {
            return dVar.f == null;
        }
        if (!this.f.equals(dVar.f)) {
            return false;
        }
    }

    public int hashCode() {
        int i2 = 0;
        int hashCode = (((((((((((((this.f != null ? this.f.hashCode() : 0) * 31) + (this.g != null ? this.g.hashCode() : 0)) * 31) + (this.h != null ? this.h.hashCode() : 0)) * 31) + (this.i != null ? this.i.hashCode() : 0)) * 31) + (this.j != null ? this.j.hashCode() : 0)) * 31) + this.l.hashCode()) * 31) + this.m.hashCode()) * 31;
        if (this.n != null) {
            i2 = this.n.hashCode();
        }
        return hashCode + i2;
    }

    public String k() {
        if ("ID_NOT_AVAILABLE".equals(this.g)) {
            return null;
        }
        if (this.g == null) {
            this.g = j();
        }
        return this.g;
    }

    public void k(String str) {
        this.g = str;
    }

    public String l() {
        return this.j;
    }

    public void l(String str) {
        this.j = str;
    }

    public String m() {
        return this.h;
    }

    public void m(String str) {
        this.h = str;
    }

    public String n() {
        return this.i;
    }

    public void n(String str) {
        this.i = str;
    }

    public String o() {
        return this.k;
    }

    public void o(String str) {
        this.k = str;
    }

    public a p(String str) {
        return b(str, null);
    }

    public h p() {
        return this.n;
    }

    public synchronized Object q(String str) {
        if (this.m == null) {
            return null;
        }
        return this.m.get(str);
    }

    public synchronized Collection<a> q() {
        if (this.l == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList(this.l));
    }

    public synchronized Collection<String> r() {
        if (this.m == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new HashSet(this.m.keySet()));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:64|(2:66|67)|(2:70|71)|72|73) */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x010d, code lost:
        if (r4 == null) goto L_0x0110;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x00f1 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:72:0x0122 */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x010a A[SYNTHETIC, Splitter:B:56:0x010a] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x011a A[SYNTHETIC, Splitter:B:66:0x011a] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x011f A[SYNTHETIC, Splitter:B:70:0x011f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String s() {
        /*
            r6 = this;
            monitor-enter(r6)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x012e }
            r0.<init>()     // Catch:{ all -> 0x012e }
            java.util.Collection r1 = r6.q()     // Catch:{ all -> 0x012e }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x012e }
        L_0x000e:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x012e }
            if (r2 == 0) goto L_0x0022
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x012e }
            com.xiaomi.smack.packet.e r2 = (com.xiaomi.smack.packet.e) r2     // Catch:{ all -> 0x012e }
            java.lang.String r2 = r2.d()     // Catch:{ all -> 0x012e }
            r0.append(r2)     // Catch:{ all -> 0x012e }
            goto L_0x000e
        L_0x0022:
            java.util.Map<java.lang.String, java.lang.Object> r1 = r6.m     // Catch:{ all -> 0x012e }
            if (r1 == 0) goto L_0x0128
            java.util.Map<java.lang.String, java.lang.Object> r1 = r6.m     // Catch:{ all -> 0x012e }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x012e }
            if (r1 != 0) goto L_0x0128
            java.lang.String r1 = "<properties xmlns=\"http://www.jivesoftware.com/xmlns/xmpp/properties\">"
            r0.append(r1)     // Catch:{ all -> 0x012e }
            java.util.Collection r1 = r6.r()     // Catch:{ all -> 0x012e }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x012e }
        L_0x003b:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x012e }
            if (r2 == 0) goto L_0x0123
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x012e }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x012e }
            java.lang.Object r3 = r6.q(r2)     // Catch:{ all -> 0x012e }
            java.lang.String r4 = "<property>"
            r0.append(r4)     // Catch:{ all -> 0x012e }
            java.lang.String r4 = "<name>"
            r0.append(r4)     // Catch:{ all -> 0x012e }
            java.lang.String r2 = com.xiaomi.smack.util.d.a(r2)     // Catch:{ all -> 0x012e }
            r0.append(r2)     // Catch:{ all -> 0x012e }
            java.lang.String r2 = "</name>"
            r0.append(r2)     // Catch:{ all -> 0x012e }
            java.lang.String r2 = "<value type=\""
            r0.append(r2)     // Catch:{ all -> 0x012e }
            boolean r2 = r3 instanceof java.lang.Integer     // Catch:{ all -> 0x012e }
            if (r2 == 0) goto L_0x0079
            java.lang.String r2 = "integer\">"
            r0.append(r2)     // Catch:{ all -> 0x012e }
            r0.append(r3)     // Catch:{ all -> 0x012e }
            java.lang.String r2 = "</value>"
        L_0x0074:
            r0.append(r2)     // Catch:{ all -> 0x012e }
            goto L_0x0110
        L_0x0079:
            boolean r2 = r3 instanceof java.lang.Long     // Catch:{ all -> 0x012e }
            if (r2 == 0) goto L_0x0088
            java.lang.String r2 = "long\">"
            r0.append(r2)     // Catch:{ all -> 0x012e }
            r0.append(r3)     // Catch:{ all -> 0x012e }
            java.lang.String r2 = "</value>"
            goto L_0x0074
        L_0x0088:
            boolean r2 = r3 instanceof java.lang.Float     // Catch:{ all -> 0x012e }
            if (r2 == 0) goto L_0x0097
            java.lang.String r2 = "float\">"
            r0.append(r2)     // Catch:{ all -> 0x012e }
            r0.append(r3)     // Catch:{ all -> 0x012e }
            java.lang.String r2 = "</value>"
            goto L_0x0074
        L_0x0097:
            boolean r2 = r3 instanceof java.lang.Double     // Catch:{ all -> 0x012e }
            if (r2 == 0) goto L_0x00a6
            java.lang.String r2 = "double\">"
            r0.append(r2)     // Catch:{ all -> 0x012e }
            r0.append(r3)     // Catch:{ all -> 0x012e }
            java.lang.String r2 = "</value>"
            goto L_0x0074
        L_0x00a6:
            boolean r2 = r3 instanceof java.lang.Boolean     // Catch:{ all -> 0x012e }
            if (r2 == 0) goto L_0x00b5
            java.lang.String r2 = "boolean\">"
            r0.append(r2)     // Catch:{ all -> 0x012e }
            r0.append(r3)     // Catch:{ all -> 0x012e }
            java.lang.String r2 = "</value>"
            goto L_0x0074
        L_0x00b5:
            boolean r2 = r3 instanceof java.lang.String     // Catch:{ all -> 0x012e }
            if (r2 == 0) goto L_0x00cb
            java.lang.String r2 = "string\">"
            r0.append(r2)     // Catch:{ all -> 0x012e }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x012e }
            java.lang.String r2 = com.xiaomi.smack.util.d.a(r3)     // Catch:{ all -> 0x012e }
            r0.append(r2)     // Catch:{ all -> 0x012e }
            java.lang.String r2 = "</value>"
            goto L_0x0074
        L_0x00cb:
            r2 = 0
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0101, all -> 0x00fd }
            r4.<init>()     // Catch:{ Exception -> 0x0101, all -> 0x00fd }
            java.io.ObjectOutputStream r5 = new java.io.ObjectOutputStream     // Catch:{ Exception -> 0x00fa, all -> 0x00f7 }
            r5.<init>(r4)     // Catch:{ Exception -> 0x00fa, all -> 0x00f7 }
            r5.writeObject(r3)     // Catch:{ Exception -> 0x00f5 }
            java.lang.String r2 = "java-object\">"
            r0.append(r2)     // Catch:{ Exception -> 0x00f5 }
            byte[] r2 = r4.toByteArray()     // Catch:{ Exception -> 0x00f5 }
            java.lang.String r2 = com.xiaomi.smack.util.d.a(r2)     // Catch:{ Exception -> 0x00f5 }
            r0.append(r2)     // Catch:{ Exception -> 0x00f5 }
            java.lang.String r2 = "</value>"
            r0.append(r2)     // Catch:{ Exception -> 0x00f5 }
            r5.close()     // Catch:{ Exception -> 0x00f1 }
        L_0x00f1:
            r4.close()     // Catch:{ Exception -> 0x0110 }
            goto L_0x0110
        L_0x00f5:
            r2 = move-exception
            goto L_0x0105
        L_0x00f7:
            r0 = move-exception
            r5 = r2
            goto L_0x0118
        L_0x00fa:
            r3 = move-exception
            r5 = r2
            goto L_0x0104
        L_0x00fd:
            r0 = move-exception
            r4 = r2
            r5 = r4
            goto L_0x0118
        L_0x0101:
            r3 = move-exception
            r4 = r2
            r5 = r4
        L_0x0104:
            r2 = r3
        L_0x0105:
            r2.printStackTrace()     // Catch:{ all -> 0x0117 }
            if (r5 == 0) goto L_0x010d
            r5.close()     // Catch:{ Exception -> 0x010d }
        L_0x010d:
            if (r4 == 0) goto L_0x0110
            goto L_0x00f1
        L_0x0110:
            java.lang.String r2 = "</property>"
            r0.append(r2)     // Catch:{ all -> 0x012e }
            goto L_0x003b
        L_0x0117:
            r0 = move-exception
        L_0x0118:
            if (r5 == 0) goto L_0x011d
            r5.close()     // Catch:{ Exception -> 0x011d }
        L_0x011d:
            if (r4 == 0) goto L_0x0122
            r4.close()     // Catch:{ Exception -> 0x0122 }
        L_0x0122:
            throw r0     // Catch:{ all -> 0x012e }
        L_0x0123:
            java.lang.String r1 = "</properties>"
            r0.append(r1)     // Catch:{ all -> 0x012e }
        L_0x0128:
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x012e }
            monitor-exit(r6)
            return r0
        L_0x012e:
            r0 = move-exception
            monitor-exit(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smack.packet.d.s():java.lang.String");
    }

    public String t() {
        return this.f;
    }
}
