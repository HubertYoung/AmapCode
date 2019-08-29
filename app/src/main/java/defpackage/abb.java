package defpackage;

/* renamed from: abb reason: default package */
/* compiled from: BasicHeaderValueParser */
public final class abb {
    public static final abb a = new abb();

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

    private static abg a(String str, String str2) {
        return new abg(str, str2);
    }

    public static abg a(abc abc, abh abh, char[] cArr) {
        boolean z;
        boolean z2;
        String str;
        int i = abh.b;
        int i2 = abh.b;
        int i3 = abh.a;
        while (true) {
            z = true;
            if (i >= i3) {
                break;
            }
            char c = abc.a[i];
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
            str = abc.a(i2, i3);
            z2 = true;
        } else {
            str = abc.a(i2, i);
            i++;
        }
        if (z2) {
            abh.a(i);
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
            char c2 = abc.a[i4];
            if (c2 == '\"' && !z3) {
                z4 = !z4;
            }
            if (!z4 && !z3 && a(c2, cArr)) {
                break;
            }
            z3 = !z3 && z4 && c2 == '\\';
            i4++;
        }
        while (i < i4 && abe.a(abc.a[i])) {
            i++;
        }
        int i5 = i4;
        while (i5 > i) {
            if (!abe.a(abc.a[i5 - 1])) {
                break;
            }
            i5--;
        }
        if (i5 - i >= 2 && abc.a[i] == '\"') {
            if (abc.a[i5 - 1] == '\"') {
                i++;
                i5--;
            }
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i5 > abc.b) {
            throw new IndexOutOfBoundsException();
        } else if (i > i5) {
            throw new IndexOutOfBoundsException();
        } else {
            String str2 = new String(abc.a, i, i5 - i);
            if (z) {
                i4++;
            }
            abh.a(i4);
            return a(str, str2);
        }
    }
}
