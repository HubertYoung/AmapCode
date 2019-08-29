package defpackage;

/* renamed from: jb reason: default package */
/* compiled from: ParserCursor */
public final class jb {
    final int a;
    int b;
    private final int c;

    public jb(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
        }
        this.c = 0;
        this.a = i;
        this.b = 0;
    }

    public final void a(int i) {
        if (i < this.c) {
            throw new IndexOutOfBoundsException();
        } else if (i > this.a) {
            throw new IndexOutOfBoundsException();
        } else {
            this.b = i;
        }
    }

    public final String toString() {
        ix ixVar = new ix(16);
        ixVar.a('[');
        ixVar.a(Integer.toString(this.c));
        ixVar.a('>');
        ixVar.a(Integer.toString(this.b));
        ixVar.a('>');
        ixVar.a(Integer.toString(this.a));
        ixVar.a(']');
        return ixVar.toString();
    }
}
