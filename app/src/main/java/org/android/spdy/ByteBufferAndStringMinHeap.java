package org.android.spdy;

/* compiled from: HTTPHeaderPool */
class ByteBufferAndStringMinHeap {
    ByteBufferAndString[] a = new ByteBufferAndString[256];
    int b = 0;

    /* access modifiers changed from: 0000 */
    public final void a(int i, int i2) {
        ByteBufferAndString byteBufferAndString = this.a[i];
        ByteBufferAndString[] byteBufferAndStringArr = this.a;
        byteBufferAndStringArr[i] = byteBufferAndStringArr[i2];
        this.a[i2] = byteBufferAndString;
        this.a[i2].a = i2;
        this.a[i].a = i;
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        int i = 1;
        int i2 = 0;
        int i3 = 2;
        int i4 = 0;
        while (i < this.b) {
            if (this.a[i].c >= this.a[i2].c) {
                i = i2;
            }
            if (i3 < this.b && this.a[i3].c < this.a[i].c) {
                i = i3;
            }
            if (i != i4) {
                a(i, i4);
                i3 = (i + 1) << 1;
                i4 = i;
                i = i3 - 1;
                i2 = i4;
            } else {
                return;
            }
        }
    }
}
