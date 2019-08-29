package defpackage;

import com.autonavi.minimap.bl.net.IHttpBuffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: ctd reason: default package */
/* compiled from: HttpBufferRepository */
public final class ctd {
    final int a = 65536;
    final ReentrantLock b = new ReentrantLock();
    final Condition c = this.b.newCondition();
    private final List<a> d = new ArrayList(5);

    /* renamed from: ctd$a */
    /* compiled from: HttpBufferRepository */
    public class a implements IHttpBuffer {
        /* access modifiers changed from: private */
        public volatile boolean b;
        private ByteBuffer c;

        private a() {
            this.b = false;
            this.c = ByteBuffer.allocateDirect(ctd.this.a);
        }

        /* synthetic */ a(ctd ctd, byte b2) {
            this();
        }

        public final byte[] getBytes() {
            if (this.b) {
                return this.c.array();
            }
            throw new IllegalStateException("buffer is recycle!");
        }

        public final int getLength() {
            if (this.b) {
                return this.c.limit();
            }
            throw new IllegalStateException("buffer is recycle!");
        }

        public final Object getPtr() {
            return a();
        }

        public final void recycle() {
            this.b = false;
            ctd ctd = ctd.this;
            ctd.b.lock();
            try {
                ctd.c.signal();
            } finally {
                ctd.b.unlock();
            }
        }

        public final ByteBuffer a() {
            if (this.b) {
                return this.c;
            }
            throw new IllegalStateException("buffer is recycle!");
        }
    }

    public ctd() {
        for (int i = 0; i < 5; i++) {
            this.d.add(new a(this, 0));
        }
    }

    public final IHttpBuffer a() throws InterruptedException {
        while (true) {
            for (a next : this.d) {
                if (!next.b) {
                    next.b = true;
                    return next;
                }
            }
            this.b.lockInterruptibly();
            try {
                this.c.await();
            } finally {
                this.b.unlock();
            }
        }
    }
}
