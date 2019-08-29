package defpackage;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.preference.PreferenceManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.RejectedExecutionException;

/* renamed from: dtq reason: default package */
/* compiled from: AutoFocusManager */
final class dtq implements AutoFocusCallback {
    private static final String a = "dtq";
    private static final Collection<String> b;
    private boolean c;
    private boolean d;
    private final boolean e;
    private final Camera f;
    private AsyncTask<?, ?, ?> g;

    /* renamed from: dtq$a */
    /* compiled from: AutoFocusManager */
    final class a extends AsyncTask<Object, Object, Object> {
        private a() {
        }

        /* synthetic */ a(dtq dtq, byte b) {
            this();
        }

        /* access modifiers changed from: protected */
        public final Object doInBackground(Object... objArr) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException unused) {
            }
            dtq.this.a();
            return null;
        }
    }

    static {
        ArrayList arrayList = new ArrayList(2);
        b = arrayList;
        arrayList.add("auto");
        b.add("macro");
    }

    dtq(Context context, Camera camera) {
        this.f = camera;
        PreferenceManager.getDefaultSharedPreferences(context);
        this.e = b.contains(camera.getParameters().getFocusMode());
        a();
    }

    public final synchronized void onAutoFocus(boolean z, Camera camera) {
        this.d = false;
        c();
    }

    private synchronized void c() {
        if (!this.c && this.g == null) {
            a aVar = new a(this, 0);
            try {
                aVar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
                this.g = aVar;
            } catch (RejectedExecutionException unused) {
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:12|13|14|15) */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        c();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001e, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x001a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a() {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.e     // Catch:{ all -> 0x001f }
            if (r0 == 0) goto L_0x001d
            r0 = 0
            r1.g = r0     // Catch:{ all -> 0x001f }
            boolean r0 = r1.c     // Catch:{ all -> 0x001f }
            if (r0 != 0) goto L_0x001d
            boolean r0 = r1.d     // Catch:{ all -> 0x001f }
            if (r0 != 0) goto L_0x001d
            android.hardware.Camera r0 = r1.f     // Catch:{ RuntimeException -> 0x001a }
            r0.autoFocus(r1)     // Catch:{ RuntimeException -> 0x001a }
            r0 = 1
            r1.d = r0     // Catch:{ RuntimeException -> 0x001a }
            monitor-exit(r1)
            return
        L_0x001a:
            r1.c()     // Catch:{ all -> 0x001f }
        L_0x001d:
            monitor-exit(r1)
            return
        L_0x001f:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dtq.a():void");
    }

    private synchronized void d() {
        if (this.g != null) {
            if (this.g.getStatus() != Status.FINISHED) {
                this.g.cancel(true);
            }
            this.g = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void b() {
        this.c = true;
        if (this.e) {
            d();
            try {
                this.f.cancelAutoFocus();
            } catch (RuntimeException unused) {
            }
        }
    }
}
