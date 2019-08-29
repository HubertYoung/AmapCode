package defpackage;

import java.util.Hashtable;

@Deprecated
/* renamed from: le reason: default package */
/* compiled from: DBRecordItem */
public final class le {
    public int a;
    Hashtable<String, ld> b = new Hashtable<>();

    public final void a(String str, int i) {
        ld ldVar = new ld(0);
        ldVar.b = str;
        ldVar.c = Integer.valueOf(i);
        this.b.put(str, ldVar);
    }

    public final void a(String str, String str2) {
        ld ldVar = new ld(1);
        ldVar.b = str;
        ldVar.c = str2;
        this.b.put(str, ldVar);
    }

    public final void a(String str, double d) {
        ld ldVar = new ld(3);
        ldVar.b = str;
        ldVar.c = Double.valueOf(d);
        this.b.put(str, ldVar);
    }

    public final void a(String str, byte[] bArr, int i, int i2) {
        if (bArr != null) {
            ld ldVar = new ld(2);
            ldVar.b = str;
            ldVar.c = new byte[i2];
            System.arraycopy(bArr, i, ldVar.c, 0, i2);
            this.b.put(str, ldVar);
        }
    }

    public final int a(String str) {
        ld ldVar = this.b.get(str);
        if (ldVar == null) {
            return -1;
        }
        return ((Integer) ldVar.c).intValue();
    }

    public final String b(String str, String str2) {
        ld ldVar = this.b.get(str);
        if (ldVar == null) {
            return str2;
        }
        return (String) ldVar.c;
    }
}
