package defpackage;

/* renamed from: abh reason: default package */
/* compiled from: ParserCursor */
public final class abh {
    public final int a;
    public int b;
    private final int c;

    public abh(int i) {
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
        abc abc = new abc(16);
        abc.a('[');
        abc.a(Integer.toString(this.c));
        abc.a('>');
        abc.a(Integer.toString(this.b));
        abc.a('>');
        abc.a(Integer.toString(this.a));
        abc.a(']');
        return abc.toString();
    }
}
