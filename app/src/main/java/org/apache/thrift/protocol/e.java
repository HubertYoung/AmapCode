package org.apache.thrift.protocol;

import java.nio.ByteBuffer;
import org.apache.thrift.transport.d;

public abstract class e {
    protected d e;

    protected e(d dVar) {
        this.e = dVar;
    }

    public abstract void a();

    public abstract void a(byte b);

    public abstract void a(double d);

    public abstract void a(int i);

    public abstract void a(long j);

    public abstract void a(String str);

    public abstract void a(ByteBuffer byteBuffer);

    public abstract void a(b bVar);

    public abstract void a(c cVar);

    public abstract void a(d dVar);

    public abstract void a(i iVar);

    public abstract void a(short s);

    public abstract void a(boolean z);

    public abstract b b();

    public abstract d c();

    public abstract c d();

    public abstract i e();

    public abstract boolean f();

    public abstract byte g();

    public abstract short h();

    public abstract int i();

    public abstract long j();

    public abstract double k();

    public abstract String l();

    public abstract ByteBuffer m();
}
