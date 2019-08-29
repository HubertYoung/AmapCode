package defpackage;

import java.util.concurrent.Executor;

/* renamed from: ahl reason: default package */
/* compiled from: TaskExecutor */
public final class ahl {

    /* renamed from: ahl$a */
    /* compiled from: TaskExecutor */
    public static abstract class a<Result> {
        public static final int STATE_CANCELLED = 4;
        public static final int STATE_ERROR = 5;
        public static final int STATE_FINISHED = 3;
        public static final int STATE_NULL = 0;
        public static final int STATE_RUNNING = 2;
        public static final int STATE_WAITING = 1;
        /* access modifiers changed from: private */
        public volatile int mState = 0;
        /* access modifiers changed from: private */
        public volatile Runnable mThreadContext;

        /* access modifiers changed from: protected */
        public abstract Result doBackground() throws Exception;

        /* access modifiers changed from: protected */
        public void onCancelled() {
        }

        /* access modifiers changed from: protected */
        public void onError(Throwable th) {
        }

        /* access modifiers changed from: protected */
        public void onFinished(Result result) {
        }

        /* access modifiers changed from: protected */
        public void onStart() {
        }

        /* access modifiers changed from: protected */
        public synchronized Runnable obtainThreadContext() {
            this.mState = 1;
            this.mThreadContext = new Runnable() {
                /* JADX WARNING: Code restructure failed: missing block: B:19:0x0035, code lost:
                    if (defpackage.ahl.a.access$100(r5.a) == r5) goto L_0x0038;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:20:0x0037, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:22:0x003e, code lost:
                    if (defpackage.ahl.a.access$000(r5.a) != 4) goto L_0x0048;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:23:0x0040, code lost:
                    defpackage.aho.a(new defpackage.ahl.a.AnonymousClass1.AnonymousClass4(r5));
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:24:0x0048, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
                    defpackage.aho.a(new defpackage.ahl.a.AnonymousClass1.AnonymousClass1(r5));
                    r1 = r5.a.doBackground();
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:30:0x0064, code lost:
                    if (defpackage.ahl.a.access$000(r5.a) != 4) goto L_0x0080;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:32:0x006c, code lost:
                    if (defpackage.ahl.a.access$100(r5.a) == r5) goto L_0x006f;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:33:0x006e, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:35:0x0075, code lost:
                    if (defpackage.ahl.a.access$000(r5.a) != 4) goto L_0x007f;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:36:0x0077, code lost:
                    defpackage.aho.a(new defpackage.ahl.a.AnonymousClass1.AnonymousClass4(r5));
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:37:0x007f, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
                    r2 = r5.a;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:40:0x0082, code lost:
                    monitor-enter(r2);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:43:0x0089, code lost:
                    if (defpackage.ahl.a.access$100(r5.a) == r5) goto L_0x00a6;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:44:0x008b, code lost:
                    monitor-exit(r2);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:46:0x0092, code lost:
                    if (defpackage.ahl.a.access$100(r5.a) == r5) goto L_0x0095;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:47:0x0094, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:49:0x009b, code lost:
                    if (defpackage.ahl.a.access$000(r5.a) != 4) goto L_0x00a5;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:50:0x009d, code lost:
                    defpackage.aho.a(new defpackage.ahl.a.AnonymousClass1.AnonymousClass4(r5));
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:51:0x00a5, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
                    defpackage.ahl.a.access$002(r5.a, 3);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:54:0x00ac, code lost:
                    monitor-exit(r2);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:57:0x00b3, code lost:
                    if (defpackage.ahl.a.access$100(r5.a) != r5) goto L_0x00bd;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:58:0x00b5, code lost:
                    defpackage.aho.a(new defpackage.ahl.a.AnonymousClass1.AnonymousClass2(r5));
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:60:0x00c3, code lost:
                    if (defpackage.ahl.a.access$100(r5.a) == r5) goto L_0x00c6;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:61:0x00c5, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:63:0x00cc, code lost:
                    if (defpackage.ahl.a.access$000(r5.a) != 4) goto L_0x012c;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:64:0x00ce, code lost:
                    r0 = new defpackage.ahl.a.AnonymousClass1.AnonymousClass4(r5);
                 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void run() {
                    /*
                        r5 = this;
                        r0 = 4
                        ahl$a r1 = defpackage.ahl.a.this     // Catch:{ Throwable -> 0x00df }
                        int r1 = r1.mState     // Catch:{ Throwable -> 0x00df }
                        if (r1 != r0) goto L_0x0023
                        ahl$a r1 = defpackage.ahl.a.this
                        java.lang.Runnable r1 = r1.mThreadContext
                        if (r1 == r5) goto L_0x0012
                        return
                    L_0x0012:
                        ahl$a r1 = defpackage.ahl.a.this
                        int r1 = r1.mState
                        if (r1 != r0) goto L_0x0022
                        ahl$a$1$4 r0 = new ahl$a$1$4
                        r0.<init>()
                        defpackage.aho.a(r0)
                    L_0x0022:
                        return
                    L_0x0023:
                        ahl$a r1 = defpackage.ahl.a.this     // Catch:{ Throwable -> 0x00df }
                        monitor-enter(r1)     // Catch:{ Throwable -> 0x00df }
                        ahl$a r2 = defpackage.ahl.a.this     // Catch:{ all -> 0x00da }
                        java.lang.Runnable r2 = r2.mThreadContext     // Catch:{ all -> 0x00da }
                        if (r2 == r5) goto L_0x0049
                        monitor-exit(r1)     // Catch:{ all -> 0x00da }
                        ahl$a r1 = defpackage.ahl.a.this
                        java.lang.Runnable r1 = r1.mThreadContext
                        if (r1 == r5) goto L_0x0038
                        return
                    L_0x0038:
                        ahl$a r1 = defpackage.ahl.a.this
                        int r1 = r1.mState
                        if (r1 != r0) goto L_0x0048
                        ahl$a$1$4 r0 = new ahl$a$1$4
                        r0.<init>()
                        defpackage.aho.a(r0)
                    L_0x0048:
                        return
                    L_0x0049:
                        ahl$a r2 = defpackage.ahl.a.this     // Catch:{ all -> 0x00da }
                        r3 = 2
                        r2.mState = r3     // Catch:{ all -> 0x00da }
                        monitor-exit(r1)     // Catch:{ all -> 0x00da }
                        ahl$a$1$1 r1 = new ahl$a$1$1     // Catch:{ Throwable -> 0x00df }
                        r1.<init>()     // Catch:{ Throwable -> 0x00df }
                        defpackage.aho.a(r1)     // Catch:{ Throwable -> 0x00df }
                        ahl$a r1 = defpackage.ahl.a.this     // Catch:{ Throwable -> 0x00df }
                        java.lang.Object r1 = r1.doBackground()     // Catch:{ Throwable -> 0x00df }
                        ahl$a r2 = defpackage.ahl.a.this     // Catch:{ Throwable -> 0x00df }
                        int r2 = r2.mState     // Catch:{ Throwable -> 0x00df }
                        if (r2 != r0) goto L_0x0080
                        ahl$a r1 = defpackage.ahl.a.this
                        java.lang.Runnable r1 = r1.mThreadContext
                        if (r1 == r5) goto L_0x006f
                        return
                    L_0x006f:
                        ahl$a r1 = defpackage.ahl.a.this
                        int r1 = r1.mState
                        if (r1 != r0) goto L_0x007f
                        ahl$a$1$4 r0 = new ahl$a$1$4
                        r0.<init>()
                        defpackage.aho.a(r0)
                    L_0x007f:
                        return
                    L_0x0080:
                        ahl$a r2 = defpackage.ahl.a.this     // Catch:{ Throwable -> 0x00df }
                        monitor-enter(r2)     // Catch:{ Throwable -> 0x00df }
                        ahl$a r3 = defpackage.ahl.a.this     // Catch:{ all -> 0x00d7 }
                        java.lang.Runnable r3 = r3.mThreadContext     // Catch:{ all -> 0x00d7 }
                        if (r3 == r5) goto L_0x00a6
                        monitor-exit(r2)     // Catch:{ all -> 0x00d7 }
                        ahl$a r1 = defpackage.ahl.a.this
                        java.lang.Runnable r1 = r1.mThreadContext
                        if (r1 == r5) goto L_0x0095
                        return
                    L_0x0095:
                        ahl$a r1 = defpackage.ahl.a.this
                        int r1 = r1.mState
                        if (r1 != r0) goto L_0x00a5
                        ahl$a$1$4 r0 = new ahl$a$1$4
                        r0.<init>()
                        defpackage.aho.a(r0)
                    L_0x00a5:
                        return
                    L_0x00a6:
                        ahl$a r3 = defpackage.ahl.a.this     // Catch:{ all -> 0x00d7 }
                        r4 = 3
                        r3.mState = r4     // Catch:{ all -> 0x00d7 }
                        monitor-exit(r2)     // Catch:{ all -> 0x00d7 }
                        ahl$a r2 = defpackage.ahl.a.this     // Catch:{ Throwable -> 0x00df }
                        java.lang.Runnable r2 = r2.mThreadContext     // Catch:{ Throwable -> 0x00df }
                        if (r2 != r5) goto L_0x00bd
                        ahl$a$1$2 r2 = new ahl$a$1$2     // Catch:{ Throwable -> 0x00df }
                        r2.<init>(r1)     // Catch:{ Throwable -> 0x00df }
                        defpackage.aho.a(r2)     // Catch:{ Throwable -> 0x00df }
                    L_0x00bd:
                        ahl$a r1 = defpackage.ahl.a.this
                        java.lang.Runnable r1 = r1.mThreadContext
                        if (r1 == r5) goto L_0x00c6
                        return
                    L_0x00c6:
                        ahl$a r1 = defpackage.ahl.a.this
                        int r1 = r1.mState
                        if (r1 != r0) goto L_0x012c
                        ahl$a$1$4 r0 = new ahl$a$1$4
                        r0.<init>()
                    L_0x00d3:
                        defpackage.aho.a(r0)
                        return
                    L_0x00d7:
                        r1 = move-exception
                        monitor-exit(r2)     // Catch:{ all -> 0x00d7 }
                        throw r1     // Catch:{ Throwable -> 0x00df }
                    L_0x00da:
                        r2 = move-exception
                        monitor-exit(r1)     // Catch:{ all -> 0x00da }
                        throw r2     // Catch:{ Throwable -> 0x00df }
                    L_0x00dd:
                        r1 = move-exception
                        goto L_0x0130
                    L_0x00df:
                        r1 = move-exception
                        ahl$a r2 = defpackage.ahl.a.this     // Catch:{ all -> 0x00dd }
                        monitor-enter(r2)     // Catch:{ all -> 0x00dd }
                        ahl$a r3 = defpackage.ahl.a.this     // Catch:{ all -> 0x012d }
                        java.lang.Runnable r3 = r3.mThreadContext     // Catch:{ all -> 0x012d }
                        if (r3 == r5) goto L_0x0106
                        monitor-exit(r2)     // Catch:{ all -> 0x012d }
                        ahl$a r1 = defpackage.ahl.a.this
                        java.lang.Runnable r1 = r1.mThreadContext
                        if (r1 == r5) goto L_0x00f5
                        return
                    L_0x00f5:
                        ahl$a r1 = defpackage.ahl.a.this
                        int r1 = r1.mState
                        if (r1 != r0) goto L_0x0105
                        ahl$a$1$4 r0 = new ahl$a$1$4
                        r0.<init>()
                        defpackage.aho.a(r0)
                    L_0x0105:
                        return
                    L_0x0106:
                        ahl$a r3 = defpackage.ahl.a.this     // Catch:{ all -> 0x012d }
                        r4 = 5
                        r3.mState = r4     // Catch:{ all -> 0x012d }
                        monitor-exit(r2)     // Catch:{ all -> 0x012d }
                        ahl$a$1$3 r2 = new ahl$a$1$3     // Catch:{ all -> 0x00dd }
                        r2.<init>(r1)     // Catch:{ all -> 0x00dd }
                        defpackage.aho.a(r2)     // Catch:{ all -> 0x00dd }
                        ahl$a r1 = defpackage.ahl.a.this
                        java.lang.Runnable r1 = r1.mThreadContext
                        if (r1 == r5) goto L_0x011e
                        return
                    L_0x011e:
                        ahl$a r1 = defpackage.ahl.a.this
                        int r1 = r1.mState
                        if (r1 != r0) goto L_0x012c
                        ahl$a$1$4 r0 = new ahl$a$1$4
                        r0.<init>()
                        goto L_0x00d3
                    L_0x012c:
                        return
                    L_0x012d:
                        r1 = move-exception
                        monitor-exit(r2)     // Catch:{ all -> 0x012d }
                        throw r1     // Catch:{ all -> 0x00dd }
                    L_0x0130:
                        ahl$a r2 = defpackage.ahl.a.this
                        java.lang.Runnable r2 = r2.mThreadContext
                        if (r2 == r5) goto L_0x0139
                        return
                    L_0x0139:
                        ahl$a r2 = defpackage.ahl.a.this
                        int r2 = r2.mState
                        if (r2 != r0) goto L_0x0149
                        ahl$a$1$4 r0 = new ahl$a$1$4
                        r0.<init>()
                        defpackage.aho.a(r0)
                    L_0x0149:
                        throw r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: defpackage.ahl.a.AnonymousClass1.run():void");
                }
            };
            return this.mThreadContext;
        }

        public void cancel() {
            this.mState = 4;
        }

        public boolean isStopped() {
            return this.mState > 2;
        }

        public boolean isStarted() {
            return this.mState != 4 && this.mState > 0;
        }

        public boolean isCancelled() {
            return this.mState == 4;
        }
    }

    public static <T> a<T> a(a<T> aVar) {
        ahn.b().execute(aVar.obtainThreadContext());
        return aVar;
    }

    public static <T> a<T> a(a<T> aVar, Executor executor) {
        executor.execute(aVar.obtainThreadContext());
        return aVar;
    }

    public static <T> a<T> b(a<T> aVar) {
        ahm.a(aVar.obtainThreadContext());
        return aVar;
    }
}
