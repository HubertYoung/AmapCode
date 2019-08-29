package defpackage;

import com.autonavi.minimap.bl.net.IHttpBuffer;

/* renamed from: ctf reason: default package */
/* compiled from: SimpleBuffer */
public final class ctf implements IHttpBuffer {
    private final byte[] a;
    private final int b;

    public final Object getPtr() {
        return null;
    }

    public final void recycle() {
    }

    public ctf(byte[] bArr) {
        this.a = bArr;
        this.b = this.a == null ? 0 : this.a.length;
    }

    public final byte[] getBytes() {
        return this.a;
    }

    public final int getLength() {
        return this.b;
    }
}
