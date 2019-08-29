package android.support.v7.widget;

class PositionMap<E> implements Cloneable {
    private static final Object a = new Object();
    private boolean b;
    private int[] c;
    private Object[] d;
    private int e;

    static class ContainerHelpers {
        static final boolean[] a = new boolean[0];
        static final int[] b = new int[0];
        static final long[] c = new long[0];
        static final Object[] d = new Object[0];

        ContainerHelpers() {
        }
    }

    public PositionMap() {
        this(0);
    }

    private PositionMap(byte b2) {
        this.b = false;
        this.c = new int[13];
        this.d = new Object[13];
        this.e = 0;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public PositionMap<E> clone() {
        try {
            PositionMap<E> positionMap = (PositionMap) super.clone();
            try {
                positionMap.c = (int[]) this.c.clone();
                positionMap.d = (Object[]) this.d.clone();
                return positionMap;
            } catch (CloneNotSupportedException unused) {
                return positionMap;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    private void b() {
        int i = this.e;
        int[] iArr = this.c;
        Object[] objArr = this.d;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != a) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.b = false;
        this.e = i2;
    }

    private int a(int i) {
        if (this.b) {
            b();
        }
        return this.c[i];
    }

    private E b(int i) {
        if (this.b) {
            b();
        }
        return this.d[i];
    }

    public String toString() {
        if (this.b) {
            b();
        }
        if (this.e <= 0) {
            return bny.c;
        }
        StringBuilder sb = new StringBuilder(this.e * 28);
        sb.append('{');
        for (int i = 0; i < this.e; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(a(i));
            sb.append('=');
            Object b2 = b(i);
            if (b2 != this) {
                sb.append(b2);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
