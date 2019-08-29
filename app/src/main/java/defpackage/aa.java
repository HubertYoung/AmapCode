package defpackage;

/* renamed from: aa reason: default package */
/* compiled from: ByteArray */
public final class aa implements Comparable<aa> {
    public final byte[] a;
    public int b;
    public int c;

    public final /* synthetic */ int compareTo(Object obj) {
        aa aaVar = (aa) obj;
        if (this.b != aaVar.b) {
            return this.b - aaVar.b;
        }
        if (this.a == null) {
            return -1;
        }
        if (aaVar.a == null) {
            return 1;
        }
        return hashCode() - aaVar.hashCode();
    }

    private aa(byte[] bArr, int i) {
        this.a = bArr == null ? new byte[i] : bArr;
        this.b = this.a.length;
        this.c = i;
    }

    public static aa a(int i) {
        return new aa(null, i);
    }

    public static aa a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        if (bArr == null || length < 0 || length > bArr.length) {
            return null;
        }
        return new aa(bArr, length);
    }

    public final void a() {
        if (this.b != 0) {
            a.a.a(this);
        }
    }
}
