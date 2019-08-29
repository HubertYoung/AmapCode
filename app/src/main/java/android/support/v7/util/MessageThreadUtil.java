package android.support.v7.util;

import android.os.Handler;
import android.support.v7.util.ThreadUtil.BackgroundCallback;
import android.support.v7.util.ThreadUtil.MainThreadCallback;
import android.support.v7.util.TileList.Tile;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

class MessageThreadUtil<T> implements ThreadUtil<T> {

    /* renamed from: android.support.v7.util.MessageThreadUtil$1 reason: invalid class name */
    class AnonymousClass1 implements MainThreadCallback<T> {
        final /* synthetic */ MainThreadCallback a;
        /* access modifiers changed from: private */
        public final MessageQueue b;
        private final Handler c;
        private Runnable d;

        /* renamed from: android.support.v7.util.MessageThreadUtil$1$1 reason: invalid class name */
        class AnonymousClass1 implements Runnable {
            final /* synthetic */ AnonymousClass1 a;

            public void run() {
                SyncQueueItem a2 = this.a.b.a();
                while (a2 != null) {
                    switch (a2.a) {
                        case 1:
                            this.a.a.a(a2.b, a2.c);
                            break;
                        case 2:
                            this.a.a.a(a2.b, (Tile) a2.g);
                            break;
                        case 3:
                            this.a.a.b(a2.b, a2.c);
                            break;
                        default:
                            new StringBuilder("Unsupported message, what=").append(a2.a);
                            break;
                    }
                    a2 = this.a.b.a();
                }
            }
        }

        public final void a(int i, int i2) {
            a(SyncQueueItem.a(1, i, i2));
        }

        public final void a(int i, Tile<T> tile) {
            a(SyncQueueItem.a(2, i, (Object) tile));
        }

        public final void b(int i, int i2) {
            a(SyncQueueItem.a(3, i, i2));
        }

        private void a(SyncQueueItem syncQueueItem) {
            this.b.b(syncQueueItem);
            this.c.post(this.d);
        }
    }

    /* renamed from: android.support.v7.util.MessageThreadUtil$2 reason: invalid class name */
    class AnonymousClass2 implements BackgroundCallback<T> {
        AtomicBoolean a;
        final /* synthetic */ BackgroundCallback b;
        /* access modifiers changed from: private */
        public final MessageQueue c;
        private final Executor d;
        private Runnable e;

        /* renamed from: android.support.v7.util.MessageThreadUtil$2$1 reason: invalid class name */
        class AnonymousClass1 implements Runnable {
            final /* synthetic */ AnonymousClass2 a;

            public void run() {
                while (true) {
                    SyncQueueItem a2 = this.a.c.a();
                    if (a2 != null) {
                        switch (a2.a) {
                            case 1:
                                this.a.c.a(1);
                                this.a.b.a(a2.b);
                                break;
                            case 2:
                                this.a.c.a(2);
                                this.a.c.a(3);
                                this.a.b.a(a2.b, a2.c, a2.d, a2.e, a2.f);
                                break;
                            case 3:
                                this.a.b.a(a2.b, a2.c);
                                break;
                            case 4:
                                this.a.b.a((Tile) a2.g);
                                break;
                            default:
                                new StringBuilder("Unsupported message, what=").append(a2.a);
                                break;
                        }
                    } else {
                        this.a.a.set(false);
                        return;
                    }
                }
            }
        }

        public final void a(int i) {
            b(SyncQueueItem.a(1, i, (Object) null));
        }

        public final void a(int i, int i2, int i3, int i4, int i5) {
            b(SyncQueueItem.a(2, i, i2, i3, i4, i5, null));
        }

        public final void a(int i, int i2) {
            a(SyncQueueItem.a(3, i, i2));
        }

        public final void a(Tile<T> tile) {
            a(SyncQueueItem.a(4, 0, (Object) tile));
        }

        private void a(SyncQueueItem syncQueueItem) {
            this.c.b(syncQueueItem);
            a();
        }

        private void b(SyncQueueItem syncQueueItem) {
            this.c.a(syncQueueItem);
            a();
        }

        private void a() {
            if (this.a.compareAndSet(false, true)) {
                this.d.execute(this.e);
            }
        }
    }

    static class MessageQueue {
        private SyncQueueItem a;

        MessageQueue() {
        }

        /* access modifiers changed from: 0000 */
        public final synchronized SyncQueueItem a() {
            if (this.a == null) {
                return null;
            }
            SyncQueueItem syncQueueItem = this.a;
            this.a = this.a.j;
            return syncQueueItem;
        }

        /* access modifiers changed from: 0000 */
        public final synchronized void a(SyncQueueItem syncQueueItem) {
            syncQueueItem.j = this.a;
            this.a = syncQueueItem;
        }

        /* access modifiers changed from: 0000 */
        public final synchronized void b(SyncQueueItem syncQueueItem) {
            if (this.a == null) {
                this.a = syncQueueItem;
                return;
            }
            SyncQueueItem syncQueueItem2 = this.a;
            while (syncQueueItem2.j != null) {
                syncQueueItem2 = syncQueueItem2.j;
            }
            syncQueueItem2.j = syncQueueItem;
        }

        /* access modifiers changed from: 0000 */
        public final synchronized void a(int i) {
            while (this.a != null && this.a.a == i) {
                SyncQueueItem syncQueueItem = this.a;
                this.a = this.a.j;
                syncQueueItem.a();
            }
            if (this.a != null) {
                SyncQueueItem syncQueueItem2 = this.a;
                SyncQueueItem a2 = syncQueueItem2.j;
                while (a2 != null) {
                    SyncQueueItem a3 = a2.j;
                    if (a2.a == i) {
                        syncQueueItem2.j = a3;
                        a2.a();
                    } else {
                        syncQueueItem2 = a2;
                    }
                    a2 = a3;
                }
            }
        }
    }

    static class SyncQueueItem {
        private static SyncQueueItem h;
        private static final Object i = new Object();
        public int a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public Object g;
        /* access modifiers changed from: private */
        public SyncQueueItem j;

        SyncQueueItem() {
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            this.j = null;
            this.f = 0;
            this.e = 0;
            this.d = 0;
            this.c = 0;
            this.b = 0;
            this.a = 0;
            this.g = null;
            synchronized (i) {
                if (h != null) {
                    this.j = h;
                }
                h = this;
            }
        }

        static SyncQueueItem a(int i2, int i3, int i4, int i5, int i6, int i7, Object obj) {
            SyncQueueItem syncQueueItem;
            synchronized (i) {
                if (h == null) {
                    syncQueueItem = new SyncQueueItem();
                } else {
                    syncQueueItem = h;
                    h = h.j;
                    syncQueueItem.j = null;
                }
                syncQueueItem.a = i2;
                syncQueueItem.b = i3;
                syncQueueItem.c = i4;
                syncQueueItem.d = i5;
                syncQueueItem.e = i6;
                syncQueueItem.f = i7;
                syncQueueItem.g = obj;
            }
            return syncQueueItem;
        }

        static SyncQueueItem a(int i2, int i3, int i4) {
            return a(i2, i3, i4, 0, 0, 0, null);
        }

        static SyncQueueItem a(int i2, int i3, Object obj) {
            return a(i2, i3, 0, 0, 0, 0, obj);
        }
    }

    MessageThreadUtil() {
    }
}
