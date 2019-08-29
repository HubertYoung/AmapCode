package org.android.spdy;

import java.util.concurrent.atomic.AtomicLong;

class ProtectedPointer {
    protected ProtectedPointerOnClose a;
    private AtomicLong b = new AtomicLong(1);
    private Object c;

    interface ProtectedPointerOnClose {
        void a(Object obj);
    }

    ProtectedPointer(Object obj) {
        this.c = obj;
    }

    /* access modifiers changed from: 0000 */
    public final void a(ProtectedPointerOnClose protectedPointerOnClose) {
        this.a = protectedPointerOnClose;
    }

    /* access modifiers changed from: 0000 */
    public final boolean a() {
        long j;
        do {
            j = this.b.get();
            if (j == 3) {
                return false;
            }
        } while (!this.b.compareAndSet(j, 16 + j));
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        this.b.addAndGet(-16);
        if (this.b.compareAndSet(2, 3)) {
            if (this.a != null) {
                this.a.a(this.c);
            }
            this.c = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        this.b.incrementAndGet();
        if (this.b.compareAndSet(2, 3)) {
            if (this.a != null) {
                this.a.a(this.c);
            }
            this.c = null;
        }
    }
}
