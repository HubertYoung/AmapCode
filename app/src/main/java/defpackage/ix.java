package defpackage;

/* renamed from: ix reason: default package */
/* compiled from: CharArrayBuffer */
public final class ix {
    char[] a;
    int b;

    public ix(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Buffer capacity may not be negative");
        }
        this.a = new char[i];
    }

    private void a(int i) {
        char[] cArr = new char[Math.max(this.a.length << 1, i)];
        System.arraycopy(this.a, 0, cArr, 0, this.b);
        this.a = cArr;
    }

    public final void a(String str) {
        if (str == null) {
            str = "null";
        }
        int length = str.length();
        int i = this.b + length;
        if (i > this.a.length) {
            a(i);
        }
        str.getChars(0, length, this.a, this.b);
        this.b = i;
    }

    public final void a(char c) {
        int i = this.b + 1;
        if (i > this.a.length) {
            a(i);
        }
        this.a[this.b] = c;
        this.b = i;
    }

    public final String a(int i, int i2) {
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i2 > this.b) {
            throw new IndexOutOfBoundsException();
        } else if (i > i2) {
            throw new IndexOutOfBoundsException();
        } else {
            while (i < i2 && iy.a(this.a[i])) {
                i++;
            }
            while (i2 > i && iy.a(this.a[i2 - 1])) {
                i2--;
            }
            return new String(this.a, i, i2 - i);
        }
    }

    public final String toString() {
        return new String(this.a, 0, this.b);
    }
}
