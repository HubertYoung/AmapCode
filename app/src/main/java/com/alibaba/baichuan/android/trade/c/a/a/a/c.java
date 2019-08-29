package com.alibaba.baichuan.android.trade.c.a.a.a;

import android.net.Uri;
import com.alibaba.baichuan.android.trade.utils.d;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class c extends com.alibaba.baichuan.android.trade.c.a.a.c {
    private boolean d = false;
    private final int e = 10240;
    private final Set f = new HashSet();
    private String[] g = {"ttid"};

    public c(int i, String str, Map map) {
        super(i, str, map);
        g();
    }

    private String a(int i) {
        String str;
        if (this.b == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (Entry entry : this.b.entrySet()) {
            if (1 != i || !this.f.contains(entry.getKey())) {
                List list = (List) entry.getValue();
                int size = list.size();
                boolean z2 = z;
                for (int i2 = 0; i2 < size; i2++) {
                    if (z2) {
                        sb.append("&");
                    } else {
                        z2 = true;
                    }
                    if (!f((String) entry.getKey())) {
                        sb.append(Uri.encode((String) entry.getKey()));
                        sb.append("=");
                        str = Uri.encode((String) list.get(i2));
                    } else {
                        sb.append(Uri.encode((String) entry.getKey()));
                        sb.append("=");
                        str = (String) list.get(i2);
                    }
                    sb.append(str);
                }
                z = z2;
            }
        }
        return sb.toString();
    }

    private boolean f(String str) {
        for (String equals : this.g) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public String b(String str, String str2) {
        List list = (List) this.b.get(str);
        if (list == null) {
            list = new ArrayList(2);
            this.b.put(str, list);
        }
        String str3 = null;
        if (list.size() > 0) {
            str3 = (String) list.set(0, str2);
        } else {
            list.add(str2);
        }
        if (!d.a((Object) str3, (Object) str2)) {
            this.d = true;
        }
        return str3;
    }

    public void c(String str, String str2) {
        if (((List) this.b.get(str)) == null) {
            ArrayList arrayList = new ArrayList(2);
            this.b.put(str, arrayList);
            arrayList.add(str2);
        }
        this.d = true;
    }

    public String[] e(String str) {
        List list = (List) this.b.remove(str);
        if (list == null || list.size() <= 0) {
            return null;
        }
        this.d = true;
        return (String[]) list.toArray(new String[0]);
    }

    public void g() {
        this.f.add("ybhpss");
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0067 A[EDGE_INSN: B:25:0x0067->B:22:0x0067 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void h() {
        /*
            r6 = this;
            boolean r0 = r6.d
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 0
            r1 = 0
        L_0x0007:
            java.lang.String r2 = r6.d()
            java.lang.String r3 = "?"
            int r3 = r2.indexOf(r3)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r5 = -1
            if (r3 == r5) goto L_0x0021
            java.lang.String r2 = r2.substring(r0, r3)
        L_0x001d:
            r4.append(r2)
            goto L_0x0030
        L_0x0021:
            java.lang.String r3 = "#"
            int r3 = r2.indexOf(r3)
            if (r3 == r5) goto L_0x001d
            java.lang.CharSequence r2 = r2.subSequence(r0, r3)
            r4.append(r2)
        L_0x0030:
            java.lang.String r2 = r6.a(r1)
            if (r2 == 0) goto L_0x003e
            java.lang.String r3 = "?"
            r4.append(r3)
            r4.append(r2)
        L_0x003e:
            java.lang.String r2 = r6.c
            if (r2 == 0) goto L_0x004c
            java.lang.String r2 = "#"
            r4.append(r2)
            java.lang.String r2 = r6.c
            r4.append(r2)
        L_0x004c:
            java.lang.String r2 = r4.toString()
            super.d(r2)
            java.lang.String r2 = r6.d()
            int r2 = r2.length()
            r3 = 10240(0x2800, float:1.4349E-41)
            if (r2 <= r3) goto L_0x0067
            int r2 = r1 + 1
            r3 = 1
            if (r1 != r3) goto L_0x0065
            goto L_0x0067
        L_0x0065:
            r1 = r2
            goto L_0x0007
        L_0x0067:
            r6.d = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.trade.c.a.a.a.c.h():void");
    }
}
