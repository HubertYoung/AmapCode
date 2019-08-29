package defpackage;

/* renamed from: iw reason: default package */
/* compiled from: BasicHeaderValueParser */
public final class iw {
    public static final iw a = new iw();

    private static boolean a(char c, char[] cArr) {
        if (cArr != null) {
            for (char c2 : cArr) {
                if (c == c2) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ja a(String str, String str2) {
        return new ja(str, str2);
    }

    public static ja a(ix ixVar, jb jbVar, char[] cArr) {
        boolean z;
        boolean z2;
        String str;
        int i = jbVar.b;
        int i2 = jbVar.b;
        int i3 = jbVar.a;
        while (true) {
            z = true;
            if (i >= i3) {
                break;
            }
            char c = ixVar.a[i];
            if (c == '=') {
                break;
            } else if (a(c, cArr)) {
                z2 = true;
                break;
            } else {
                i++;
            }
        }
        z2 = false;
        if (i == i3) {
            str = ixVar.a(i2, i3);
            z2 = true;
        } else {
            str = ixVar.a(i2, i);
            i++;
        }
        if (z2) {
            jbVar.a(i);
            return a(str, (String) null);
        }
        int i4 = i;
        boolean z3 = false;
        boolean z4 = false;
        while (true) {
            if (i4 >= i3) {
                z = z2;
                break;
            }
            char c2 = ixVar.a[i4];
            if (c2 == '\"' && !z3) {
                z4 = !z4;
            }
            if (!z4 && !z3 && a(c2, cArr)) {
                break;
            }
            z3 = !z3 && z4 && c2 == '\\';
            i4++;
        }
        while (i < i4 && iy.a(ixVar.a[i])) {
            i++;
        }
        int i5 = i4;
        while (i5 > i) {
            if (!iy.a(ixVar.a[i5 - 1])) {
                break;
            }
            i5--;
        }
        if (i5 - i >= 2 && ixVar.a[i] == '\"') {
            if (ixVar.a[i5 - 1] == '\"') {
                i++;
                i5--;
            }
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i5 > ixVar.b) {
            throw new IndexOutOfBoundsException();
        } else if (i > i5) {
            throw new IndexOutOfBoundsException();
        } else {
            String str2 = new String(ixVar.a, i, i5 - i);
            if (z) {
                i4++;
            }
            jbVar.a(i4);
            return a(str, str2);
        }
    }
}
