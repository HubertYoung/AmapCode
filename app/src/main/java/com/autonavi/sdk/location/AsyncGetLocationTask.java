package com.autonavi.sdk.location;

import android.location.Location;
import android.os.Handler;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator.Provider;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.common.model.GeoPoint;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import org.xidea.el.impl.ReflectUtil;

public class AsyncGetLocationTask<T> implements Callback<Status>, com.autonavi.common.Callback.a {
    private static volatile boolean isOriginalLocation = false;
    /* access modifiers changed from: private */
    public Callback<T> callback;
    private a delayTask;
    private Handler handler;
    private boolean isCancel = false;
    private final long requestTime = System.currentTimeMillis();
    private final int timeout;

    static class a implements Runnable {
        volatile boolean a = false;
        volatile boolean b = false;
        private WeakReference<AsyncGetLocationTask> c;

        a(AsyncGetLocationTask asyncGetLocationTask) {
            this.c = new WeakReference<>(asyncGetLocationTask);
        }

        public final void run() {
            if (!this.a) {
                this.b = true;
                if (this.c != null) {
                    AsyncGetLocationTask asyncGetLocationTask = (AsyncGetLocationTask) this.c.get();
                    if (asyncGetLocationTask != null) {
                        if (asyncGetLocationTask.callback != null) {
                            asyncGetLocationTask.callback.error(new IllegalStateException("Request Timeout"), false);
                        }
                        asyncGetLocationTask.safeRemoveLocationCallback();
                    }
                }
            }
        }
    }

    public AsyncGetLocationTask(Callback<T> callback2, Handler handler2, int i) {
        this.callback = callback2;
        this.timeout = Math.max(0, i);
        this.handler = handler2;
    }

    public void doGet() {
        this.isCancel = false;
        if (this.callback != null) {
            if (LocationInstrument.getInstance().isProviderEnabled(Provider.PROVIDER_GPS) || NetworkReachability.b()) {
                anf.e();
                LocationInstrument.getInstance().doStartLocate();
                LocationInstrument.getInstance().addStatusCallback(this, this);
                if (!isOriginalLocation) {
                    LocationInstrument.getInstance().addOriginalLocation(new ang<Status>() {
                        public final /* synthetic */ void onOriginalLocationChange(Object obj) {
                            anf.e();
                        }
                    });
                    isOriginalLocation = true;
                }
                this.delayTask = new a(this);
                this.handler.postDelayed(this.delayTask, (long) this.timeout);
                return;
            }
            this.callback.error(new IllegalStateException("Gps is not open, and network is not avaiable"), false);
        }
    }

    public void callback(Status status) {
        if (this.delayTask != null && !this.delayTask.b) {
            if (status != Status.ON_LOCATION_GPS_FAIL_LOOP) {
                this.delayTask.a = true;
            }
            if (status == Status.ON_LOCATION_OK) {
                Class cls = (Class) ReflectUtil.a((Type) this.callback.getClass(), Callback.class);
                if (cls.equals(Location.class)) {
                    this.callback.callback(LocationInstrument.getInstance().getLatestLocation());
                } else if (cls.equals(GeoPoint.class)) {
                    this.callback.callback(LocationInstrument.getInstance().getLatestPosition());
                }
                safeRemoveLocationCallback();
            }
        }
    }

    public void error(Throwable th, boolean z) {
        if (this.callback != null) {
            this.callback.error(th, z);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0029, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void safeRemoveLocationCallback() {
        /*
            r4 = this;
            monitor-enter(r4)
            long r0 = r4.requestTime     // Catch:{ all -> 0x002a }
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 > 0) goto L_0x000b
            monitor-exit(r4)
            return
        L_0x000b:
            com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ all -> 0x002a }
            boolean r0 = r0.removeStatusCallbackForAsyncTask(r4)     // Catch:{ all -> 0x002a }
            if (r0 == 0) goto L_0x0028
            com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ all -> 0x002a }
            java.util.concurrent.atomic.AtomicInteger r0 = r0.requireCount     // Catch:{ all -> 0x002a }
            int r0 = r0.decrementAndGet()     // Catch:{ all -> 0x002a }
            if (r0 > 0) goto L_0x0028
            com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ all -> 0x002a }
            r0.doStopLocate()     // Catch:{ all -> 0x002a }
        L_0x0028:
            monitor-exit(r4)
            return
        L_0x002a:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.sdk.location.AsyncGetLocationTask.safeRemoveLocationCallback():void");
    }

    public void cancel() {
        this.isCancel = false;
        safeRemoveLocationCallback();
        if (this.delayTask != null) {
            this.delayTask.a = true;
        }
    }

    public boolean isCancelled() {
        return this.isCancel;
    }
}
